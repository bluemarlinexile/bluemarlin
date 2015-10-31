/*
 */
package com.airhacks.airfield;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullCommand;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.TextProgressMonitor;


/**
 *
 * @author adam-bien.com
 */
public class TakeDown {

    private final String remotePath;
    private final String localPath;
    private Git git;
	private ProgressMonitor monitor;
	private PrintStream ps;

    public TakeDown(String localPath, String remotePath, PrintStream ps) {
    	this.ps = ps;
    	monitor = new TextProgressMonitor(new PrintWriter(ps, true));
        this.remotePath = remotePath;
        this.localPath = localPath;
    }

    void initialDownload() throws AirfieldException {
        try {
            this.git = Git.cloneRepository()
                    .setURI(remotePath)
                    .setProgressMonitor(monitor)
                    .setDirectory(new File(localPath))
                    .call();
            ps.println("+App installed into: " + this.localPath);
        } catch (GitAPIException ex) {
            throw new AirfieldException("--Cannot download files: " + ex.getMessage(), ex);
        }

    }

    boolean update() throws AirfieldException {
    	 try {
             this.git
             	.reset()
             	.setMode(ResetCommand.ResetType.HARD).call();

//             ps.println("+Changed files removed");
         } catch (GitAPIException ex) {
             throw new AirfieldException("Cannot reset local repository", ex);
         }
        PullCommand command = this.git.pull();
        command.setProgressMonitor(monitor);
        try {
            PullResult pullResult = command.call();
            if (pullResult.isSuccessful()) {
            	ps.println("+Files updated, ready to start!");
            } else {
            	ps.println("--Download was not successful " + pullResult.toString());
            }
            boolean localrepoUpdate = !pullResult.getFetchResult().getTrackingRefUpdates().isEmpty();
			return localrepoUpdate;
        } catch (GitAPIException ex) {
        	throw new AirfieldException("Exception on PullCommand.call()", ex);
        }
    }

    boolean openLocal() {
        File localRepo = new File(this.localPath);
        try {
            this.git = Git.open(localRepo);
        } catch (IOException ex) {
        	ps.println("-" + ex.getMessage());
            return false;
        }
        ps.println("+Application installed at: " + this.localPath);
        return true;
    }

    public boolean installOrUpdate() throws AirfieldException {
    	boolean newChanges = false;
        boolean alreadyInstalled = openLocal();
        if (alreadyInstalled) {
            newChanges = update();
        } else {
            initialDownload();
            newChanges = true;
        }
        if (newChanges) {
        	ps.println("+Bluemarlin successfully updated");
		} else {
			ps.println("+Bluemarlin is up-to-date");
		}
        return newChanges;
    }

}

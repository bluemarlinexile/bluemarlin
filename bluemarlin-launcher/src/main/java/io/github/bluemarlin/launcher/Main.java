/*
 * Copyright (C) 2015 thirdy
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package io.github.bluemarlin.launcher;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import com.airhacks.airfield.AirfieldException;
import com.airhacks.airfield.TakeDown;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * @author thirdy
 *
 */
public class Main extends Application {
	
	private static final String LAUNCHER_VERSION = "1.0";

	private static final String GITHUB_RELEASES_REPO = "https://github.com/bluemarlinexile/bluemarlin-releases.git";
	private static final String CONTROL_PROPERTIES_URL = "http://bluemarlinexile.github.io/control.properties.txt";
	private static final String CHANGELOG_URL = "http://bluemarlinexile.github.io/changelog.txt";
	private static final String WEBSITE_URL = "http://bluemarlinexile.github.io/";

	
	BooleanProperty showLaunchButton = new SimpleBooleanProperty(false);

	@Override
	public void start(Stage primaryStage) {
		List<String> args = getParameters().getRaw();
		
        String local = args.size() == 2 ? args.get(0) : "./lib";
        String remote = args.size() == 2 ? args.get(1) : GITHUB_RELEASES_REPO;
        
        if (args.size() == 1 && "-skip".equalsIgnoreCase(args.get(0))) {
        	finishedAllTasks();
        	return;
        }
        
        TextArea updaterMessagesTextArea = new TextArea();
        updaterMessagesTextArea.setWrapText(true);
        updaterMessagesTextArea.setId("updaterMessagesTextArea");
        
        PrintStream takedownPrintStream = new PrintStream(new Console(updaterMessagesTextArea), true) {
        	@Override
        	public void flush() {
        		append("\n");
        		super.flush();
        	}
        };
       
		TakeDown installer = new TakeDown(local, remote, takedownPrintStream);
        AirfieldService airfieldService = new AirfieldService(installer);
        showLaunchButton.bind(airfieldService.newChangesProperty());
        
        airfieldService.setOnSucceeded(e -> {
        	takedownPrintStream.close();
        	finishedAllTasks();
        });
        
        airfieldService.setOnFailed(e -> {
        	Dialogs.showExceptionDialog(airfieldService.getException());
        	finishedAllTasks();
        });
        
        TextArea changelogTextArea = new TextArea();
        changelogTextArea.setWrapText(true);
        changelogTextArea.setId("changelogTextArea");
        
        Button launchBtn = new Button("Launch");
        launchBtn.setId("launchBtn");
        launchBtn.setMinWidth(180);
        launchBtn.visibleProperty().bind(showLaunchButton);
        launchBtn.setOnAction(e -> runBluemarlin());
		
        Label status = new Label("Sucessfully started Bluemarlin Launcher");
        status.textProperty().bind(airfieldService.messageProperty());
        status.visibleProperty().bind(showLaunchButton.not());
        
        ProgressBar p = new ProgressBar();
        p.setMaxWidth(Double.MAX_VALUE);
        p.progressProperty().bind(airfieldService.progressProperty());
        p.visibleProperty().bind(showLaunchButton.not());
        
        VBox updaterMessagesTextAreaPane = new VBox(updaterMessagesTextArea);
        updaterMessagesTextAreaPane.setId("updaterMessagesTextAreaPane");
        
        VBox changelogTextAreaPane = new VBox(changelogTextArea);
        changelogTextAreaPane.setId("changelogTextAreaPane");
        
        int randomIdx = new Random().nextInt(2);
        System.out.println("randomIdx: " + randomIdx);
        String images = new String[] {"/DarkshrineAnnouncement.jpg", "/gggbanner-plain.png"} [randomIdx];
        ImageView backgroundImageView = new ImageView(images);

        HBox centerPane = new HBox();
        
        if (randomIdx == 1) {
        	changelogTextArea.setStyle("-fx-text-fill: black; -fx-font-weight: normal; -fx-font-size: 11pt;  -fx-font-family: \"Serif\"");
        	updaterMessagesTextArea.setStyle("-fx-text-fill: black;");
        	updaterMessagesTextArea.setMinWidth(230);
        	centerPane.getChildren().addAll(changelogTextAreaPane, updaterMessagesTextAreaPane);
        } else {
        	centerPane.getChildren().addAll(updaterMessagesTextAreaPane, changelogTextAreaPane);
        }
        
        
        centerPane.setSpacing(10);
        centerPane.setId("centerPane");
        BorderPane widgetsPane = new BorderPane();
        widgetsPane.setId("widgetsPane");
		widgetsPane.setCenter(centerPane);
        StackPane bottom = new StackPane(p, status, launchBtn);
        widgetsPane.setBottom(bottom);

        backgroundImageView.setId("backgroundImageView");
        StackPane root = new StackPane(backgroundImageView, widgetsPane);
        root.setId("root");
        
		Scene scene = new Scene(root, backgroundImageView.getImage().getWidth(), 359);
		scene.getStylesheets().add(this.getClass().getResource("/css/bluemarlin-launcher.css").toExternalForm());
		primaryStage.setTitle("Bluemarlin Launcher");
		primaryStage.getIcons().add(new Image("/Atlantic_blue_marlin64x64.png"));
		primaryStage.setScene(scene);
		
		updaterMessagesTextArea.maxWidthProperty().bind(scene.widthProperty().multiply(0.5));
		
		Task<Void> changelogDownloaderTask = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
				UrlReader urlReader = new UrlReader(s -> changelogTextArea.appendText(s + System.lineSeparator()));
				urlReader.download(CHANGELOG_URL);
				return null;
			}
		};
		
		changelogDownloaderTask.setOnFailed(e -> {
			changelogTextArea.appendText("Failed to download changelog: " + changelogDownloaderTask.getException().toString());
		});
		
		
		Task<Properties> controlDownloaderTask = new Task<Properties>() {
			
			@Override
			protected Properties call() throws Exception {
				String controlurl = CONTROL_PROPERTIES_URL;

				System.out.println( "Now downloading control properties from: " + controlurl );
				
				URL url = new URL(controlurl);
				InputStream in = url.openStream();
				Reader reader = new InputStreamReader(in, "UTF-8"); // for example
				 
				Properties prop = new Properties();
				try {
				    prop.load(reader);
				} finally {
				    reader.close();
				}
				
				System.out.println( "Successfully downloaded control properties from: " + controlurl );

				return prop;
			}
		};
		
		controlDownloaderTask.setOnSucceeded(e -> {
			Properties properties = controlDownloaderTask.getValue();
			boolean updateEnabled = Boolean.valueOf(properties.getProperty("update.enabled", "true"));
			String currentLauncherVersion = properties.getProperty("current.launcher.version", LAUNCHER_VERSION);
			System.out.println("Control Properties: " + properties.toString());
			if (!LAUNCHER_VERSION.equals(currentLauncherVersion)) {
				Dialogs.showInfo("Your current version of Bluemarlin is now outdated. You can still continue to use your version of Bluemarlin. Check out the website "
						+ "to grab the latest version: " + WEBSITE_URL, 
						"New version of Bluemarlin!");
				runBluemarlin();
			} else if (updateEnabled) {
				airfieldService.start();
				new Thread(changelogDownloaderTask).run();
			}
		});
		
		controlDownloaderTask.setOnFailed(e -> {
			Dialogs.showExceptionDialog(controlDownloaderTask.getException());
			finishedAllTasks();
		});
		
		primaryStage.show();
		
		new Thread(controlDownloaderTask).run();
	}

	private void finishedAllTasks() {
		if (!showLaunchButton.getValue()) {
			runBluemarlin();
			Platform.exit();
		}
	}

	private void runBluemarlin() {
		String exe = "cmd /c start /D . bluemarlin.exe";
		System.out.println("Running Bluemarlin Actual: " + exe);
		try {
			Process p = new ProcessBuilder(exe.split("\\s"))
					.start();
			p.waitFor();
			System.out.println("Successfully started Bluemarlin Actual, launcher is now exiting.");
		} catch ( Exception e) {
			System.out.println("Failed to launch bluemarlin.exe");
			e.printStackTrace();
			Dialogs.showExceptionDialog(e);
		}
		Platform.exit();
	}

	public static void main(String[] args) {
		System.out.println("Commandline usage:");
		System.out.println("param1: [PATH_TO_LOCAL_APP]");
		System.out.println("param2: [PATH_TO_REMOTE_GIT_REPO]");
		System.out.println("or");
		System.out.println("param1: -skip");
		
		launch(args);
	}
	
	private static class AirfieldService extends Service<Void> {
		private BooleanProperty newChangesProperty = new SimpleBooleanProperty(false); 
		private final TakeDown installer;
        public AirfieldService(TakeDown installer) {
			super();
			this.installer = installer;
		}
		@Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                protected Void call() throws AirfieldException {
                	updateMessage("Now making sure that Bluemarlin is up-to-date.");
                	boolean newChanges = installer.installOrUpdate();
                	newChangesProperty.set(newChanges);
                	updateMessage("Auto-update done. Launching Bluemarlin.");
                	return null;
                }
            };
        }
		public BooleanProperty newChangesProperty() {
			return newChangesProperty;
		}
    }

	 public static class Console extends OutputStream {

	        private TextArea output;
	        

	        public Console(TextArea ta) {
	            this.output = ta;
	        }

	        @Override
	        public void write(int i) throws IOException {
	        	Platform.runLater(new Runnable() {
	                public void run() {
	                    String s = String.valueOf((char) i);
						output.appendText(s);
	                }
	            });
	        }
	    }
}

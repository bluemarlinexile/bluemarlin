package io.github.bluemarlin.ui.searchtree;

import java.io.File;
import java.io.IOException;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SearchTreeView extends TreeView<SearchTreeItem> {
	
	public SearchTreeView() {
		setEditable(false);
		setCellFactory((TreeView<SearchTreeItem> p) -> 
        		new SearchTreeViewTreeCellImpl());
		
		findFiles(new SearchDirectory(), this, null);
	}

	private void findFiles(File dir, TreeView<SearchTreeItem> filesTree, TreeItem<SearchTreeItem> parent) {
		TreeItem<SearchTreeItem> root = new TreeItem<>(new SearchTreeItem(dir));
		root.setExpanded(true);
		try {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isDirectory()) {
					System.out.println("directory:" + file.getCanonicalPath());
					findFiles(file, filesTree, root);
				} else {
					System.out.println("     file:" + file.getCanonicalPath());
					SearchTreeItem searchTreeItem = new SearchTreeItem(file);
					root.getChildren().add(new TreeItem<>(searchTreeItem));
				}

			}
			if (parent == null) {
				filesTree.setRoot(root);
			} else {
				parent.getChildren().add(root);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

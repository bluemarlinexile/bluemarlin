package io.github.bluemarlin.ui.searchtree;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class SearchTreeView extends TreeView<SearchTreeItem> {
	
	private List<SearchTreeItem> searchTreeItems = new ArrayList<>();

	public SearchTreeView() {
		setEditable(false);
		setCellFactory((TreeView<SearchTreeItem> p) -> new SearchTreeViewTreeCellImpl());
		findFiles(new SearchDirectory(), this, null);
	}

	private void findFiles(File dir, TreeView<SearchTreeItem> filesTree, TreeItem<SearchTreeItem> parent) {
		TreeItem<SearchTreeItem> root = new TreeItem<>(new SearchTreeItem(dir));
		root.setExpanded(true);
		File[] files = dir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				findFiles(file, filesTree, root);
			} else {
				SearchTreeItem searchTreeItem = new SearchTreeItem(file);
				root.getChildren().add(new TreeItem<>(searchTreeItem));
				searchTreeItems.add(searchTreeItem);
			}

		}
		if (parent == null) {
			filesTree.setRoot(root);
		} else {
			parent.getChildren().add(root);
		}
	}

	public List<SearchTreeItem> durianSearchTreeItems() {
		return searchTreeItems.stream()
				.filter(sti -> sti.isDurianSeachItem())
				.collect(Collectors.toList());
	}
	
	
	
}

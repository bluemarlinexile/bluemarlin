package io.github.bluemarlin.ui.searchtree;

import javafx.scene.control.TreeCell;

class SearchTreeViewTreeCellImpl  extends TreeCell<SearchTreeItem> {
 
        public SearchTreeViewTreeCellImpl() {
        }
 
        @Override
        public void updateItem(SearchTreeItem item, boolean empty) {
            super.updateItem(item, empty);
            
            if (empty) {
                setGraphic(null);
                textProperty().unbind();
                setText(null);
                disableProperty().unbind();
            } else {
            		setGraphic(getTreeItem().getGraphic());
            		textProperty().bind(item.displayNameProperty());
                    disableProperty().bind(item.searchRunningProperty());
            }
        }
 
 
    }
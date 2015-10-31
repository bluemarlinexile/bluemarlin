package io.github.bluemarlin.ui.searchtree;

import javafx.scene.control.TreeCell;

class SearchTreeViewTreeCellImpl  extends TreeCell<SearchTreeItem> {
 
//        private TextField textField;
 
        public SearchTreeViewTreeCellImpl() {
        }
 
//        @Override
//        public void startEdit() {
//            super.startEdit();
// 
//            if (textField == null) {
//                createTextField();
//            }
//            setText(null);
//            setGraphic(textField);
//            textField.selectAll();
//        }
 
//        @Override
//        public void cancelEdit() {
//            super.cancelEdit();
//            File item = (File) getItem();
//			setText(item.getName());
//            setGraphic(getTreeItem().getGraphic());
//        }
 
        @Override
        public void updateItem(SearchTreeItem item, boolean empty) {
            super.updateItem(item, empty);
            
            if (empty) {
                setText(null);
                setGraphic(null);
                disableProperty().unbind();
            } else {
//                if (isEditing()) {
//                    if (textField != null) {
//                        textField.setText(getString());
//                    }
//                    setText(null);
//                    setGraphic(textField);
//                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
//                }
                    disableProperty().bind(item.searchRunningProperty());
            }
        }
 
//        private void createTextField() {
//            textField = new TextField(getString());
//            textField.setOnKeyReleased((KeyEvent t) -> {
//                if (t.getCode() == KeyCode.ENTER) {
//                	File item = (File) getItem();
//                	File newFile = new File(item.getParentFile(), textField.getText());
//                	item.renameTo(newFile);
//                	commitEdit(newFile);
//                } else if (t.getCode() == KeyCode.ESCAPE) {
//                    cancelEdit();
//                }
//            });
//        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().getName();
        }
    }
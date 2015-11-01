package io.github.bluemarlin.service;

import io.github.bluemarlin.ui.BluemarlinApplication;
import io.github.bluemarlin.ui.searchtree.SearchFile;
import io.github.bluemarlin.ui.searchview.Search;
import io.searchbox.core.SearchResult;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ExileToolsService extends Service<Void> {
	
	private ObjectProperty<Search> search = new SimpleObjectProperty<>();
	public ObjectProperty<Search> searchProperty() {return search;}
	
	private ObjectProperty<SearchFile> searchFile = new SimpleObjectProperty<>();
	public ObjectProperty<SearchFile> searchFileProperty() {return searchFile;}
    
	@Override
    protected Task<Void> createTask() {
        return new Task<Void>() {    
            @Override protected Void call() throws Exception {
            		updateMessage("Querying against Exile Tools Elastic Search Public API...");
            		SearchResult result = BluemarlinApplication.getExileToolsESClient().execute(searchFile.getValue().getJsonSearch());
            		
            		Platform.runLater(() -> search.set(new Search(searchFile.getValue(), result)));

//        			// cache images
//        			updateMessage("Caching images...");
//        			
//					for (ExileToolsHit h : exileToolHits) {
//						String icon = h.getInfo().getIcon();
//						updateMessage("Caching image: " + icon);
//						ImageCache.getInstance().preLoad(icon);
//					}
					
					// online status
//					updateMessage("Looking up player status...");
					
        			return null;
            }
			
        };
	}

}

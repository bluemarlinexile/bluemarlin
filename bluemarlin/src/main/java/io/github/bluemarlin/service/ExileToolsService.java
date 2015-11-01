package io.github.bluemarlin.service;

import io.github.bluemarlin.ui.BluemarlinApplication;
import io.github.bluemarlin.util.ex.BlackmarketException;
import io.jexiletools.es.ExileToolsSearchException;
import io.searchbox.core.SearchResult;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ExileToolsService extends Service<Void> {
	
	private ObjectProperty<SearchResult> searchResult = new SimpleObjectProperty<>();
	public ObjectProperty<SearchResult> searchResultProperty() {return searchResult;}
	
	private StringProperty searchJson = new SimpleStringProperty();
    public final StringProperty searchJsonProperty() { return searchJson; }
    
	@Override
    protected Task<Void> createTask() {
        return new Task<Void>() {    
            @Override protected Void call() throws Exception {
            	try {

            		updateMessage("Querying against Exile Tools Elastic Search Public API...");
            		SearchResult result = BluemarlinApplication.getExileToolsESClient().execute(searchJson.getValue());
            		
            		Platform.runLater(() -> searchResult.set(result));

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
        		} catch (ExileToolsSearchException e) {
        			e.printStackTrace();
        			String msg = "Error while running search to Exile Tools ES API";
        			updateMessage(e.getMessage());
					throw new BlackmarketException(msg, e);
        		}
            }
			
        };
	}

}

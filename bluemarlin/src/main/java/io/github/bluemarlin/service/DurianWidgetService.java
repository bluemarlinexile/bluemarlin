package io.github.bluemarlin.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bluemarlin.ui.searchtree.SearchTreeItem;
import io.github.bluemarlin.util.UrlReaderUtil;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class DurianWidgetService extends Service<Void> {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	private List<SearchTreeItem> searchTreeItems;
	
	private IntegerProperty resultsFound = new SimpleIntegerProperty(0);
	public IntegerProperty resultsFoundProperty() {return resultsFound;}
	
	public DurianWidgetService(List<SearchTreeItem> searchTreeItems) {
		this.searchTreeItems = searchTreeItems;
		setOnSucceeded(e -> goAgain());
		setOnFailed	 (e -> goAgain());
		
		searchTreeItems.stream().forEach(sti -> {
			sti.searchResultProperty().addListener((observable, oldVal, newVal) -> {
				System.out.println("new total: " + newVal.getTotal());
				resultsFound.setValue(newVal.getTotal() + resultsFound.getValue());
				if (newVal.getTotal() > 0) {
					sti.hasDurianResultsProperty().setValue(true);
				}
			});
		});
	}

	private void goAgain() {
		resultsFound.setValue(0);
		restart();
	}

	@Override
    protected Task<Void> createTask() {
        return new Task<Void>() {  
        	
            @Override protected Void call() throws Exception {
            	updateMessage("Running Durian Service..");
				String lastUpdate = retrieveIndexerLastUpdate();

				for (int i = 6; i >= 0; i--) {
					Thread.sleep(1000);
					final int _i = i;
					updateMessage(String.format("Checking in %d sec..", _i));
				}
            	return null;
            }
        };
	}
	
	private String retrieveIndexerLastUpdate() throws InterruptedException {
		String result = "SUCCESS";
		
		for (SearchTreeItem sti : searchTreeItems) {
				Platform.runLater(() -> sti.search());
		}
		
		return result;
	}
}

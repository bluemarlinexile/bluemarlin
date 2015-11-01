package io.github.bluemarlin.service;

import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bluemarlin.ui.searchtree.SearchTreeItem;
import io.github.bluemarlin.util.config.BluemarlinConfig;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;

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
			sti.searchProperty().addListener((observable, oldVal, newVal) -> {
				Integer total = newVal.getSearchResult().getTotal();
				if (total != null) {
					resultsFound.setValue(total + resultsFound.getValue());
					if (resultsFound.intValue() > 0) {
						String soundfile = Paths.get(BluemarlinConfig.defaultNotificationSound()).toAbsolutePath().toUri().toString();
						AudioClip notificationSound = new AudioClip(soundfile);
				        Platform.runLater(() -> notificationSound.play(1.0));
					}
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

				int ten_mins = 60 * 10;
				for (int i = ten_mins; i >= 0; i--) {
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
				Platform.runLater(() -> {
					sti.search(true);
				});
		}
		
		return result;
	}
}

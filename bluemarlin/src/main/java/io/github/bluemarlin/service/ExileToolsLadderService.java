package io.github.bluemarlin.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bluemarlin.util.UrlReaderUtil;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class ExileToolsLadderService extends Service<Void> {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private StringProperty result = new SimpleStringProperty("{}");
    public final StringProperty resultProperty() { return result; }
    
    public ExileToolsLadderService() {
		setOnSucceeded(e -> restart());
		setOnFailed	 (e -> restart());
	}
    
	@Override
    protected Task<Void> createTask() {
        return new Task<Void>() {  
        	
            @Override protected Void call() throws Exception {
				String resultJson = retrieveAllOnline();
				result.setValue(resultJson);

				int fiveMins = 60 * 5;
				for (int i = fiveMins; i >= 0; i--) {
					Thread.sleep(1000);
//					int _i = i;
//					Platform.runLater(() -> setCountdown("(" + _i + ")" + " Indexer Last Update: "));
				}
            	return null;
            }
        };
	}
	
	private String retrieveAllOnline() {
		String result = "";
		String url = "http://api.exiletools.com/ladder?showAllOnline=1";
		try {
			result += StringUtils.trimToEmpty(UrlReaderUtil.getString(url));
		} catch (Exception e1) {
			result += String.format("Error downloading Ladder API data from %s, error: %s", url, e1.getMessage());
		}
		return result;
	}
}

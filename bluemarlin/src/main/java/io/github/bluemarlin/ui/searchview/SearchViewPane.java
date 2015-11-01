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
package io.github.bluemarlin.ui.searchview;

import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;

import io.github.bluemarlin.Main;
import io.github.bluemarlin.service.ExileToolsLadderService;
import io.github.bluemarlin.util.Dialogs;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * @author thirdy
 *
 */
public class SearchViewPane extends StackPane {
	
	private ObjectProperty<Search> search = new SimpleObjectProperty<>();
	public ObjectProperty<Search> searchProperty() {return search;}
	
	public SearchViewPane() {
		
		ExileToolsLadderService exileToolsLadderService = new ExileToolsLadderService();
		exileToolsLadderService.restart();
		
		// FIXME, hacky
		if (!Main.DEVELOPMENT_MODE) {
			while (exileToolsLadderService.resultProperty().getValue().equals("{}")) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
		// FIXME, hacky
		
		SearchViewRendererCallback blueMarlineCallback = new SearchViewRendererCallback();
		
		if (Main.RAW_RENDERER_ENABLED) {
			RawSearchViewRenderer viewRenderer = new RawSearchViewRenderer(); 
			viewRenderer.searchProperty().bind(search);
			getChildren().add((Node)viewRenderer);
		} else {
			WebView webView = new WebView();
			WebEngine webEngine = webView.getEngine();

			search.addListener((observ, oldVal, newValue) -> {
				if (newValue != null) {
					String renderer = search.getValue().getSearchFile().getRenderer();
					
					if (renderer.startsWith("renderers")) {
						renderer = Paths.get(renderer).toAbsolutePath().toUri().toString();
					}
					webEngine.load(renderer);
				}
			});
			
			webEngine.getLoadWorker().stateProperty().addListener((observ, oldVal, newVal) -> {
				if (newVal.equals(Worker.State.SUCCEEDED)) {
					JSObject window = (JSObject) webEngine.executeScript("window");
					window.setMember("blueMarlineCallback", blueMarlineCallback);
					window.setMember("searchFile", search.getValue().getSearchFile());
					window.setMember("searchResult", search.getValue().getSearchResult());
					window.setMember("ladderOnlinePlayers", exileToolsLadderService.resultProperty().getValue());
					webEngine.executeScript("onBluemarlineReady()");
				}
			});
		
			
			// setup callbacks
			webEngine.setOnAlert(e -> Dialogs.showInfo(e.getData()));
			
			getChildren().add(webView);
		}
		
	}
	
}

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
package io.github.bluemarlin.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bluemarlin.Main;
import io.github.bluemarlin.service.DurianWidgetService;
import io.github.bluemarlin.service.ExileToolsService;
import io.github.bluemarlin.ui.widget.WidgetStage;
import io.github.bluemarlin.util.LangContants;
import io.jexiletools.es.ExileToolsSearchClient;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * @author thirdy
 *
 */
public class BluemarlinApplication extends Application {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static final String BLUEMARLIN_API_KEY = "31db172a8b734e566dfe3211c0752862";
	public static final String VERSION = "Version: 0.1" + (Main.DEVELOPMENT_MODE ? " [DEVELOPMENT MODE]" : LangContants.STRING_EMPTY);
	private static final String TITLE = "Bluemarlin " + VERSION;

    @Override 
    public void start(Stage primaryStage) throws Exception {
    	exileToolsESClient = new ExileToolsSearchClient(BLUEMARLIN_API_KEY);
    	
		BorderPane root = new BorderPane();
		primaryStage.setTitle(TITLE);
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.getIcons().add(new Image("/Atlantic_blue_marlin64x64.png"));
        
        BottomPane bottomPane = new BottomPane();
        root.setBottom(bottomPane);
        
        CenterPane centerPane = new CenterPane();
        root.setCenter(centerPane);
        
        DurianWidgetService durianWidgetService = new DurianWidgetService(centerPane.durianSearchTreeItems());
        
        WidgetStage widget = new WidgetStage(primaryStage, durianWidgetService);
        primaryStage.setOnCloseRequest(e -> widget.close());
        
        if(Main.DURIAN_MODE_ENABLED) durianWidgetService.restart();
        primaryStage.show();
        widget.show();
    }

	@Override
	public void stop() throws Exception {
		super.stop();
		exileToolsESClient.shutdown();
	}

	private static ExileToolsSearchClient exileToolsESClient;

	public static ExileToolsSearchClient getExileToolsESClient() {
		return exileToolsESClient;
	}


}
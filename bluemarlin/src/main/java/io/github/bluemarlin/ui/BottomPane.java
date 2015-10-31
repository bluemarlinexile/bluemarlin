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

import io.github.bluemarlin.service.ExileToolsLastIndexUpdateService;
import io.github.bluemarlin.ui.fx.HBoxSpacer;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * @author thirdy
 *
 */
public class BottomPane extends HBox {
	
	private final ExileToolsLastIndexUpdateService lastIndexUpdateService = new ExileToolsLastIndexUpdateService();

	public BottomPane() {
		Label bottomLabel = new Label("Status: Ready");
		
		Label indexerLastUpdateText = new Label("Indexer Last Update: ");
		Label indexerLastUpdateValueText = new Label();
		indexerLastUpdateValueText.textProperty().bind(lastIndexUpdateService.messageProperty());
		indexerLastUpdateText.textProperty().bind(lastIndexUpdateService.countdownProperty());
		
		getChildren().addAll(bottomLabel, new HBoxSpacer(), indexerLastUpdateText, indexerLastUpdateValueText);
		
		lastIndexUpdateService.setOnSucceeded(e -> lastIndexUpdateService.restart());
		lastIndexUpdateService.setOnFailed	 (e -> lastIndexUpdateService.restart());
		
		lastIndexUpdateService.restart();
	}
}

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
package io.github.bluemarlin.launcher;

import java.io.PrintWriter;
import java.io.StringWriter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

/**
 * @author thirdy
 *
 */
public class Dialogs {
	
	
	public static void showInfo(String string, String title) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(string);
		alert.showAndWait();
	}
	
	public static void showExceptionDialog(Throwable throwable) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("An error occured");
		alert.setHeaderText("Sorry something wrong happened");
//		alert.setGraphic(new ImageView(ImageCache.getInstance().get("/images/gallio/gallio-sad.png")));
		alert.setContentText("Error Message: " + throwable.getMessage());

		// Create expandable Exception.
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception stacktrace was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(false);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(150);
//		GridPane.setVgrow(textArea, Priority.ALWAYS);
//		GridPane.setHgrow(textArea, Priority.ALWAYS);

//		GridPane expContent = new GridPane();
//		expContent.setMaxWidth(Double.MAX_VALUE);
//		expContent.add(label, 0, 0);
//		expContent.add(textArea, 0, 1);

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(new VBox(label, textArea));
		alert.getDialogPane().setExpanded(true);

		alert.showAndWait();
	}

}

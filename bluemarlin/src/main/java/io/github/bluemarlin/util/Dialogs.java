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
package io.github.bluemarlin.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.bluemarlin.ui.BluemarlinApplication;
import io.github.bluemarlin.util.ex.BlackmarketException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author thirdy
 *
 */
public class Dialogs {
	
	private static final Logger logger = LoggerFactory.getLogger(Dialogs.class.getName());

	public static final String[] comics = new String[]{"craft", "sad", "rhoa"};
	
	public static void showExceptionDialog(Throwable throwable) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("An error occured");
		alert.setHeaderText("Sorry something wrong happened");
//		alert.setGraphic(new ImageView(ImageCache.getInstance().get("/images/gallio/gallio-"
//				+ comics[RandomUtils.nextInt(0, 3)] +
//				".png")));
		alert.setGraphic(new ImageView(ImageCache.getInstance().get("/images/gallio/gallio-sad.png")));
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

		// Set expandable Exception into the dialog pane.
		alert.getDialogPane().setExpandableContent(new VBox(label, textArea));
		alert.getDialogPane().setExpanded(true);

		alert.showAndWait();
	}

	public static void showInfo(String string) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("");
		alert.setHeaderText("");
		alert.setContentText(string);
		alert.showAndWait();
	}
	
	public static void showInfo(String string, String title) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText("");
		alert.setContentText(string);
		alert.showAndWait();
	}
	
	public static void showAbout() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setGraphic(new Region());
		alert.setTitle("Bluemarline " + BluemarlinApplication.VERSION);
		alert.setContentText("");
		alert.setHeaderText("");
		
		TextArea textArea = new TextArea("Copyright 2015 Vicente de Rivera III"
				  + "\r\n"
				  + "\r\n http://thirdy.github.io/Bluemarline"
				  + "\r\n"
				  + "\r\n This program is free software; you can redistribute it and/or"
				  + "\r\n modify it under the terms of the GNU General Public License"
				  + "\r\n as published by the Free Software Foundation; either version 2"
				  + "\r\n of the License, or (at your option) any later version."
				  + "\r\n"
				  + "\r\n This program is distributed in the hope that it will be useful,"
				  + "\r\n but WITHOUT ANY WARRANTY; without even the implied warranty of"
				  + "\r\n MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"
				  + "\r\n GNU General Public License for more details."
				  + "\r\n"
				  + "\r\n You should have received a copy of the GNU General Public License"
				  + "\r\n along with this program; if not, write to the Free Software"
				  + "\r\n Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA."
				  + "\r\n"
				);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		
		Hyperlink website = new Hyperlink("Visit Bluemarline Homepage");
		website.setOnAction(e -> {
			try {
				SwingUtil.openUrlViaBrowser("http://thirdy.github.io/Bluemarline/");
			} catch (BlackmarketException ex) {
				logger.error(ex.getMessage(), ex);
			}
		});
		
		Hyperlink exwebsite = new Hyperlink("Visit Exile Tools Homepage");
		exwebsite.setOnAction(e -> {
			try {
				SwingUtil.openUrlViaBrowser("http://exiletools.com/");
			} catch (BlackmarketException ex) {
				logger.error(ex.getMessage(), ex);
			}
		});
		
		VBox content = new VBox(
				new ImageView(new Image("/images/Bluemarline-logo.png")),
				new Label("Bluemarline is fan-made software for Path of Exile but is not affiliated with Grinding Gear Games in any way."),
				new Label("A few tips:"),
				new Label("CTRL + Space - hot key to slide the search control pane"),
				new Label("CTRL + Enter - hot key to run the search"),
				new Label("Advance mode grants you power overwhelming of Elastic Search"),
				new Label("For more information and updates,"),
				website,
				new Label("Bluemarline uses Exile Tools Shop Indexer Elastic Search API:"),
				exwebsite,
				new Label("Comics by /u/gallio"),
				new Label("Bluemarline is 100% Free and Open Source Software under GPLv2:"),
				textArea
				);
		content.setSpacing(8);
		alert.getDialogPane().setContent(content);
		alert.showAndWait();
		

	}
}

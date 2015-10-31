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

import java.io.File;
import java.io.IOException;

import io.github.bluemarlin.ui.searchtree.SearchTreePane;
import io.github.bluemarlin.ui.searchview.SearchViewPane;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;

/**
 * @author thirdy
 *
 */
public class CenterPane extends SplitPane {

	public CenterPane() {
		setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

		SearchTreePane leftSide = new SearchTreePane();
		SearchViewPane rightSide = new SearchViewPane();
		
		rightSide.searchResultProperty().bind(leftSide.searchResultProperty());

		getItems().addAll(leftSide, rightSide);

		setDividerPositions(0.3f, 0.9f);
	}



}

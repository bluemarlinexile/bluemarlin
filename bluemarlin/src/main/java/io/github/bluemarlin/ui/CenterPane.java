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

import java.util.List;

import io.github.bluemarlin.ui.searchtree.SearchTreeItem;
import io.github.bluemarlin.ui.searchtree.SearchTreePane;
import io.github.bluemarlin.ui.searchview.SearchViewPane;
import javafx.scene.control.SplitPane;

/**
 * @author thirdy
 *
 */
public class CenterPane extends SplitPane {
	
	SearchTreePane leftSide = new SearchTreePane();
	SearchViewPane rightSide = new SearchViewPane();

	public CenterPane() {
		setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		
		rightSide.searchProperty().bind(leftSide.searchProperty());

		getItems().addAll(leftSide, rightSide);

		SplitPane.setResizableWithParent(leftSide, Boolean.FALSE);
		setDividerPositions(0.17f);
	}

	public List<SearchTreeItem> durianSearchTreeItems() {
		return leftSide.durianSearchTreeItems();
	}



}

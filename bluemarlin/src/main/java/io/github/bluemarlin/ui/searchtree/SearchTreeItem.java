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
package io.github.bluemarlin.ui.searchtree;

import java.io.File;

import io.github.bluemarlin.service.ExileToolsService;
import io.searchbox.core.SearchResult;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author thirdy
 *
 */
public class SearchTreeItem {
	
	private File file;

	private ObjectProperty<SearchResult> searchResult = new SimpleObjectProperty<>();
	public ObjectProperty<SearchResult> searchResultProperty() {return searchResult;}
	
	private BooleanProperty searchRunning = new SimpleBooleanProperty(false);
	public BooleanProperty searchRunningProperty() { return searchRunning; }
	
	private final ExileToolsService searchService = new ExileToolsService();
	
	public SearchTreeItem(File file) {
		this.file = file;
		searchRunning.bind(searchService.runningProperty());
		searchResult.bind(searchService.searchResultProperty());
	}
	
	public boolean isDirectory() {
		return file.isDirectory();
	}
	
	public void search() {
		searchService.restart();
	}
	
	@Override
	public String toString() {
		return file.getName();
	}

	public String getName() {
		return file.getName();
	}

}

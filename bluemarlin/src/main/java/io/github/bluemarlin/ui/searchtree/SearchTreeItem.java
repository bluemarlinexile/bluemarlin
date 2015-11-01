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
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;

import io.github.bluemarlin.service.ExileToolsService;
import io.github.bluemarlin.ui.searchview.Search;
import io.github.bluemarlin.util.Dialogs;
import javafx.beans.binding.Bindings;
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
	
	private ObjectProperty<Search> search = new SimpleObjectProperty<>();
	public ObjectProperty<Search> searchProperty() {return search;}

	private StringProperty displayName = new SimpleStringProperty();
	public StringProperty displayNameProperty() {return displayName;}
	
	private BooleanProperty searchRunning = new SimpleBooleanProperty(false);
	public BooleanProperty searchRunningProperty() { return searchRunning; }
	
	private BooleanProperty fromDurianSearch = new SimpleBooleanProperty(false);
	public BooleanProperty fromDurianSearchProperty() { return fromDurianSearch; }
	
	private final ExileToolsService searchService = new ExileToolsService();
	
	public SearchTreeItem(File file) {
		this.file = file;
		searchRunning.bind(searchService.runningProperty());
		search.bind(searchService.searchProperty());

		displayName.bind(Bindings.createStringBinding(() -> {
			String name = FilenameUtils.removeExtension(file.getName());
			Search value = search.getValue();
			if (value != null) {
				name += " (" + value.getSearchResult().getTotal() + ")";
				if (value.getSearchResult().getTotal() > 0) {
					if (fromDurianSearch.getValue()) {
						name = "! " + name;
					}
				}
			}
			return name;
		}, search, fromDurianSearch));
		
		searchService.setOnFailed(e -> searchService.getException().printStackTrace());
	}
	
	public boolean isDirectory() {
		return file.isDirectory();
	}
	
	public void search(boolean fromDurianService) {
		if (!searchService.isRunning()) {
			SearchFile sf = null;
			try {
				sf = new SearchFile(file);
				searchService.searchFileProperty().setValue(sf);
				fromDurianSearch.setValue(fromDurianService);
				searchService.restart();
			} catch (IOException e) {
				Dialogs.showExceptionDialog(e);
			}
		}
	}
	
	public ExileToolsService getSearchService() { return searchService; }
	
	@Override
	public String toString() {
		return FilenameUtils.removeExtension(file.getName());
	}

	public boolean isDurianSeachItem() {
		return file.getParentFile().getName().equalsIgnoreCase("durian");
	}

}

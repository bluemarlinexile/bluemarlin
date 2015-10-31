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

import io.searchbox.core.SearchResult;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * @author thirdy
 *
 */
public class RawSearchViewRenderer extends BorderPane implements SearchViewRenderer {
	
	private ObjectProperty<SearchResult> searchResult = new SimpleObjectProperty<>();
	public ObjectProperty<SearchResult> searchResultProperty() {return searchResult;}
	
	public RawSearchViewRenderer() {
		StringBinding binding = Bindings.createStringBinding(() -> {
			SearchResult value = searchResult.getValue();
			String result = "Double click to run search.";
			if (value != null) {
				result = value.getJsonString(); 
			}
			return result;
		}, searchResult);

		TextArea textArea = new TextArea();
		textArea.textProperty().bind(binding);
		
		setCenter(textArea);
	}

}

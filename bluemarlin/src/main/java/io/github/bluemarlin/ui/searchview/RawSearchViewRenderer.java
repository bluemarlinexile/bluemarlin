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

import io.github.bluemarlin.ui.searchtree.SearchFile;
import io.jexiletools.es.SearchResultErrorResult;
import io.searchbox.core.SearchResult;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

/**
 * @author thirdy
 *
 */
public class RawSearchViewRenderer extends BorderPane {
	
	private ObjectProperty<Search> search = new SimpleObjectProperty<>();
	public ObjectProperty<Search> searchProperty() {return search;}
	
	public RawSearchViewRenderer() {
		StringBinding binding = Bindings.createStringBinding(() -> {
			Search value = search.getValue();
			String result = "Double click to run search.";
			if (value != null) {
				if (value.getSearchResult() instanceof SearchResultErrorResult) {
					result = value.getSearchResult().getErrorMessage();
				} else {
					result = value.getSearchResult().getJsonString(); 
				}
			}
			return result;
		}, search);

		TextArea textArea = new TextArea();
		textArea.textProperty().bind(binding);
		
		Label renderer = new Label();
		renderer.textProperty().bind(Bindings.createStringBinding(() -> {
			Search value = search.getValue();
			return value != null ? value.getSearchFile().getRenderer() : ""; 
		}, search));
		
		setTop(renderer);
		setCenter(textArea);
	}

}

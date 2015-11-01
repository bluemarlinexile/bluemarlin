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
package io.jexiletools.es;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.google.gson.Gson;

import io.searchbox.core.SearchResult;

/**
 * @author thirdy
 *
 */
public class SearchResultErrorResult extends SearchResult {

	public SearchResultErrorResult(Exception ex) {
		super((Gson) null);
		this.setErrorMessage(ExceptionUtils.getStackTrace(ex));
	}

}

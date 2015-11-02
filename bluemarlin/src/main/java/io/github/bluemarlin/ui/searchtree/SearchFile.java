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
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import io.github.bluemarlin.util.config.BluemarlinConfig;

/**
 * @author thirdy
 *
 */
public class SearchFile {
	
	private String jsonSearch;
	private String renderer;
	
	public String getJsonSearch() {
		return jsonSearch;
	}
	public String getRenderer() {
		return renderer;
	}
	
	public SearchFile(File file) throws IOException {
		List<String> lines = FileUtils.readLines(file, "UTF-8");
		
		List<String> comments = lines.stream()
				.filter(l -> l.startsWith("`"))
				.collect(Collectors.toList());
		
		renderer = comments.stream()
				.filter(c -> c.contains("$renderer")).findFirst()
				.orElse("default");
		
		if ("default".equals(renderer)) {
			renderer = BluemarlinConfig.defaultRenderer();
		} else {
			renderer = StringUtils.substringAfter(renderer, "=");
		}
		
		int twentyMinsInMillis = 20 * 60 * 1000;
		long timeNowInMillis = (new Date()).getTime();
		String twentyMinsAgoInMillis = String.valueOf( timeNowInMillis - twentyMinsInMillis );
		jsonSearch = lines.stream()
				.filter(l -> !l.startsWith("`"))
				.map(l -> l.replace("$DEFAULT_LEAGUE", BluemarlinConfig.defaultLeague()))
				.map(l -> l.replace("$TWENTY_MINS_AGO_IN_MILLISEC", twentyMinsAgoInMillis))
				.collect(Collectors.joining(System.lineSeparator()));
	}

}

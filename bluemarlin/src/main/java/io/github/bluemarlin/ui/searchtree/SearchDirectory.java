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
import java.net.URISyntaxException;
import java.nio.file.Paths;

import io.github.bluemarlin.Main;
import io.github.bluemarlin.util.CopyFolder;

/**
 * @author thirdy
 *
 */
public class SearchDirectory extends File {
	private static final long serialVersionUID = 1L;

	public SearchDirectory() {
		super("search");
		
//		if (!this.exists() || Main.DEVELOPMENT_MODE) {
//			try {
//				File source = Paths.get(this.getClass().getResource("/default/search").toURI()).toFile();
//				File target = Paths.get(this.toURI()).toFile();
//
//				CopyFolder.copyFolder(source, target);
//			} catch (URISyntaxException e) {
//				throw new RuntimeException(e);
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//		}
		
	}
	

}

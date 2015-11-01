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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import io.github.bluemarlin.Main;

/**
 * @author thirdy
 *
 */
public class DefaultAssets {
	public static void copyDefaultRenderers() throws URISyntaxException {
		File renderersDirectory= new File("renderers");
		System.out.println(renderersDirectory.getAbsolutePath());
		if (!renderersDirectory.exists() || Main.DEVELOPMENT_MODE) {
			System.out.println("NOW COPYING: " + renderersDirectory.getAbsolutePath());
				File source = Paths.get(DefaultAssets.class.getResource("/default/renderers").toURI()).toFile();
				File target = Paths.get(renderersDirectory.toURI()).toFile();
				System.out.println("source: " + source.getAbsolutePath());
				System.out.println("target: " + target.getAbsolutePath());

//				CopyFolder.copyFolder(source, target);
				System.out.println("COPYSUCCECS");
		}
	}
	
	// I think theres a bug with copying audio files
//	public static void copyDefaultAudio() {
//		File targetDirectory= new File(".");
//		if (!targetDirectory.exists() || Main.DEVELOPMENT_MODE) {
//			try {
//				File source = Paths.get(DefaultAssets.class.getResource("/default/audio").toURI()).toFile();
//				File target = Paths.get(targetDirectory.toURI()).toFile();
//				
//				CopyFolder.copyFolder(source, target);
//			} catch (URISyntaxException e) {
//				throw new RuntimeException(e);
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//		}
//	}
}

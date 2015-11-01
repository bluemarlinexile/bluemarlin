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
package io.github.bluemarlin.util.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import io.github.bluemarlin.Main;
import io.github.bluemarlin.ui.BluemarlinApplication;

/**
 *
 * @author thirdy
 *
 */
public class BluemarlinConfig  {
	
	private static final String KEY_WIDGET_WIDTH = "widget.width";
	private static final String KEY_WIDGET_HEIGHT = "widget.height";
	private static final String KEY_BLUEMARLINE_VERSION = "bluemarline.version";
	private static final String KEY_API_LADDER_LEAGUES = "api.ladder.leagues";
	private static Properties properties;
	
	public static void init() throws FileNotFoundException, IOException {
		File configDir = new File("config");
		if(!configDir.exists()) configDir.mkdir();
		File file = new File(configDir , "bluemarlin.properties");
		createIfNotExist(file);
		properties = new Properties();
		FileInputStream fis = new FileInputStream(file);
		properties.load(fis);
		fis.close();
	}

	private static void createIfNotExist(File file) throws IOException {
		if (!file.exists() || Main.DEVELOPMENT_MODE) {
			file.createNewFile();
			Properties defaults = new Properties();
			defaults.setProperty(KEY_BLUEMARLINE_VERSION, BluemarlinApplication.VERSION);
			defaults.setProperty(KEY_API_LADDER_LEAGUES, "flashback2,flashback2hc,standard,hardcore");
			defaults.setProperty(KEY_WIDGET_WIDTH, "130");
			defaults.setProperty(KEY_WIDGET_HEIGHT, "70");
			FileOutputStream fos = new FileOutputStream(file);
			defaults.store(fos, "defaults-auto-generated-by-bluemarline");
			fos.close();
		}
	}
	
	public static List<String> apiLadderLeagues() {
		String ladderLeaguesStr = properties.getProperty(KEY_API_LADDER_LEAGUES);
		return Arrays.asList(ladderLeaguesStr.split(","));
	}

	public static int widgetHeight() {
		String widgetheight = properties.getProperty(KEY_WIDGET_HEIGHT);
		return Integer.parseInt(widgetheight);
	}
	
	public static int widgetWidth() {
		String widgetWidth = properties.getProperty(KEY_WIDGET_WIDTH);
		return Integer.parseInt(widgetWidth);
	}

//	public void save() {
//		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
//		try(FileOutputStream fos = new FileOutputStream(propertiesFile)) {
//			super.store(fos, "BlackmarketProperties.store()");
//		} catch (IOException e) {
//			throw new BlackmarketRuntimeException(e);
//		};
//	}
}

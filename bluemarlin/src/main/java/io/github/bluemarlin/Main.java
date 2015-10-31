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
package io.github.bluemarlin;


import java.io.FileNotFoundException;
import java.io.IOException;

import io.github.bluemarlin.ui.BluemarlinApplication;
import io.github.bluemarlin.util.config.BluemarlinConfig;
import javafx.application.Application;


public class Main {
	
	public static final boolean DEVELOPMENT_MODE = true;

	public static void main(String[] args) throws FileNotFoundException, IOException {
		BluemarlinConfig.init();
        Application.launch(BluemarlinApplication.class, args);
    }

}

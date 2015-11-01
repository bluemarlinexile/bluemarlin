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
package io.github.bluemarlin.ui.widget;

import io.github.bluemarlin.service.DurianWidgetService;
import io.github.bluemarlin.util.config.BluemarlinConfig;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author thirdy
 *
 */
public class WidgetStage extends Stage {
	
	//variables for storing initial position of the stage at the beginning of drag
    private double initX;
    private double initY;

	public WidgetStage(Stage parent, DurianWidgetService durianWidgetService) {
        // INITIALISATION OF THE STAGE/SCENE
		
        //create stage which has set stage style transparent
        super(StageStyle.TRANSPARENT);

        setAlwaysOnTop(true);
        
		int HEIGHT = BluemarlinConfig.widgetHeight();
		int WIDTH  = BluemarlinConfig.widgetWidth();
        
        //create root node of scene, i.e. group
        StackPane rootGroup = new StackPane();
        //create scene with set width, height and color
        Scene scene = new Scene(rootGroup, WIDTH, HEIGHT, Color.TRANSPARENT);
        //set scene to stage
        setScene(scene);
        //center stage on screen
        centerOnScreen();
        

        //create dragger with desired size
        Rectangle dragger = new Rectangle(WIDTH, HEIGHT);
        //fill the dragger with some nice radial background
        dragger.setFill(new RadialGradient(-0.3, 135, 0.5, 0.5, 1, true, CycleMethod.NO_CYCLE, new Stop[] {
            new Stop(0, Color.ANTIQUEWHITE),
            new Stop(1, Color.AQUA)
         }));


        //when mouse button is pressed, save the initial position of screen
        rootGroup.setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                initX = me.getScreenX() - WidgetStage.this.getX();
                initY = me.getScreenY() - WidgetStage.this.getY();
            }
        });

        //when screen is dragged, translate it accordingly
        rootGroup.setOnMouseDragged(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
            	WidgetStage.this.setX(me.getScreenX() - initX);
            	WidgetStage.this.setY(me.getScreenY() - initY);
            }
        });
       
	      Button showBlueMarline = new Button("Bluemarline");
	      showBlueMarline.setOnAction(e -> {
	    	  	  parent.setIconified(false);
	    	  	  parent.toFront();
	          });
	      showBlueMarline.setFont(Font.font(Font.getDefault().getFamily(), 10));
	      showBlueMarline.setPrefSize(80, 5);
	      
	    Label durianResultsFoundLabel = new Label();
	    durianResultsFoundLabel.textProperty().bind(
	    		Bindings.concat("Found: ", durianWidgetService.resultsFoundProperty().asString())
	    		);
        
        Label durianStatusLabel = new Label();
        durianStatusLabel.textProperty().bind(durianWidgetService.messageProperty());
        
        VBox vBox = new VBox();
        vBox.setSpacing(2);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(durianResultsFoundLabel, durianStatusLabel, showBlueMarline);

        //add all nodes to main root group
        rootGroup.getChildren().addAll(dragger, vBox);
        
	}

}

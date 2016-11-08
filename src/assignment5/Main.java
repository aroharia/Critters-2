/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * <Ashvin Roharia>
 * <ar34426>
 * <16475>
 * <Ram Muthukumar>
 * <rm48763>
 * <16470>
 * Slip days used: <0>
 * Fall 2016
 */

package assignment5; // cannot be in default package




import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application{
	static GridPane grid = new GridPane();
	static GridPane modelGrid = new GridPane();
	static int steps =0;
	static boolean shown = false;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage){
		try{
			primaryStage.setTitle("Critter Setup");
			grid.setHgap(5);
			grid.setVgap(5);
			grid.setGridLinesVisible(false);
			grid.setPadding(new Insets(10, 10, 10, 10));
			Scene scene = new Scene(grid, 400, 500);
			Text scenetitle = new Text("Controller");
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
			grid.add(scenetitle, 0, 0, 20, 2);
			
			Stage model = new Stage();
			model.setTitle("Critter World");
			Scene modelScene = new Scene(modelGrid, Params.world_width*(Painter.size+1), Params.world_height*(Painter.size+1));
			model.setScene(modelScene);
        	model.show();
			
			Label name = new Label("Critter:");
			grid.add(name, 1, 9, 2, 1);
			TextField critterType = new TextField();
			grid.add(critterType, 1, 10, 5, 1);
			ObservableList<String> options = 
				    FXCollections.observableArrayList(
				        "Craig",
				        "Algae",
				        "Critter1",
				        "Critter2",
				        "Critter3",
				        "Critter4"
				    );
			ComboBox comboBox = new ComboBox(options);
//			grid.add(comboBox, 1, 10, 5, 1);
//			GridPane.setHalignment(comboBox, HPos.LEFT); // To align horizontally in the cell
//			GridPane.setValignment(comboBox, VPos.BOTTOM); // To align vertically in the cell
			
			

			Label num = new Label("Critter #");
			grid.add(num, 6, 9, 4, 1);
			TextField numCritter = new TextField();
			grid.add(numCritter, 6, 10, 3 ,1);
			Button butt = new Button("Add");
			GridPane.setHalignment(butt, HPos.LEFT); // To align horizontally in the cell
			GridPane.setValignment(butt, VPos.BOTTOM); // To align vertically in the cell
	        grid.add(butt, 9, 10);
	        final Text actiontarget = new Text();
	        grid.add(actiontarget, 1, 8, 10, 1);
			GridPane.setHalignment(actiontarget, HPos.CENTER); // To align horizontally in the cell
			GridPane.setValignment(actiontarget, VPos.BOTTOM); // To align vertically in the cell
			TextArea clist = new TextArea();
			clist.setPromptText("Critters to add next step will appear here");
			clist.setEditable(false);
			clist.setWrapText(true);
			grid.add(clist, 1, 11, 15, 20);
	        butt.setOnAction(new EventHandler<ActionEvent>() {
	       	 
	            @Override
	            public void handle(ActionEvent event) {
	                if((critterType.getText() != null && 
	                        !critterType.getText().isEmpty()) && (numCritter.getText() != null && !numCritter.getText().isEmpty())){
	                    String number = numCritter.getText();
	                    String type = critterType.getText();
	                	if(!(numCritter.getText().matches("^[0-9]+$"))){
		                	actiontarget.setFill(Color.FIREBRICK);
		                    actiontarget.setText("Insert valid number");
	                	}
	                	else{
	                		try{
	                			actiontarget.setText("                   ");
	                			
				                for(int i=0; i < Integer.parseInt(number); i+=1){
	        							Critter.makeCritter(type);
				                }
				                clist.appendText(type + " x " + number + "\n");
	                		}catch(InvalidCritterException|NullPointerException|NumberFormatException exception){
	            					actiontarget.setText("Please input a valid Critter type");
	   
	                		}
	                	}
	                }
	                else{
	                    actiontarget.setFill(Color.FIREBRICK);
	                    actiontarget.setText("Please select number and type of critters");
	                }
	            }
	        });
	        
			Label numStep = new Label("Time Step #");
			grid.add(numStep, 1, 35, 4, 1);
			TextField numSteps = new TextField();
			grid.add(numSteps, 5, 35, 3, 1);
//			Button buttStep = new Button("Add");
//			GridPane.setHalignment(buttStep, HPos.LEFT); // To align horizontally in the cell
//			GridPane.setValignment(buttStep, VPos.BOTTOM); // To align vertically in the cell
//	        grid.add(buttStep, 10, 35);
	        final Text actiontarget1 = new Text();
	        grid.add(actiontarget1, 1, 34, 20, 1);
			GridPane.setHalignment(actiontarget1, HPos.CENTER); // To align horizontally in the cell
			GridPane.setValignment(actiontarget1, VPos.BOTTOM); // To align vertically in the cell
	        
/*	        buttStep.setOnAction(new EventHandler<ActionEvent>() {
		       	 
	            @Override
	            public void handle(ActionEvent event) {
	                if((numSteps.getText() != null && !numSteps.getText().isEmpty())){
	                    String stepNum = numSteps.getText();
	                	if(!(numSteps.getText().matches("^[0-9]+$"))){
		                	actiontarget1.setFill(Color.FIREBRICK);
		                    actiontarget1.setText("Insert valid number");
	                	}
	                	else{
	                		try{
	                			actiontarget1.setText("                   ");
				                for(int i=0; i < Integer.parseInt(stepNum); i+=1){
				                	Critter.worldTimeStep();
				                }
	                		}catch(NullPointerException|NumberFormatException exception){
	            					System.out.println("error processing");
	   
	                		}
	                	}
	                }
	                else{
	                    actiontarget.setFill(Color.FIREBRICK);
	                    actiontarget.setText("Please select number and type of critters");
	                }
	            }
	        }); */
	        
	        
	        
			Button displayButt = new Button("Update World");
			GridPane.setHalignment(displayButt, HPos.LEFT); // To align horizontally in the cell
			GridPane.setValignment(displayButt, VPos.BOTTOM); // To align vertically in the cell
	        grid.add(displayButt, 10, 35);
	        
	        displayButt.setOnAction(new EventHandler<ActionEvent>() {
		       	 
	            @Override
	            public void handle(ActionEvent event) {
	                if((numSteps.getText() != null && !numSteps.getText().isEmpty())){
	                    String stepNum = numSteps.getText();
	                	if(!(numSteps.getText().matches("^[0-9]+$"))){
		                	actiontarget1.setFill(Color.FIREBRICK);
		                    actiontarget1.setText("Insert valid number");
	                	}
	                	else{
	                		try{
	                			actiontarget1.setText("                   ");
				                for(int i=0; i < Integer.parseInt(stepNum); i+=1){
				                	Critter.worldTimeStep();
				                }
	                		}catch(NullPointerException|NumberFormatException exception){
	            					System.out.println("error processing");
	   
	                		}
	                		numSteps.clear();
	    	            	numCritter.clear();
	    	            	clist.clear();
	    	            	comboBox.getSelectionModel().clearSelection();
	    	            	shown = true;
	    	                Critter.displayWorld();
	                	}
	                }
	                else{
	                    actiontarget1.setFill(Color.FIREBRICK);
	                    actiontarget1.setText("Insert number of steps");
	                }
	            	
	            }
	        });
	        
	        
/*	        Label times = new Label("Time: ");
	        grid.add(times, 1, 40, 2, 1);
	        GridPane.setHalignment(times, HPos.LEFT); // To align horizontally in the cell
			GridPane.setValignment(times, VPos.CENTER); // To align vertically in the cell
	        
			Label timesNum = new Label(String.valueOf(steps));
	        grid.add(timesNum, 2, 40, 1, 1);
	        GridPane.setHalignment(timesNum, HPos.LEFT); // To align horizontally in the cell
			GridPane.setValignment(timesNum, VPos.CENTER); // To align vertically in the cell
*/			
	        Label speedL = new Label("Speed: ");
	        grid.add(speedL, 1, 45);
	        GridPane.setHalignment(speedL, HPos.LEFT); // To align horizontally in the cell
			GridPane.setValignment(speedL, VPos.CENTER); // To align vertically in the cell
	        
			Button runButt = new Button("Simulate");
			GridPane.setHalignment(runButt, HPos.LEFT); // To align horizontally in the cell
			GridPane.setValignment(runButt, VPos.BOTTOM); // To align vertically in the cell
	        grid.add(runButt, 9, 50, 1, 1);
	        
	        Button stopper = new Button("Stop");
	        stopper.setDisable(true);
	        grid.add(stopper,  10, 50, 1, 1);
	        
	        
	        Slider slider = new Slider();
	        slider.setMin(1);
	        slider.setMax(10);
	        slider.setValue(5);
	        slider.setShowTickLabels(true);
	        slider.setShowTickMarks(true);
	        slider.setSnapToTicks(true);
	        slider.setMajorTickUnit(1);
	        slider.setMinorTickCount(0);
	        slider.setBlockIncrement(1);
	        grid.add(slider, 2, 45, 10, 2);
			GridPane.setHalignment(slider, HPos.LEFT); // To align horizontally in the cell
			GridPane.setValignment(slider, VPos.BOTTOM); // To align vertically in the cell
			Timeline timeline = new Timeline();
			runButt.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event) {
					if(!shown){
						return;
					}
					stopper.setDisable(false);
					comboBox.setDisable(true);
					numCritter.setDisable(true);
					butt.setDisable(true);
					numSteps.setDisable(true);
					displayButt.setDisable(true);
					runButt.setDisable(true);
					
					timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000/slider.getValue()), 
							new EventHandler<ActionEvent>() {
						
						@Override
						public void handle(ActionEvent event){
							Critter.worldTimeStep();
							Critter.displayWorld();
						}
					}));    
					timeline.setCycleCount(Timeline.INDEFINITE);
					timeline.play();
					
					
				}
			});
			
			stopper.setOnAction(new EventHandler<ActionEvent>() {
				
				@Override
				public void handle(ActionEvent event){
					timeline.stop();
					stopper.setDisable(true);
					comboBox.setDisable(false);
					numCritter.setDisable(false);
					butt.setDisable(false);
					numSteps.setDisable(false);
					displayButt.setDisable(false);
					runButt.setDisable(false);
				}
			});
			
			Button quitButt = new Button("Quit");
			GridPane.setHalignment(quitButt, HPos.RIGHT); // To align horizontally in the cell
			GridPane.setValignment(quitButt, VPos.BOTTOM); // To align vertically in the cell
	        grid.add(quitButt, 10, 1, 1, 1);
	       
	        quitButt.setOnAction(new EventHandler<ActionEvent>() {
		       	 
	            @Override
	            public void handle(ActionEvent event) {
	                System.exit(0);
	            }
	        });
	        
			primaryStage.show();
			primaryStage.setScene(scene);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}

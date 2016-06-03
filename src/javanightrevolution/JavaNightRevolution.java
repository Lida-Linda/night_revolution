/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javanightrevolution;

import javafx.scene.layout.GridPane;
import java.util.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.converter.FloatStringConverter;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.awt.Desktop;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.FileReader;


/**
 *
 * @author admin
 */
public class JavaNightRevolution extends Application {
    private Desktop desktop = Desktop.getDesktop();
    private List<Float> raw_data = new ArrayList<Float>();

    @Override
    public void start(Stage primaryStage) {
        final FileChooser fileChooser = new FileChooser();

        Label label1 = new Label("Result: ");
        TextField resultField = new TextField ();
        HBox resultBox = new HBox();
        resultBox.getChildren().addAll(label1, resultField);
        resultBox.setSpacing(10);
        
        Button openFile = new Button();
        openFile.setText("Open file");
        openFile.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                File file = fileChooser.showOpenDialog(primaryStage);
                if (file != null) {
                    openFile(file);
                    Double result = calculate();
                    String stringResult = Double.toString(result);
                    resultField.setText(stringResult);
                }
            }
        });
        
        Button writeFile = new Button();
        writeFile.setText("Write file");
        writeFile.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
              FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save calculates result");
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null) {
                try {
                    FileWriter wr = new FileWriter(file);
                    wr.write(resultField.getText());
                    wr.close();
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
            }
        });
        
        
        StackPane root = new StackPane();
        
        // show buttons 
        root.getChildren().add(resultBox);
        final GridPane inputGridPane = new GridPane();
        GridPane.setConstraints(openFile, 0, 5);
        GridPane.setConstraints(writeFile, 1, 5);
        inputGridPane.setHgap(6);
        inputGridPane.setVgap(6);
        inputGridPane.getChildren().addAll(openFile, writeFile);
        root.getChildren().add(inputGridPane);

        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("So F Course work!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
     private void openFile(File file) {
        try {
//            desktop.open(file);
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            String line = null;

            while ((line = br.readLine()) != null) {
                float value = Float.parseFloat(line);//FloatStringConverter(str);
                raw_data.add(value);
            }
            br.close();
        } catch (IOException ex) {
            Logger.getLogger(
                JavaNightRevolution.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }
    
     private double calculate() {
         double result = 0;
         for(int i = 0; i < raw_data.size() - 2; i++) {
             result = Math.log(raw_data.get(i + 1) - raw_data.get(i));
         }
         return result;
     }
}

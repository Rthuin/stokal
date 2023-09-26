package com.example.stokal;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class SettingsController extends StokAlController implements Initializable {
    @FXML
    private ChoiceBox<String> choiceProcess;
    @FXML
    private TextField tf1;
    @FXML
    private TextField tf2;
    @FXML
    private TextField tf3;
    private String choice;
    private final String[] option = {"Read","Write"};
    private int period;
    private String dbfName;
    private String tableName;
    private final Import imp = new Import();
    private final Export export = new Export();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        choiceProcess.getItems().addAll(option);
        choiceProcess.setOnAction(this::getChoice);
    }
    public void getChoice(ActionEvent event){
        choice = choiceProcess.getValue();
    }

    @FXML
    public void onStartButtonClick(){
        if(choice == "Write"){
            period = Integer.parseInt(tf3.getText());
            dbfName = tf1.getText();
            tableName = tf2.getText();
            TimerTask repeatedTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        imp.insertdata(dbfName,tableName);
                        Platform.runLater(() -> {
                            int i = imp.getNumberOfRecord();
                            String info = i + " adet veri " + tableName + " tablosuna kaydedildi \n";
                            static_Label.setText(info);
                        });
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                }
            };
            Timer timer = new Timer("Timer");

            long delay = 1000L;

            period = period * 1000;

            timer.scheduleAtFixedRate(repeatedTask,delay,period);
        }
        else {
            period = Integer.parseInt(tf3.getText());
            dbfName = tf1.getText();
            tableName = tf2.getText();
            TimerTask repeatedTask = new TimerTask() {
                @Override
                public void run() {
                    try {
                        export.exportData(dbfName,tableName);
                        Platform.runLater(() -> {
                            int i = export.getNumberOfRecord();
                            String info = i + " adet veri " + dbfName + " dosyasına kaydedildi \n";
                            static_Label.setText(info);
                        });
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
            Timer timer = new Timer("Timer");

            long delay = 1000L;

            period = period * 1000;

            timer.scheduleAtFixedRate(repeatedTask,delay,period);

        }

    }


}

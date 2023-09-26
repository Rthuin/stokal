package com.example.stokal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StokAlController implements Initializable {
    @FXML
    private Label infoLabel = new Label();
    public static Label static_Label;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        static_Label = infoLabel;
    }
    @FXML
    protected void onSettingsButton() throws IOException {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(StokAlController.class.getResource("Settings.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 640, 480);
            stage.setResizable(false);
            stage.setTitle("Settings");
            stage.setScene(scene);
            stage.show();
    }
    @FXML
    public void updateLabelExport(String info){
        Export export = new Export();
        int i = export.getNumberOfRecord();
        String count = String.valueOf(i);
        infoLabel.setText(count + "adet veri " + info + " 'e yazildi");
    }
    @FXML
    public void updateLabelImport(String info){
        Import imp = new Import();
        int i = imp.getNumberOfRecord();
        Label label = new Label();
        String count = String.valueOf(i);
        label.setText(count + "adet veri " + info + " 'e yazildi");

    }



}

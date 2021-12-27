package agh.ics.oop.gui;

import agh.ics.oop.IEngineObserver;
import agh.ics.oop.IMagicEvolutionObserver;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertHandler implements IMagicEvolutionObserver {
    Alert alert;
    public AlertHandler(Stage stage){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Magic evoution happend!");
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.initOwner(stage);
    }
    @Override
    public void magicEvolutionHappend(Integer magicCounter){
        Platform.runLater(() -> {
            alert.setContentText("Magic evolution used only " + magicCounter.toString() + " to go");
            alert.show();}); 
    }
}

package sample;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.fxpart.custom.CustomCtrl;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    CustomCtrl customCtrl;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void click(Event event) {
        System.out.println(" click on my custom ");
    }
}

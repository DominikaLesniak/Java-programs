package sample;

import Others.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class NewTaskController  implements Initializable{
    public Stage dialogStage;
    @FXML private TextField titleInput;
    @FXML private ComboBox priorityCombo;
    @FXML private DatePicker datePick;
    @FXML private TextArea detailsInput;
    @FXML private Button buttonAdd;
    @FXML private AnchorPane ap;
    boolean ifEdit;
    short tabNr;

    public void setDialogStage() {
       dialogStage = (Stage) ap.getScene().getWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ObservableList<String> options = FXCollections.observableArrayList("High", "Average", "Low");
        priorityCombo.setItems(options);
        //priorityCombo.getSelectionModel().selectedItemProperty().addListener((ChangeListener<String>) (observable, oldValue, newValue) -> this.priority= newValue);
        ifEdit = Controller.edition;
        tabNr=Controller.tableNr;

        if(ifEdit) {
            Task editedTask;
            switch (tabNr){
                case 1:
                    editedTask=Controller.observToDoList.get(Controller.index);
                    break;
                case 2:
                    editedTask=Controller.observInProgrList.get(Controller.index);
                    break;
                case 3:
                    editedTask=Controller.observDoneList.get(Controller.index);
                    break;
                default:
                    editedTask=new Task();
            }
            titleInput.setText(editedTask.getName());
            priorityCombo.setValue(editedTask.getPriority());
            datePick.setValue(editedTask.getDate());
            detailsInput.setText(editedTask.getDetails());
        }
    }
    @FXML protected void handleAddNewTask(ActionEvent event) {
        Task tmp = new Task(titleInput.getText(), detailsInput.getText(),
                priorityCombo.getSelectionModel().getSelectedItem().toString(), datePick.getValue());
        if(!ifEdit) {
            Controller.observToDoList.add(tmp);
        }
        else {
            switch (tabNr){
                case 1:
                    Controller.observToDoList.set(Controller.index, tmp);
                    break;
                case 2:
                    Controller.observInProgrList.set(Controller.index, tmp);
                    break;
                case 3:
                    Controller.observDoneList.set(Controller.index, tmp);
                    break;
            }

        }
        setDialogStage();
        dialogStage.close();
    }

}

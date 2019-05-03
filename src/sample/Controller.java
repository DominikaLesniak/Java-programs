package sample;

import Others.FileOperations;
import Others.Task;

import Others.TaskCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Callback;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML MenuItem close;
    @FXML MenuItem reset;
    @FXML MenuItem about;
    @FXML MenuItem save;
    @FXML MenuItem load;
    @FXML MenuItem exportLists;
    @FXML MenuItem importLists;

    @FXML //fx:id="ToDoList";
    private ListView<Task> ToDoList;
    public static ObservableList<Task> observToDoList = FXCollections.observableArrayList();
    @FXML private ListView<Task> InProgressList;
    public static ObservableList<Task> observInProgrList = FXCollections.observableArrayList();
    @FXML private ListView<Task> DoneList;
    public static ObservableList<Task> observDoneList = FXCollections.observableArrayList();
    @FXML private AnchorPane anchorPane;
    public Window window;
    static public int index;
    static public boolean edition;
    static public short tableNr;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listInit(ToDoList,observToDoList,1);
        listInit(InProgressList, observInProgrList, 2);
        listInit(DoneList, observDoneList, 3);
      //  setDialogStage();
        setMenuItems();
    }
    public void moveToToDo(ListView thisList, Task movedTask){
        thisList.getItems().remove(movedTask);
        observToDoList.add(movedTask);
    }
    public void moveToInProgress(ListView thisList, Task movedTask){
        thisList.getItems().remove(movedTask);
        observInProgrList.add(movedTask);
    }
    public void moveToDone(ListView thisList, Task movedTask){
        thisList.getItems().remove(movedTask);
        observDoneList.add(movedTask);
    }
    public void addingNewTask() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("AddNewTaskScene.fxml"));
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add new task");
        dialogStage.initStyle(StageStyle.UTILITY);
        dialogStage.setScene(new Scene(root,550,400));

        dialogStage.show();
    }

    public void addDefaultTask(ActionEvent event) {
        edition=false;
        try{
            addingNewTask();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void listInit(ListView<Task> listView, ObservableList<Task> observableList, int tabNr){
        listView.setItems(observableList);

        listView.setCellFactory((Callback<ListView<Task>, ListCell<Task>>) list -> {

            ListCell<Task> cell = new TaskCell();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem editItem = new MenuItem("edit");
            editItem.setOnAction(event -> {
                if(listView.getId().equals("ToDoList")){
                    tableNr=1;
                }
                else if(listView.getId().equals("InProgressList")){
                    tableNr=2;
                }
                else if(listView.getId().equals("DoneList")) {
                    tableNr=3;
                }
                index = cell.getIndex();
                edition=true;
                try{
                    addingNewTask();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                edition=false;
            });
            MenuItem deleteItem = new MenuItem("delete");
            deleteItem.setOnAction(event -> {
                Task thisTask = cell.getItem();
                listView.getItems().remove(thisTask);
            });
            MenuItem moveToInProgress = new MenuItem("move to IN PROGRESS");
            moveToInProgress.setOnAction(event -> {
                moveToInProgress(listView,cell.getItem());
                cell.setStyle("-fx-control-inner-background:#fbfbff;");
            });
            MenuItem moveToDone = new MenuItem("move to DONE");
            moveToDone.setOnAction(event -> {
                moveToDone(listView,cell.getItem());
                cell.setGraphic(null);
            });
            MenuItem moveToToDo = new MenuItem("move to TO DO");
            moveToToDo.setOnAction(event -> {
                moveToToDo(listView,cell.getItem());
                cell.setGraphic(null);
            });
            contextMenu.getItems().addAll(editItem, deleteItem);
            switch(tabNr){
                case 1:
                    contextMenu.getItems().addAll(moveToInProgress, moveToDone);
                    break;
                case 2:
                    contextMenu.getItems().addAll(moveToToDo,moveToDone);
                    break;
                case 3:
                    contextMenu.getItems().addAll(moveToInProgress, moveToToDo);
                    break;
            }
            cell.setOnMouseEntered(event -> {
                if(cell.getIndex()< observableList.size()){
                    final Tooltip tooltip = new Tooltip();
                    tooltip.setText("Priority: "+ observableList.get(cell.getIndex()).getPriority() + ", Exp date: "+
                            observableList.get(cell.getIndex()).getDate()+"\n details: "+ observableList.get(cell.getIndex()).getDetails());
                    cell.setTooltip(tooltip);
                }
            });
            cell.setOnMouseClicked(mouseEvent -> {
                if(cell.getItem()!=null) {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell;
        });
    }

    public void setMenuItems(){
        close.setOnAction(actionEvent -> System.exit(0));

        reset.setOnAction(actionEvent -> {
            observToDoList.clear();
            observInProgrList.clear();
            observDoneList.clear();
        });
        about.setOnAction(actionEvent -> {
            Alert dialog = new Alert(Alert.AlertType.INFORMATION);
            dialog.setTitle("Informations ");
            dialog.setHeaderText("");
            dialog.setContentText("Author: Dominika Lesniak \n Program is my implementation of Kanban Table");

            dialog.showAndWait();
        });

        save.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text file",  "*.txt"),
                    new FileChooser.ExtensionFilter("Csv file",  "*.csv"),
                    new FileChooser.ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());

            if (selectedFile != null) {
                FileOperations.saveFile(observToDoList,observInProgrList,observDoneList,selectedFile);
            }
        });
        load.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open saved file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Text files",  "*.txt"),
                    new FileChooser.ExtensionFilter("Csv file",  "*.csv"),
                    new FileChooser.ExtensionFilter("All files",  "*.*"));
            File selectedFile = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());

            if(selectedFile != null){
                FileOperations.loadFile(selectedFile);
            }

        });
        exportLists.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Export lists to csv file");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Csv file",  "*.csv"));
            File selectedFile = fileChooser.showSaveDialog(anchorPane.getScene().getWindow());
            if (selectedFile != null) {
                FileOperations.exportFile(selectedFile);
            }
        });
        importLists.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose file to import");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Csv files",  "*.csv"));
            File selectedFile = fileChooser.showOpenDialog(anchorPane.getScene().getWindow());

            if(selectedFile != null){
                FileOperations.importFile(selectedFile);
            }
        });
    }
}

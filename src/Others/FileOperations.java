package Others;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import sample.Controller;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FileOperations {

    public static void saveFile(ObservableList<Task> observableToDoList, ObservableList<Task> observableInProgressList,
                                ObservableList<Task> observableDoneList, File fileName) {
        SerializableLists serializableList = new SerializableLists(observableToDoList,observableInProgressList,observableDoneList);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)));
                outputStream.writeObject(serializableList);
                outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void loadFile(File fileName){
        SerializableLists loadedLists;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            loadedLists = (SerializableLists) inputStream.readObject();
            inputStream.close();
            //System.out.println(loadedLists.getToDoList().get(0).getPriority());

            Controller.observToDoList.setAll(loadedLists.getToDoList());
            Controller.observInProgrList.setAll(loadedLists.getInProgressList());
            Controller.observDoneList.setAll(loadedLists.getDoneList());


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void exportFile(File fileName){
        try {
            Writer writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("To do list: \n");
            for (Task task : Controller.observToDoList) {

                String text = task.getName() + "," + task.getPriority() + "," + task.getDate() + "," + task.getDetails() + "\n";
                writer.write(text);
            }
            writer.write("In progress list: \n");
            for (Task task : Controller.observInProgrList) {

                String text = task.getName() + "," + task.getPriority() + "," + task.getDate() + "," + task.getDetails() + "\n";
                writer.write(text);
            }
            writer.write("Done list: \n");
            for (Task task : Controller.observDoneList) {

                String text = task.getName() + "," + task.getPriority() + "," + task.getDate() + "," + task.getDetails() + "\n";
                writer.write(text);
            }
            writer.flush();
            writer.close();
        } catch (Exception ex) {
                ex.printStackTrace();
        }
    }
    public static void importFile(File fileName){
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int listNr = 0;
            while(true){
                line = bufferedReader.readLine();
                if(line==null)
                    break;
                if(line.contains("To do list")){
                    listNr=1;
                }
                else if (line.contains("In progress list")) {
                    listNr = 2;
                }
                else if (line.contains("Done list"))
                {
                    listNr = 3;
                }
                else {
                    String[] parts;
                    if(line.contains(",")) {
                        parts = line.split(",");
                    }
                    else if(line.contains(";")){
                        parts = line.split(";");
                    }
                    else{
                        Alert dialog = new Alert(Alert.AlertType.ERROR);
                        dialog.setTitle("Error ");
                        dialog.setHeaderText("Wrong file format");
                        dialog.setContentText("correct format is: 'task_name, priority, exp_date, details'");
                        dialog.showAndWait();
                        bufferedReader.close();
                        fileReader.close();
                        break;
                    }
                    String details = "";
                    LocalDate date = null;
                    if(parts.length>2) {
                        if(!parts[2].isBlank())
                        date = LocalDate.parse(parts[2]);
                    }
                    if(parts.length>3)
                        details=parts[3];
                    Task task = new Task(parts[0],details,parts[1],date);
                    switch (listNr){
                        case 1:
                            Controller.observToDoList.add(task);
                            break;
                        case 2:
                            Controller.observInProgrList.add(task);
                            break;
                        case 3:
                            Controller.observDoneList.add(task);
                            break;
                        default:
                            System.err.println("bad");
                            break;
                    }
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
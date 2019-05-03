package Others;

import javafx.collections.ObservableList;
import sample.Controller;

import java.io.Serializable;
import java.util.ArrayList;

public class SerializableLists implements Serializable {
    private ArrayList<Task> toDoList;
    private ArrayList<Task> inProgressList;
    private ArrayList<Task> doneList;

    public SerializableLists(ObservableList<Task> observableToDoList, ObservableList<Task> observableInProgressList,
                             ObservableList<Task> observableDoneList) {
        this.toDoList = new ArrayList<>();
        toDoList.addAll(observableToDoList);
        this.inProgressList = new ArrayList<>();
        inProgressList.addAll(observableInProgressList);
        this.doneList = new ArrayList<>();
        doneList.addAll(observableDoneList);
    }

    public SerializableLists() {
        toDoList = new ArrayList<>();
        inProgressList = new ArrayList<>();
        doneList = new ArrayList<>();
    }

    public ArrayList<Task> getToDoList() {
        return toDoList;
    }

    public ArrayList<Task> getInProgressList() {
        return inProgressList;
    }

    public ArrayList<Task> getDoneList() {
        return doneList;
    }
}

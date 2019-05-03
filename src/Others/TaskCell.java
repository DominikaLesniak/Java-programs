package Others;

import Others.Task;
import javafx.scene.control.ListCell;

public class TaskCell extends ListCell<Task>{

    public void updateItem (Task item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            if(item.getPriority().equals("High")){
                setStyle("-fx-control-inner-background:#ffe1dd;");
            }
            else if(item.getPriority().equals("Average")){
                setStyle("-fx-control-inner-background:#feffa8;");
            }
            else {
                setStyle("-fx-control-inner-background:#d7ffb8;");
            }
            setText(item.getName());

        }
        else{
            setText(null);
            setGraphic(null);
            setStyle(null);
        }
    }
}

package Others;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private String name;
    private String details;
    private String priority;
    private LocalDate date;

    public Task() {
        this.name =new String("");
        this.details = new String("");
        this.priority = new String("");
        this.date = null;
    }
    public Task(String name, String details, String priority, LocalDate date) {
        this.name =name;
        this.details = details;
        this.priority = priority;
        this.date=date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name=name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details=details;
    }

    public String getPriority() {
        return priority;
    }


    public void setPriority(String priority) {
        this.priority=priority;
    }

    public LocalDate getDate() {
        return date;
    }


    public void setDate(LocalDate date) {
        this.date=date;
    }
}

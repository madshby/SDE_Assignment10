package sample.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Task {
    private StringProperty description;
    private ObjectProperty<LocalDate> dueDate;
    private BooleanProperty isCompleted;

    public Task() {
        this("");
    }

    public Task(String newDescription) {
        this.description = new SimpleStringProperty();
        this.setDescription(newDescription);

        this.dueDate = new SimpleObjectProperty<LocalDate>();
        this.setDueDate(LocalDate.now().plusDays(2));

        this.isCompleted = new SimpleBooleanProperty();
        this.setIsCompleted(false);
    }

    public StringProperty getDescriptionProperty() {
        return this.description;
    }

    public ObjectProperty<LocalDate> getDueDateProperty() {
        return this.dueDate;
    }

    public BooleanProperty getIsCompletedProperty() {
        return this.isCompleted;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public LocalDate getDueDate() {
        return dueDate.get();
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate.set(dueDate);
    }

    public boolean getIsCompleted() {
        return this.isCompleted.get();
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted.set(isCompleted);
    }

    public void postpone(int noDays) {
        this.setDueDate( this.getDueDate().plusDays(noDays) );
    }

    public void complete() {
        this.setIsCompleted(true);
    }

    public static void saveTasks(ObservableList<Task> tasks) {
        //TODO
    }

    public static ObservableList<Task> loadTasks() {
        //TODO
        return FXCollections.observableArrayList();
    }
}

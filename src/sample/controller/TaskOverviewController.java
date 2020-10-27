package sample.controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import sample.model.Task;

import javax.xml.bind.JAXBException;
import java.time.LocalDate;

public class TaskOverviewController {
    private final String NEW_TASK_HEADER_TEXT = "Create new task";
    private final String NEW_TASK_CONTENT_TEXT = "Please add a description";
    private final String MISSING_SELECTION_HEADER = "No task selected!";
    private final String MISSING_SELECTION_CONTENT = "Please select a task before using this button.";
    private final String ERROR_MESSAGE = "An error occured!";

    private ObservableList<Task> tasks;
    private Task selectedTask;

    @FXML
    private TableView<Task> taskTable;

    @FXML
    private TableColumn<Task,String> descriptionColumn;

    @FXML
    private TableColumn<Task,LocalDate> dueDateColumn;

    @FXML
    private TableColumn<Task,Boolean> isCompletedColumn;

    @FXML
    public void initialize() {
        this.loadTasks();

        this.taskTable.setItems(this.tasks);
        this.taskTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.selectedTask = newValue;
        });

        this.descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
        this.dueDateColumn.setCellValueFactory(cellData -> cellData.getValue().getDueDateProperty());
        this.isCompletedColumn.setCellValueFactory(cellData -> cellData.getValue().getIsCompletedProperty());
    }

    private void loadTasks() {
        try {
            this.tasks = Task.loadTasks();
        } catch (JAXBException ex) {
            showError(ex);
        }
    }

    private void saveTasks() {
        try {
            Task.saveTasks(this.tasks);
        } catch (JAXBException ex) {
            showError(ex);
        }
    }

    @FXML
    public void addTaskClicked() {
        TextInputDialog input = new TextInputDialog();
        input.setHeaderText(NEW_TASK_HEADER_TEXT);
        input.setTitle(NEW_TASK_HEADER_TEXT);
        input.setContentText(NEW_TASK_CONTENT_TEXT);

        String newDescription = input.showAndWait().get();

        this.tasks.add( new Task(newDescription) );
        this.saveTasks();
    }

    @FXML
    public void postpone1DayClicked() {
        if(this.selectedTask == null) {
            this.showMissingSelectionWarning();
            return;
        }

        this.selectedTask.postpone(1);
        this.saveTasks();
    }

    @FXML
    public void postpone1MonthClicked() {
        if(this.selectedTask == null) {
            this.showMissingSelectionWarning();
            return;
        }

        this.selectedTask.postpone(30);
        this.saveTasks();
    }

    @FXML
    public void completeTaskClicked() {
        if(this.selectedTask == null) {
            this.showMissingSelectionWarning();
            return;
        }

        this.selectedTask.complete();
        this.saveTasks();
    }

    private void showMissingSelectionWarning() {
        Alert missingAlert = new Alert(Alert.AlertType.WARNING);
        missingAlert.setHeaderText(MISSING_SELECTION_HEADER);
        missingAlert.setContentText(MISSING_SELECTION_CONTENT);

        missingAlert.showAndWait();
    }

    private void showError(Exception ex) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(ERROR_MESSAGE);
        errorAlert.setContentText(ex.getMessage());

        errorAlert.showAndWait();
    }
}

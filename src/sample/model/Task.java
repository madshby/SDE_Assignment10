package sample.model;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.util.LocalDateAdapter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.File;
import java.time.LocalDate;

public class Task {
    private final StringProperty description;
    private final ObjectProperty<LocalDate> dueDate;
    private final BooleanProperty isCompleted;

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

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
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

    public static void saveTasks(ObservableList<Task> tasks) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(TaskListWrapper.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        TaskListWrapper wrapper = new TaskListWrapper();
        wrapper.setTasks(tasks);
        m.marshal(wrapper, new File("Tasks.xml"));
    }

    public static ObservableList<Task> loadTasks() throws JAXBException {
        File file = new File("Tasks.xml");
        if (!file.exists()) {
            return FXCollections.observableArrayList();
        }
        JAXBContext context = JAXBContext.newInstance(TaskListWrapper.class);
        Unmarshaller um = context.createUnmarshaller();
        TaskListWrapper wrapper = (TaskListWrapper)um.unmarshal(file);
        return FXCollections.observableArrayList(wrapper.getTasks());
    }
}

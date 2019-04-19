package model;

import java.util.ArrayList;

public class Lane {

    private String description;

    private ArrayList<String> references = new ArrayList<String>();

    private Employee employee;

    private ArrayList<Task> tasks = new ArrayList<Task>();

    public void addTask(Task task){tasks.add(task);}

    public void addRefernece(String s){references.add(s);}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<String> references) {
        this.references = references;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "Lane{" +
                "description='" + description + '\'' +
                ", references=" + references +
                ", employee=" + employee +
                ", tasks=" + tasks +
                '}';
    }
}

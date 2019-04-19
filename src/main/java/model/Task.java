package model;

import java.util.ArrayList;

public class Task {
    private String type;
    private String name;
    private String id;
    private String description;
    private ArrayList<Task> origins = new ArrayList<Task>();
    private ArrayList<Task> destinations = new ArrayList<Task>();

    public void addOrigin(Task task){
        origins.add(task);
    }

    public void addDestination(Task task){
        destinations.add(task);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<Task> getOrigins() {
        return origins;
    }

    public void setOrigins(ArrayList<Task> origins) {
        this.origins = origins;
    }

    public ArrayList<Task> getDestinations() {
        return destinations;
    }

    public void setDestinations(ArrayList<Task> destinations) {
        this.destinations = destinations;
    }

    @Override
    public String toString() {
        return "Task{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", origins=" + origins +
                ", destinations=" + destinations +
                '}';
    }
}

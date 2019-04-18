package model;

public class Employee {
    private String name = "";
    private String description = "";

    public Employee() {
    }

    public Employee(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

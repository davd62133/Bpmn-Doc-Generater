package model;

public class Employee {
    private String value = "";
    private String description = "";

    public Employee() {
    }

    public Employee(String value, String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
                "value='" + value + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

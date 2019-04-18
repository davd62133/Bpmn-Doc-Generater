package model;

import java.util.ArrayList;

public class Process {
    private ArrayList<Employee> employees = new ArrayList<Employee>();

    private String name;

    private String description;

    public void addEmployee(Employee employee){
        employees.add(employee);
    }

    public String print() {
        String s = "Process{";
        for(Employee e:employees){
            s+=e.toString();
        }
        return s+"}";
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
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
        return "Process{" +
                "employees=" + employees +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

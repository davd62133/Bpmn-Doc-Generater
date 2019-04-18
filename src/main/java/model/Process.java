package model;

import java.util.ArrayList;

public class Process {

    private ArrayList<Employee> employees = new ArrayList<Employee>();

    private ArrayList<Lane> lanes = new ArrayList<Lane>();

    private String name;

    private String description;

    public void addEmployee(Employee employee){employees.add(employee);}

    public void addLane(Lane lane){
        lane.setEmployee(employees.get(lanes.size()));
        lanes.add(lane);
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

    public ArrayList<Lane> getLanes() {
        return lanes;
    }

    public void setLanes(ArrayList<Lane> lanes) {
        this.lanes = lanes;
    }

    @Override
    public String toString() {
        return "Process{" +
                "employees=" + employees +
                ", lanes=" + lanes +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

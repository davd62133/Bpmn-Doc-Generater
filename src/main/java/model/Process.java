package model;

import java.util.ArrayList;

public class Process {
    private static ArrayList<Employee> employees = new ArrayList<Employee>();

    public static void addEmployee(Employee employee){
        employees.add(employee);
    }

    public static String print() {
        String s = "Process{";
        for(Employee e:employees){
            s+=e.toString();
        }
        return s+"}";
    }
}

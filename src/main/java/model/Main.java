package model;

import java.util.ArrayList;

public class Main {
    private ArrayList<Process> processes = new ArrayList<Process>();

    public void addProcess(Process process){
        processes.add(process);
    }

    public ArrayList<Process> getProcesses() {
        return processes;
    }

    @Override
    public String toString() {
        return "Main{" +
                "processes=" + processes +
                '}';
    }
}

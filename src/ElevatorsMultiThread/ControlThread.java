package ElevatorsMultiThread;

import java.util.ArrayList;

public class ControlThread {

    private ArrayList<Floor> floors;
    private ArrayList<ElevatorThread> elevatorThreads;
    private ArrayList<Elevator> elevators;

    public ControlThread(ArrayList<Floor> floors, ArrayList<ElevatorThread> elevatorThreads, ArrayList<Elevator> elevators) {
        this.floors = floors;
        this.elevatorThreads = elevatorThreads;
        this.elevators = elevators;
        Thread controlThread = new Thread(() -> {
            control();
        });
        controlThread.start();
    }

    private void control() {

        while (true) {
            try {
                int peopleCount = getPeopleCount();
                int activeElevatorCount = getActiveElevatorCount();
                if (peopleCount > activeElevatorCount * 20) {
                    open();
                } else if ((peopleCount < activeElevatorCount * 10) && activeElevatorCount != 1) {
                    close();
                }
                Thread.sleep(20);
                
            } catch (Exception e) {
                System.out.println("ControlThread.java" );
            }
        }
    }

    public ArrayList<Floor> getFloors() {
        return floors;
    }

    public void setFloors(ArrayList<Floor> floors) {
        this.floors = floors;
    }

    public ArrayList<ElevatorThread> getElevatorThreads() {
        return elevatorThreads;
    }

    public void setElevatorThreads(ArrayList<ElevatorThread> elevatorThreads) {
        this.elevatorThreads = elevatorThreads;
    }

    public ArrayList<Elevator> getElevators() {
        return elevators;
    }

    public void setElevators(ArrayList<Elevator> elevators) {
        this.elevators = elevators;
    }

    private  int getPeopleCount() {
        int result = 0;
        for (Floor floor : floors) {
            result = result + floor.getFloorQueueCount();
        }
        return result;
    }

    private  int getActiveElevatorCount() {
        int result = 0;
        for (Elevator elevator : elevators) {
            if (elevator.isMode()) {
                result++;
            }
        }
        return result;
    }

    private  void close() {
        for (int i = 4; i > 0; i--) {
            if (elevators.get(i).isMode()) {
                elevatorThreads.get(i).stopThread();
                break;
            }

        }
    }

    private  void open() {
        for (int i = 0; i < 5; i++) {
            if (!(elevators.get(i).isMode())) {
                elevatorThreads.get(i).startThread();
                break;
            }

        }
    }

}

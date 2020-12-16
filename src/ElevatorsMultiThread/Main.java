
package ElevatorsMultiThread;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class Main {

    public static void main(String[] args) {

        ArrayList<Floor> floors = new ArrayList<>(5);
        ArrayList<Elevator> elevators = new ArrayList<>(5);
        ArrayList<ElevatorThread> elevatorThreads = new ArrayList<>(5); // 

        for (int i = 0; i < 5; i++) {
            floors.add(new Floor(0, i, new ArrayBlockingQueue<>(999)));
            elevators.add(new Elevator(i + 1, false, 0, 0, false, 10, 0, new ArrayList<>(), false, floors));
            elevatorThreads.add(new ElevatorThread(elevators.get(i)));
        }
        LoginThread login = new LoginThread(floors.get(0).getFloorQueue());
        ExitThread exit = new ExitThread(floors);
        ControlThread control = new ControlThread(floors, elevatorThreads, elevators);

        new Thread(
                () -> {
                    try {
                        while (true) {
                            System.out.println("0. floor : queue :" + floors.get(0).getFloorQueueCount());
                            System.out.println("1. floor :all : " + floors.get(1).getInsideCount() + " queue : " + floors.get(1).getFloorQueueCount());
                            System.out.println("2. floor :all : " + floors.get(2).getInsideCount() + " queue : " + floors.get(2).getFloorQueueCount());
                            System.out.println("3. floor :all : " + floors.get(3).getInsideCount() + " queue : " + floors.get(3).getFloorQueueCount());
                            System.out.println("4. floor :all : " + floors.get(4).getInsideCount() + " queue : " + floors.get(4).getFloorQueueCount());
                            System.out.println("exit count : " + floors.get(0).getInsideCount());
                            System.out.println("Total waitingCount: " + getPeopleCount(floors));

                            for (Elevator elevator : elevators) {
                                System.out.println("");
                                System.out.println(elevator.getId() + " active : " + elevator.isActive());
                                System.out.println("\t \t mode : " + String.valueOf(elevator.isMode() ? "working" : "idle"));
                                System.out.println("\t \t floor: " + elevator.getFloor());
                                System.out.println("\t \t destination : " + elevator.getDestination());
                                System.out.println("\t \t direction : " + String.valueOf(elevator.isDirection() ? "up" : "down"));
                                System.out.println("\t \t capacity : " + elevator.getCapasity());
                                System.out.println("\t \t count_inside : " + elevator.getInsideCount());
                                System.out.println("\t \t inside : " + elevator.getInsideQueue());
                            }
                            System.out.println("");
                            for (int i = 0; i < 5; i++) {
                                System.out.println(i + ". floor : " + floors.get(i).getFloorQueue());
                            }

                            System.out.println("");
                            System.out.println("----------------------------------------------------");
                            Thread.sleep(200);

                        }
                    } catch (InterruptedException e) {
                        System.out.println("main ");
                    }
                }
        ).start();
    }

    private static int getPeopleCount(ArrayList<Floor> floors) {
        int result = 0;
        for (Floor floor : floors) {
            result = result + floor.getFloorQueueCount();
        }
        return result;
    }

}

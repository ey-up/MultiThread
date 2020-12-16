package ElevatorsMultiThread;

public class Element {

    private int floorNo;
    private int peopleCount;

    public Element(int peopleCount, int targetFloor) {
        this.peopleCount = peopleCount;
        this.floorNo = targetFloor;
    }

    public void setPeopleCount(int peopleCount) {
        this.peopleCount = peopleCount;
    }

    public int getTargetFloor() {
        return floorNo;
    }

    public int getPeopleCount() {
        return peopleCount;
    }

    public void setTargetFloor(int targetFloor) {
        this.floorNo = targetFloor;
    }

    @Override
    public String toString() {
        return "[" + peopleCount + ", " + floorNo + "]";
    }
}

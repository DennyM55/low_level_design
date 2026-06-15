package _13_code_parking_lot;

public class ParkingSlot {

    private final String slotId;
    private final SlotType slotType;
    private boolean occupied;
    private Vehicle parkedVehicle;

    public ParkingSlot(String slotId, SlotType slotType) {
        this.slotId = slotId;
        this.slotType = slotType;
        this.occupied = false;
    }

    public String getSlotId() {
        return slotId;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public boolean isAvailable() {
        return !occupied;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.parkedVehicle = vehicle;
        this.occupied = true;
    }

    public void freeSlot() {
        this.parkedVehicle = null;
        this.occupied = false;
    }

    public Vehicle getParkedVehicle() {
        return parkedVehicle;
    }

    @Override
    public String toString() {
        return "Slot[" + slotId + ", " + slotType + ", " + (occupied ? "OCCUPIED" : "FREE") + "]";
    }
}

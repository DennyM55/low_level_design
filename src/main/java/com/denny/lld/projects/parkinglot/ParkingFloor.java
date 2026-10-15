package com.denny.lld.projects.parkinglot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ParkingFloor {

    private final int floorNumber;
    private final List<ParkingSlot> slots;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.slots = new ArrayList<>();
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void addSlot(ParkingSlot slot) {
        slots.add(slot);
    }

    public Optional<ParkingSlot> getAvailableSlot(VehicleType vehicleType) {
        SlotType required = mapToSlotType(vehicleType);
        return slots.stream()
                .filter(s -> s.getSlotType() == required && s.isAvailable())
                .findFirst();
    }

    private SlotType mapToSlotType(VehicleType vehicleType) {
        switch (vehicleType) {
            case BIKE:  return SlotType.TWO_WHEELER;
            case CAR:   return SlotType.COMPACT;
            case TRUCK: return SlotType.LARGE;
            default:    throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }

    public List<ParkingSlot> getSlots() {
        return slots;
    }
}

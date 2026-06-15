package _13_code_parking_lot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class ParkingLot {

    private static ParkingLot instance;

    private final String name;
    private final List<ParkingFloor> floors;
    private final Map<String, Ticket> activeTickets;
    private final FeeStrategy feeStrategy;

    private ParkingLot(String name, FeeStrategy feeStrategy) {
        this.name = name;
        this.floors = new ArrayList<>();
        this.activeTickets = new HashMap<>();
        this.feeStrategy = feeStrategy;
    }

    public static synchronized ParkingLot getInstance(String name, FeeStrategy feeStrategy) {
        if (instance == null) {
            instance = new ParkingLot(name, feeStrategy);
        }
        return instance;
    }

    // Allows resetting for testing purposes
    static void resetInstance() {
        instance = null;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public Ticket issueTicket(Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            Optional<ParkingSlot> availableSlot = floor.getAvailableSlot(vehicle.getVehicleType());
            if (availableSlot.isPresent()) {
                ParkingSlot slot = availableSlot.get();
                slot.parkVehicle(vehicle);
                String ticketId = "TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                Ticket ticket = new Ticket(ticketId, vehicle, slot);
                activeTickets.put(ticketId, ticket);
                System.out.println("Ticket issued: " + ticket);
                return ticket;
            }
        }
        throw new IllegalStateException("No available slot for vehicle type: " + vehicle.getVehicleType());
    }

    public double processExit(String ticketId) {
        Ticket ticket = activeTickets.get(ticketId);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket not found: " + ticketId);
        }
        ticket.setExitTime(LocalDateTime.now());
        double fee = feeStrategy.calculateFee(ticket);
        ticket.getParkingSlot().freeSlot();
        activeTickets.remove(ticketId);
        System.out.println("Vehicle " + ticket.getVehicle() + " exited. Fee: Rs. " + fee);
        return fee;
    }

    public String getName() {
        return name;
    }

    public void printStatus() {
        System.out.println("=== " + name + " Status ===");
        for (ParkingFloor floor : floors) {
            System.out.println("Floor " + floor.getFloorNumber() + ":");
            for (ParkingSlot slot : floor.getSlots()) {
                System.out.println("  " + slot);
            }
        }
    }
}

package com.denny.lld.projects.parkinglot;

public class Main {

    public static void main(String[] args) {
        ParkingLot.resetInstance();
        ParkingLot lot = ParkingLot.getInstance("City Center Parking", new HourlyFeeStrategy());

        // Set up floors and slots
        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addSlot(new ParkingSlot("F1-B1", SlotType.TWO_WHEELER));
        floor1.addSlot(new ParkingSlot("F1-B2", SlotType.TWO_WHEELER));
        floor1.addSlot(new ParkingSlot("F1-C1", SlotType.COMPACT));
        floor1.addSlot(new ParkingSlot("F1-C2", SlotType.COMPACT));
        floor1.addSlot(new ParkingSlot("F1-L1", SlotType.LARGE));

        ParkingFloor floor2 = new ParkingFloor(2);
        floor2.addSlot(new ParkingSlot("F2-C1", SlotType.COMPACT));
        floor2.addSlot(new ParkingSlot("F2-C2", SlotType.COMPACT));
        floor2.addSlot(new ParkingSlot("F2-L1", SlotType.LARGE));

        lot.addFloor(floor1);
        lot.addFloor(floor2);

        lot.printStatus();
        System.out.println();

        // Park vehicles
        Vehicle bike = new Bike("MH-12-AB-1234");
        Vehicle car = new Car("MH-12-CD-5678");
        Vehicle truck = new Truck("MH-12-EF-9999");

        Ticket bikeTicket = lot.issueTicket(bike);
        Ticket carTicket = lot.issueTicket(car);
        Ticket truckTicket = lot.issueTicket(truck);

        System.out.println();
        lot.printStatus();
        System.out.println();

        // Exit vehicles
        lot.processExit(bikeTicket.getTicketId());
        lot.processExit(carTicket.getTicketId());
        lot.processExit(truckTicket.getTicketId());

        System.out.println();
        lot.printStatus();
    }
}

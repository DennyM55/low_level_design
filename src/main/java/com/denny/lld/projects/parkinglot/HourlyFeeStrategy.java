package com.denny.lld.projects.parkinglot;

import java.time.temporal.ChronoUnit;

public class HourlyFeeStrategy implements FeeStrategy {

    @Override
    public double calculateFee(Ticket ticket) {
        long hours = ChronoUnit.HOURS.between(ticket.getEntryTime(), ticket.getExitTime());
        if (hours == 0) hours = 1; // minimum 1 hour charge

        double ratePerHour;
        switch (ticket.getVehicle().getVehicleType()) {
            case BIKE:  ratePerHour = 20.0;  break;
            case CAR:   ratePerHour = 50.0;  break;
            case TRUCK: ratePerHour = 100.0; break;
            default:    ratePerHour = 50.0;
        }

        return hours * ratePerHour;
    }
}

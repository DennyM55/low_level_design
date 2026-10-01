package com.denny.lld.projects.bookmyshow;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class BookingService {

    public Booking bookSeats(User user, Show show, List<String> seatIds) {
        boolean locked = show.lockSeats(seatIds);
        if (!locked) {
            throw new IllegalStateException("One or more selected seats are not available.");
        }

        List<Seat> selectedSeats = seatIds.stream()
                .map(id -> show.getSeats().get(id))
                .collect(Collectors.toList());

        double total = selectedSeats.stream().mapToDouble(Seat::getPrice).sum();

        // Simulate payment success
        System.out.println("Processing payment of Rs. " + total + " for " + user.getName() + "...");
        boolean paymentSuccess = simulatePayment(total);

        if (paymentSuccess) {
            show.confirmSeats(seatIds);
            Booking booking = new Booking.Builder()
                    .bookingId("BMS-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                    .user(user)
                    .show(show)
                    .seats(selectedSeats)
                    .totalAmount(total)
                    .status(BookingStatus.CONFIRMED)
                    .build();
            System.out.println("Booking confirmed: " + booking);
            return booking;
        } else {
            show.releaseSeats(seatIds);
            throw new IllegalStateException("Payment failed. Seats released.");
        }
    }

    private boolean simulatePayment(double amount) {
        // In a real system this calls a payment gateway
        return true;
    }
}

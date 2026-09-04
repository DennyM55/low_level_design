package com.denny.lld.patterns.creational.builder;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Booking booking = new Booking.Builder()
                .bookingId("BKG-101")
                .userId("USER-7")
                .showId("SHOW-55")
                .seats(List.of("A1", "A2"))
                .addFoodCombo(true)
                .build();

        System.out.println(booking);
    }
}

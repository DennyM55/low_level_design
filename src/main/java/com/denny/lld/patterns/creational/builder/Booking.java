package com.denny.lld.patterns.creational.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Booking {
    private final String bookingId;
    private final String userId;
    private final String showId;
    private final List<String> seatNumbers;
    private final LocalDateTime bookingTime;
    private final boolean foodComboAdded;

    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.userId = builder.userId;
        this.showId = builder.showId;
        this.seatNumbers = Collections.unmodifiableList(new ArrayList<>(builder.seatNumbers));
        this.bookingTime = builder.bookingTime;
        this.foodComboAdded = builder.foodComboAdded;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public String getShowId() {
        return showId;
    }

    public List<String> getSeatNumbers() {
        return seatNumbers;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public boolean isFoodComboAdded() {
        return foodComboAdded;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId='" + bookingId + '\'' +
                ", userId='" + userId + '\'' +
                ", showId='" + showId + '\'' +
                ", seatNumbers=" + seatNumbers +
                ", bookingTime=" + bookingTime +
                ", foodComboAdded=" + foodComboAdded +
                '}';
    }

    public static class Builder {
        private String bookingId;
        private String userId;
        private String showId;
        private List<String> seatNumbers = new ArrayList<>();
        private LocalDateTime bookingTime = LocalDateTime.now();
        private boolean foodComboAdded;

        public Builder bookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder showId(String showId) {
            this.showId = showId;
            return this;
        }

        public Builder seats(List<String> seatNumbers) {
            this.seatNumbers = new ArrayList<>(seatNumbers);
            return this;
        }

        public Builder bookingTime(LocalDateTime bookingTime) {
            this.bookingTime = bookingTime;
            return this;
        }

        public Builder addFoodCombo(boolean foodComboAdded) {
            this.foodComboAdded = foodComboAdded;
            return this;
        }

        public Booking build() {
            validate();
            return new Booking(this);
        }

        private void validate() {
            if (bookingId == null || bookingId.isBlank()) {
                throw new IllegalArgumentException("bookingId is required");
            }
            if (userId == null || userId.isBlank()) {
                throw new IllegalArgumentException("userId is required");
            }
            if (showId == null || showId.isBlank()) {
                throw new IllegalArgumentException("showId is required");
            }
            if (seatNumbers == null || seatNumbers.isEmpty()) {
                throw new IllegalArgumentException("at least one seat is required");
            }
        }
    }
}

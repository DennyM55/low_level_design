package _15_code_bookmyshow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Booking {

    private final String bookingId;
    private final User user;
    private final Show show;
    private final List<Seat> seats;
    private final double totalAmount;
    private BookingStatus status;
    private final LocalDateTime bookingTime;

    private Booking(Builder builder) {
        this.bookingId = builder.bookingId;
        this.user = builder.user;
        this.show = builder.show;
        this.seats = Collections.unmodifiableList(new ArrayList<>(builder.seats));
        this.totalAmount = builder.totalAmount;
        this.status = builder.status;
        this.bookingTime = LocalDateTime.now();
    }

    public String getBookingId() { return bookingId; }
    public User getUser() { return user; }
    public Show getShow() { return show; }
    public List<Seat> getSeats() { return seats; }
    public double getTotalAmount() { return totalAmount; }
    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Booking{id='" + bookingId + "', user=" + user.getName()
                + ", movie=" + show.getMovie().getTitle()
                + ", seats=" + seats.size()
                + ", amount=Rs." + totalAmount
                + ", status=" + status + "}";
    }

    public static class Builder {
        private String bookingId;
        private User user;
        private Show show;
        private List<Seat> seats = new ArrayList<>();
        private double totalAmount;
        private BookingStatus status = BookingStatus.PENDING;

        public Builder bookingId(String bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder user(User user) {
            this.user = user;
            return this;
        }

        public Builder show(Show show) {
            this.show = show;
            return this;
        }

        public Builder seats(List<Seat> seats) {
            this.seats = new ArrayList<>(seats);
            return this;
        }

        public Builder totalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }

        public Builder status(BookingStatus status) {
            this.status = status;
            return this;
        }

        public Booking build() {
            if (bookingId == null || user == null || show == null || seats.isEmpty()) {
                throw new IllegalArgumentException("bookingId, user, show, and seats are required");
            }
            return new Booking(this);
        }
    }
}

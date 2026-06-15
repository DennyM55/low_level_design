package _21_chain_of_responsibility;

public class Address {
    public final String zip;
    public final String state;
    public final String city;
    public final String lines;

    public Address(String zip, String state, String city, String lines) {
        this.zip   = zip;
        this.state = state;
        this.city  = city;
        this.lines = lines;
    }

    @Override
    public String toString() {
        return lines + ", " + city + ", " + state + " " + zip;
    }
}

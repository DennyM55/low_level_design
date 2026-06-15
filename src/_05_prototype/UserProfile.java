package _05_prototype;

public class UserProfile implements Cloneable {

    private String name;
    private Address address;

    public UserProfile(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Deep clone: creates a new Address object so that the clone and original
     * do not share the same nested reference.
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        UserProfile cloned = (UserProfile) super.clone();
        cloned.address = new Address(this.address.getCity());
        return cloned;
    }

    @Override
    public String toString() {
        return "UserProfile{name='" + name + "', address=" + address + "}";
    }
}

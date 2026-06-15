package _05_prototype;

public class Main {

    public static void main(String[] args) throws CloneNotSupportedException {
        UserProfile original = new UserProfile("Alice", new Address("Delhi"));
        System.out.println("Original:              " + original);

        UserProfile clone1 = (UserProfile) original.clone();
        clone1.setName("Bob");
        clone1.getAddress().setCity("Mumbai");

        System.out.println("Clone after edit:      " + clone1);
        System.out.println("Original after clone edit: " + original);

        System.out.println();
        System.out.println("Same object?           " + (original == clone1));
        System.out.println("Same address object?   " + (original.getAddress() == clone1.getAddress()));
    }
}

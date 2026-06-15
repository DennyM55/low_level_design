package _01_solid._04_i_interface_segregation;

class Chef implements Cook {
    public void cookFood() {
        System.out.println("Chef is cooking food.");
    }
}

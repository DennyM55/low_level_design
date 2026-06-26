package com.denny.lld.fundamentals.solid.interfacesegregation;

public class Main {
    public static void main(String[] args) {
        Chef chef = new Chef();
        Waiter waiter = new Waiter();
        RestaurantService restaurantService = new RestaurantService();

        restaurantService.prepareOrder(chef);
        restaurantService.completeDining(waiter, waiter);
    }
}

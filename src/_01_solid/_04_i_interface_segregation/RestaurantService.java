package _01_solid._04_i_interface_segregation;

class RestaurantService {
    void prepareOrder(Cook cook) {
        cook.cookFood();
    }

    void completeDining(Server server, Cleaner cleaner) {
        server.serveCustomer();
        cleaner.cleanTable();
    }
}

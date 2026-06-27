package com.denny.lld.fundamentals.solid.interfacesegregation;

class RestaurantService {
    void prepareOrder(Cook cook) {
        cook.cookFood();
    }

    void completeDining(Server server, Cleaner cleaner) {
        server.serveCustomer();
        cleaner.cleanTable();
    }
}

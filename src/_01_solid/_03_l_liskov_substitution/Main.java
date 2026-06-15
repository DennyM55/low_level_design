package _01_solid._03_l_liskov_substitution;

public class Main {
    public static void main(String[] args) {
        BirdCareService birdCareService = new BirdCareService();
        FlightShowService flightShowService = new FlightShowService();

        Eagle eagle = new Eagle();
        Penguin penguin = new Penguin();

        birdCareService.feed(eagle);
        birdCareService.feed(penguin);

        flightShowService.startFlyingShow(eagle);
        // flightShowService.startFlyingShow(penguin); // Compile-time safety: penguin is not a FlyingBird.
    }
}

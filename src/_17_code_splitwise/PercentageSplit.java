package _17_code_splitwise;

public class PercentageSplit extends Split {

    private final double percentage;

    public PercentageSplit(User user, double percentage) {
        super(user);
        this.percentage = percentage;
    }

    public double getPercentage() {
        return percentage;
    }
}

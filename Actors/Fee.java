package Actors;

public record Fee(String name, double amount, boolean isMandatory) {

    public Fee(String name, double amount) {
        this(name, amount, true);
    }

    public Fee(String name) {
        this(name, 0, false);
    }

    public Fee() {
        this("Unknown", 0, false);
    }

    public double getAmount() {
        return amount;
    }

    public boolean isMandatory() {
        return isMandatory;
    }

    public String getName() {
        return name;
    }
}
public class Fee {
    private String name;
    private double amount;
    private boolean isMandatory;

    public Fee(String name, double amount, boolean isMandatory) {
        this.name = name;
        this.amount = amount;
        this.isMandatory = isMandatory;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
    }

    public boolean isMandatory() {

        return isMandatory;
    }

}

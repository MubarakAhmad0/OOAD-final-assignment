import java.io.Serializable;

public class Course implements Serializable {
    private int id;
    private String name;
    private String level;
    private double baseFee;

    public Course(int id, String name, String level, double baseFee) {
        this.id = id;
        this.name = name;
        this.level = level;
        this.baseFee = baseFee;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public double getBaseFee() {
        return baseFee;
    }

    @Override
    public String toString() {
        return name + " (" + level + ")";
    }
}

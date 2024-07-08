package Actors;

import java.io.Serializable;

public record Course(int id, String name, String level, double baseFee) implements Serializable {

    @Override
    public String toString() {
        return name + " (" + level + ")";
    }
}

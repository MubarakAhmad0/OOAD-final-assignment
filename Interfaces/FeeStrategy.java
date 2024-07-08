package Interfaces;

import Actors.Course;

public interface FeeStrategy {
    double calculateFee(Course course);
}

package Interfaces;

import Actors.Course;

import java.util.List;

public interface DiscountStrategy {

    double calculateDiscount(List<Course> courses);

}

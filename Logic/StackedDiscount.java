package Logic;

import Actors.Course;
import Interfaces.DiscountStrategy;

import java.util.List;

public class StackedDiscount implements DiscountStrategy {
    @Override
    public double calculateDiscount(List<Course> courses) {
        double discount = 0;

        if (courses.size() >= 3) { 
            double totalFee = 0;
            for (Course course : courses) {
                totalFee += course.baseFee();
            }
            discount = totalFee * 0.10;
        }

        return discount;
    }
}


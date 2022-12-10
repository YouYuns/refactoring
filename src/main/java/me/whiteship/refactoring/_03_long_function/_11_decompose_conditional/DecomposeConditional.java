package me.whiteship.refactoring._03_long_function._11_decompose_conditional;

import java.time.LocalDate;

public class DecomposeConditional {
    public class Plan{
        LocalDate summerStart;
        LocalDate summerEnd;

        Double summerRate;
        Double regularServiceCharge;

        public Plan(LocalDate summerStart, LocalDate summerEnd) {
            this.summerStart = summerStart;
            this.summerEnd = summerEnd;
        }
    }
    public void summer(Plan plan){
        LocalDate date = LocalDate.now();
        Double charge;
        Double quantity=0d;
        charge = summer(plan, date) ? summerCharge(plan, quantity) : regularCharge(plan, quantity);
    }

    private static double regularCharge(Plan plan, Double quantity) {
        return quantity * plan.summerRate + plan.regularServiceCharge;
    }

    private static double summerCharge(Plan plan, Double quantity) {
        return quantity * plan.summerRate;
    }

    private static boolean summer(Plan plan, LocalDate date) {
        return !date.isBefore(plan.summerStart) && !date.isAfter(plan.summerEnd);
    }


}

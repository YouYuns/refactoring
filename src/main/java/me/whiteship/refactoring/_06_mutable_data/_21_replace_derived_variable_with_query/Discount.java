package me.whiteship.refactoring._06_mutable_data._21_replace_derived_variable_with_query;

public class Discount {
        //파생된 값이다 discountedTotal은 기존의 discount과 baseTotal데이터로부터 계산되어온 값
    private double discountedTotal;
    private double discount;

    private double baseTotal;

    public Discount(double baseTotal) {
        this.baseTotal = baseTotal;
    }

    public double getDiscountedTotal() {
        return this.discountedTotal;
    }

    public void setDiscount(double number) {
        this.discount = number;
        this.discountedTotal = this.baseTotal - this.discount;
    }
}

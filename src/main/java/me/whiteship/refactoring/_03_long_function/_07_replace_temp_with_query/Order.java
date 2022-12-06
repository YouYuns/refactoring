package me.whiteship.refactoring._03_long_function._07_replace_temp_with_query;

public class Order {
    Double quantity;
    Item item;

    public Order(Double quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }

    public Double getPrice(){
        return this.getBasePrice() * this.getDiscountFactor();
    }

    private Double getDiscountFactor() {
        Double discountFactor = 0.98d;
        if(this.getBasePrice() > 1000) discountFactor -= 0.03;
        return discountFactor;
    }

    private Double getBasePrice() {
        return this.quantity * this.item.price;
    }
}

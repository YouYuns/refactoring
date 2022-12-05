package me.whiteship.refactoring._06_mutable_data._19_separate_query_from_modifier;

import me.whiteship.refactoring._02_duplicated_code._04_extract_function.Order;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;



public class Invoice {

    private double amount;

    private List<Order> orders;

    private LocalDate dueDate;

    private String customer;

    public Invoice(double amount, List<Order> orders, LocalDate dueDate, String customer) {
        this.amount = amount;
        this.orders = orders;
        this.dueDate = dueDate;
        this.customer=customer;
    }

    public String getCustomer() {
        return customer;
    }

    public Invoice(int amount) {
        this.amount=amount;
    }

    public Invoice() {
    }


    public double getAmount() {
        return amount;
    }

    public List<Order> getOrders(){
        return orders;
    }

    public LocalDate getDueDate(){
        return dueDate;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }




    public void setDueDate(LocalDate now) {
        this.dueDate=now;
    }
}

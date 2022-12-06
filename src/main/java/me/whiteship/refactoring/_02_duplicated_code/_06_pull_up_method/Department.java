package me.whiteship.refactoring._02_duplicated_code._06_pull_up_method;

public class Department extends Party{
    public Integer annualCost(){
        return this.montylyCost * 12;
    }
}

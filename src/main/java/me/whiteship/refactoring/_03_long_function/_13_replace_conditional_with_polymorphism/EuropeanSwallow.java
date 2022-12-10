package me.whiteship.refactoring._03_long_function._13_replace_conditional_with_polymorphism;

public class EuropeanSwallow extends Birds{

    public EuropeanSwallow(Birds birds) {
        super(birds);
    }
    public String plumage(){
        return "보통이다.";
    }
}

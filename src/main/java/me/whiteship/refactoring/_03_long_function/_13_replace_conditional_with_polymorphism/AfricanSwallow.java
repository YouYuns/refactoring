package me.whiteship.refactoring._03_long_function._13_replace_conditional_with_polymorphism;

public class AfricanSwallow extends Birds {
    public AfricanSwallow(Birds birds) {
        super(birds);
    }
    public String plumage(){
        return (this.numberOfCoconuts > 2) ? "지쳤다" : "보통이다.";
    }
}


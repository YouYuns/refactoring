package me.whiteship.refactoring._03_long_function._13_replace_conditional_with_polymorphism;

public class NorwegianBlueParrot extends Birds {
    public NorwegianBlueParrot(Birds birds) {
        super(birds);
    }

    public String plumage(){
        return(this.voltage > 100) ? "그을렸다" : "예쁘다";
    }
}

package me.whiteship.refactoring._03_long_function._13_replace_conditional_with_polymorphism;

import java.util.Map;

public class ReplaceConditionalWithPolymorphism {
    public String plumage(Birds birds){
        return new CreatedBird(birds).plumage();
    }


    public Integer airSpeedVelocity(Birds birds){
        return new Birds(birds).airSpeedVelocity();
    }

}

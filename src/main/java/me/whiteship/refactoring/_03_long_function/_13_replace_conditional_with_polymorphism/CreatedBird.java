package me.whiteship.refactoring._03_long_function._13_replace_conditional_with_polymorphism;

public class CreatedBird extends Birds{

    public CreatedBird(Birds birds) {
        super(birds);
    }

    public Object createdBird(Birds birds){
        switch (birds.type){
            case "유럽 제비":
                return new EuropeanSwallow(birds);
            case "아프리카 제비":
                return new AfricanSwallow(birds);
            case "노르웨이 파랑 앵무":
                return new NorwegianBlueParrot(birds);
            default:
                return new Birds(birds);
        }
    }


}

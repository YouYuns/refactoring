package me.whiteship.refactoring._03_long_function._13_replace_conditional_with_polymorphism;

import java.util.Map;
import java.util.Objects;

public class Birds {
    String name;

    String type;

    Integer numberOfCoconuts;

    Integer voltage;

    Boolean isNailed;
    public String plumage(){
          return "알 수 없다.";
    }

    public Integer airSpeedVelocity(){
        switch (this.type){
            case "유럽 제비":
                return 35;
            case "아프리카 제비":
                return 40 - 2 * this.numberOfCoconuts;
            case "노르웨이 파랑 앵무":
                return (this.isNailed) ? 0 : 10 + this.voltage / 10;
            default:
                return null;
        }
    }

    public Birds(Birds birds) {

    }
}

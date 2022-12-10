## 리팩토링 13. 조건문을 다형성으로 바꾸기
#### Replace Conditional with Polymorphism

### ✅ 배경

- 여러 타입에 따라 각기 다른 로직으로 처리해야 하는 경우에 다형성을 적용해서 조건문을
  보다 명확하게 분리할 수 있다. (예, 책, 음악, 음식 등…) 반복되는 switch문을 각기 다른
  클래스를 만들어 제거할 수 있다
####
- 공통으로 사용되는 로직은 상위클래스에 두고 달라지는 부분만 하위클래스에 둠으로써, 달
  리지는 부분만 강조할 수 있다.
####
- 모든 조건문을 다형성으로 바꿔야 하는 것은 아니다
---
### ✅ 절차
1. 다형적 동작을 표현하는 클래스들이 아직 없다면 만들어준다. 이왕이면 적합한 인스턴스를 알아서 만들어 반환하는 팩터리 함수도 함께 만든다.
2. 호출하는 코드에서 팩터리 함수를 사용하게 한다.
3. 조건부 로직 함수를 슈퍼클래스로 옮긴다.
   - 조건부 로직이 온전한 함수로 분리되어 있지 않다면 먼저 함수로 추출한다.
4. 서브클래스 중 하나를 선택한다. 서브클래스에서 슈퍼클래스의 조건부 로직 메서드를 오버라이드한다.<br>
   조건무 문장 중 선택된 서브클래스에 해당하는 조건절을 서브클래스 메서드로 복사한 다음 적절히 수정한다.
5. 같은 방식으로 각 조건절을 해당 서브클래스에서 메서드로 구현한다.
6. 슈퍼클래스 메서드에는 기본 동작 부분만 남긴다. 혹은 슈퍼클래스가 추상 클래스여야 한다면, <br>
   이 메서드를 추상으로 선언하거나 서브클래스에서 처리해야 함을 알리는 에러를 던진다.
---
### ✅ 예시
✔ 새의 종에 따른 비행 속도와 깃털 상태를 알수 있는 프로그램

```java
public class ReplaceConditionalWithPolymorphism {


    public String plumage(Birds birds){
        switch (birds.type){
            case "유럽 제비":
                return "보통이다.";
            case "아프리카 제비":
                return (birds.numberOfCoconuts > 2) ? "지쳤다" : "보통이다";
            case "노르웨이 파랑 앵무":
                return (birds.voltage > 100) ? "그을렸다" : "예쁘다";
            default:
                return "알 수 없다.";
        }
    }

    public Integer airSpeedVelocity(Birds birds){
        switch (birds.type){
            case "유럽 제비":
                return 35;
            case "아프리카 제비":
                return 40 - 2 * birds.numberOfCoconuts;
            case "노르웨이 파랑 앵무":
                return (birds.isNailed) ? 0 : 10 + birds.voltage / 10;
            default:
                return null;
        }
    }
}
```
⏬ 가장 먼저 airSpeedVelocity()와 plumage()를 Birds라는 클래스로 묶는다
```java
public class ReplaceConditionalWithPolymorphism {
    public String plumage(Birds birds){
        return new Birds(birds).plumage();
    }
    public Integer airSpeedVelocity(Birds birds){
        return new Birds(birds).airSpeedVelocity();
    }

}
public class Birds {
    String name;

    String type;

    Integer numberOfCoconuts;

    Integer voltage;

    Boolean isNailed;
    public String plumage(){
        switch (this.type){
            case "유럽 제비":
                return "보통이다.";
            case "아프리카 제비":
                return (this.numberOfCoconuts > 2) ? "지쳤다" : "보통이다";
            case "노르웨이 파랑 앵무":
                return (this.voltage > 100) ? "그을렸다" : "예쁘다";
            default:
                return "알 수 없다.";
        }
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

```
⏬ 종별 서브클래스를 만든다. 적합한 서브클래스의 인스턴스를 만들어줄 팩터리 함수도 만든다.
⏬ 객체를 얻을 때 팩터리 함수를 사용하도록 수정
```java
public class ReplaceConditionalWithPolymorphism {
    public String plumage(Birds birds){
        return new CreatedBird(birds).plumage();
    }


    public Integer airSpeedVelocity(Birds birds){
        return new Birds(birds).airSpeedVelocity();
    }

}
```
```java
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
```

⏬ plumage()부터 시작해서 switch문의 절 하나를 선택해 해당 서브클래스에서 오버라이드
```java
public class EuropeanSwallow extends Birds{

    public EuropeanSwallow(Birds birds) {
        super(birds);
    }
    public String plumage(){
        return "보통이다.";
    }
}

```
⏬ 모든 서브 클래스로 옮기고 슈퍼클래스의 메서드는 기본 동작용으로 남긴다.
```java
public class EuropeanSwallow extends Birds{

    public EuropeanSwallow(Birds birds) {
        super(birds);
    }
    public String plumage(){
        return "보통이다.";
    }
}
public class NorwegianBlueParrot extends Birds {
    public NorwegianBlueParrot(Birds birds) {
        super(birds);
    }

    public String plumage(){
        return(this.voltage > 100) ? "그을렸다" : "예쁘다";
    }
}
public class Birds{
   public String plumage(){
          return "알 수 없다.";
    }
}
```
---

✏ ️정리
- 조건문을 다형성을 사용해서 교체하는것인데 
- switch문이나 if문은 조건이 다를때마다 다른 액션을 취하게 하는건데 각각의 타입에 해당하는 클래스를 만들거나 <br>
  if-else같은 경우는 달라지는 부부만 바뀌도록 그런식으로 코드를 작성하면 거대한 메서드를 다른 여러 클래스에 나누어 둘수 있다.
- 각기 다른 타입을 지원하는 경우 switch의 경우 여러번 반복해서 발생할수 있는데 switch문의 반복을 제거할수있는 좋은 방법이다.


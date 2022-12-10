## 리팩토링 10. 함수를 명령으로 바꾸기
#### Replace Function with Command

### ✅ 배경

- 함수를 독립적인 객체인, Command로 만들어 사용할 수 있다
####
- 커맨드 패턴을 적용하면 다음과 같은 장점을 취할 수 있다
  - 부가적인 기능으로 undo 기능을 만들 수도 있다
  - 더 복잡한 기능을 구현하는데 필요한 여러 메소드를 추가할 수 있다
  - 상속이나 템플릿을 활용할 수도 있다
  - 복잡한 메소드를 여러 메소드나 필드를 활용해 쪼갤 수도 있다
####
- 대부분의 경우에 “커맨드” 보다는 “함수”를 사용하지만, 커맨드 말고 다른 방법이 없는 경우에만 사용
  한다

---
### ✅ 절차
1. 매개변수들을 원하는 형태로 받는 빈 함수를 만든다.
    - 마지막 단계에서 이 함수의 이름을 변경해야 하니 검색하기 쉬운 이름으로 지어준다.
2. 새 함수의 본문에서는 원래 함수를 호출하도록 하며, 새 매개변수와 원래 함수의 매개변수를 매핑한다.
3. 정적 검사를 수행한다.
4. 모든 호출자가 새 함수를 사용하게 수정한다. 하나씩 수정하며 테스트
    - 수정 후에는 원래의 매개변수를 만들어내는 코드 일부가 필요 없어질 수 있다. 따라서 죽은 코드제거하기로 없앨 수 있을 것이다.
5. 호출자를 모두 수정했다면 원래 함수를 인라인한다.
6. 새 함수의 이름을 적절히 수정하고 모든 호출자에 반영한다.
---
### ✅ 예시
✔️실내온도 모니터링 시스템을 생각해보자. 이 시스템은 일일 최저 최고 기온이 난방 계획에서 정한 범위를 벗어나는지 확인한다.
```java
import java.time.LocalDate;

public class PreserveWholeObject {

    Room room = new Room();

    Integer low = room.daysTempRange.low;
    Integer high = room.daysTempRange.high;
    
    //호출자
    public void Caller(){
        if(!withinRange(low, high)){
            System.out.println("방 온도가 지정 범위를 벗어났습니다.");
        }
    }
    //Heating클래스
    TeamperatureRange teamperatureRange = new TeamperatureRange();
    public Boolean withinRange(Integer bottom, Integer top){
        return (bottom >= this.teamperatureRange.low) && (top <= this.teamperatureRange.high);
    }
}
```
⏬ 최저 최고 기온을 뽑아내서 인수로 건네는 대신 범위 객체를 통째로 건넬 수도 있다.<br>
⏬ 가장 먼저 원하는 인터페이스를 갖춘 빈 메서드를 만든다.
```java

public class PreserveWholeObject {
    public void xxNEWwithinRange(NumberRange numberRange){
    }
}
```
⏬ 본문은 기존 withRange()를 호출하는 코드로 채운다. 자연스럽게 새 매개변수를 기존 매개변수와 매핑하는 로직이 만들어진다.

```java
public class PreserveWholeObject {
    public Boolean xxNEWwithinRange(NumberRange numberRange){
        return this.withinRange(numberRange.low, numberRange.high);
    }
}
```
⏬ 기존 함수를 호출하는 코드를 찾아서 새 함수를 호출하게 수정

```java
public class PreserveWholeObject {
    public void 호출자() {
        if (!xxNEWwithinRange(room.daysTempRange)) {
            System.out.println("방 온도가 지정 범위를 벗어났습니다.");
        }
    }
}
```

⏬ 모두 새함수로 대체하고 모두 반영한다.
```java
public class PreserveWholeObject {
    public Boolean withinRange(DaysTempRange numberRange) {
        return (numberRange.low >= this.teamperatureRange.low) && (numberRange.high <= this.teamperatureRange.high);
    }
}
```
---
### ✅ 예시 : 새 함수를 다른 방식으로 만들기
✔ 코드 작성 없이 순전히 다른 리팩터링들을 연달아 수행하여 새 메서드를 만들어 내는 방법이다.
✔ 메서드를 추출하는 방식으로 새 메서드를 만들려한다.
```java
public class PreserveWholeObject{
    DaysTempRange tempRange = room.daysTempRange;
    Integer low = tempRange.low;
    Integer high = tempRange.high;
    Boolean isWithinRange = withinRange(low, high);
    public void 호출자(){
        if(!isWithinRange){
            System.out.println("방 온도가 지정 범위를 벗어났습니다.");
        }
    }
}
```
⏬ 함수추출하기로 새 메서드를 만들수 있다.
```java
public class PreserveWholeObject{
    Boolean isWithinRange = withinRange(tempRange);
    public void 호출자(){
        if(!isWithinRange){
            System.out.println("방 온도가 지정 범위를 벗어났습니다.");
        }
    }
}
```
---

✏️정리
- 메서드를 넘기는 파라미터가 있는경우 특히 여러 파라미터가 있는 경우 <br>
  그 파라미터를 하나의 오브젝트에서 파생된 값들이 종종있는데 <br>
  각각을 넘기지 않고 하나의 오브젝트 타입을 넘김으로써 파라미터 갯수를 줄이는 기법이다.
####
- 마치 IntroduceParameterObject와 굉장히 비슷하다 <br>
  IntroduceParameterObject는 아예 파라미터의 파생되어 온 클래스가 아예없을때<br> 그거를 만들면서 자연스럽게 PreserveWholeObject까지 적용이 된다
####
- PreserverWholeObject는 그러한 타입이 이미 있는경우에 적용하는 기술이다.
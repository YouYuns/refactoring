## 리팩토링 9. 객체 통쨰로 넘기기
####  Preserve Whole Object

### ✅ 배경

- 어떤 한 레코드에서 구할 수 있는 여러 값들을 함수에 전달하는 경우, 해당 매개변수를 레
  코드 하나로 교체할 수 있다
####
- 매개변수 목록을 줄일 수 있다. (향후에 추가할지도 모를 매개변수까지도…)
####
- 이 기술을 적용하기 전에 의존성을 고려해야 한다
####
- 어쩌면 해당 메소드의 위치가 적절하지 않을 수도 있다. (기능 편애 “Feature Envy” 냄새
  에 해당한다.)

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
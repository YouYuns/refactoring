## 리팩토링 17. 변수 캡슐화하기
#### Encapsulate Variable

### ✅ 배경

- 메소드는 점진적으로 새로운 메소드로 변경할 수 있으나, 데이터는 한번에 모두 변경해야
  한다.
####
- 데이터 구조를 변경하는 작업을 그보다는 조금 더 수월한 메소드 구조 변경 작업으로 대체
  할 수 있다.
####
- 데이터가 사용되는 범위가 클수록 캡슐화를 하는 것이 더 중요해진다.
    - 함수를 사용해서 값을 변경하면 보다 쉽게 검증 로직을 추가하거나 변경에 따르는 후속
      작업을 추가하는 것이 편리하다
####
- 불변 데이터의 경우에는 이런 리팩토링을 적용할 필요가 없다.


---
### ✅ 절차
1. 변수로의 접근과 갱신을 전담하는 캡슐화 함수들을 만든다.
2. 정적 검사를 수행한다.
3. 변수를 직접 참조하던 부분을 모두 적절한 캡슐화 함수 호출로 바꾼다. 하나씩 바꿀때 마다테스트
4. 변수의 접근 범위를 제한한다.
   - 변수로의 직접 접근을 막을 수 없을 때도 있다. 그럴 떄는 변수 이름을 바꿔서 테스트해보면 해당 변수를 참조하는 곳을 쉽게 찾아 낼 수 있다.
5. 테스트
6. 변수 값이 레코드라면 레코드 캡슐화하기를 적용할지 고려해본다.
---
### ✅ 예시
✔️온도를 조절하는 코드
```java
public class Thermostats {
    public static Integer targetTemperature = 70;
    public static Boolean heating = true;
    public static Boolean cooling = false;
    public static Boolean fahrenheit = true;
}
```
⏬ 캡슐화 되어있지않으면 값을 말도 안되는 값을 설정할 수 있다.
```java
public class Home {
    public static void main(String[] args) {
        System.out.println(Thermostats.targetTemperature);
        Thermostats.targetTemperature = -1111600;
        Thermostats.fahrenheit = false;
    }
}

```
⏬ 게터 세터를 만들어서 사용하는게 좋다
```java
public class Thermostats{
    public static void setTargetTemperature(Integer targetTemperature) {
        Thermostats.targetTemperature = targetTemperature;
    }

    public static Boolean getHeating() {
        return heating;
    }
}
```

---
✏️정리
- 변수를 변경하는것보다는 메서드를 변경하는게 더 쉬운일이다.<br>
  모든 변수를 사용하는 곳을 정확히 고쳐야된다.
- 변수를 직접 사용하는 코드들이 있다면 메서드로 감싸주면 나중에 변경에 좋을 수 있다.
- 변수들의 범위가 여러개가 있는데 범위가 커지면 커질수록 변경하기가 어려워진다.<br>
  그러므로 범위가 클수록 캡슐화해야된다.
- 불변데이터면 고려하지 않아도된다.
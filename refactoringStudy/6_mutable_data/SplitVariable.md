## 리팩토링 18. 변수 쪼개기
#### Split Variable

### ✅ 배경

- 어떤 변수가 여러번 재할당 되어도 적절한 경우
  - 반복문에서 순회하는데 사용하는 변수 또는 인덱스
  - 값을 축적시키는데 사용하는 변수
####
- 그밖에 경우에 재할당 되는 변수가 있다면 해당 변수는 여러 용도로 사용되는 것이며 변수
  를 분리해야 더 이해하기 좋은 코드를 만들 수 있다.
  - 변수 하나 당 하나의 책임(Responsibility)을 지도록 만든다
  - 상수를 활용하자. (자바스크립트의 const, 자바의 final)

---
### ✅ 절차
1. 변수를 선언한 곳과 값을 처음 대입하는 곳에서 변수 이름을 바꾼다.
    - 이후의 대입이 항상 i = i + <무언가> 형태라면 수집 변수이므로 쪼개면 안된다. <br>
    수집 변수는 총합계산, 문자열 연결, 스트림에 쓰기, 컬렉션에 추가하기 등의 용도로 흔히 쓰인다.
2. 가능하면 이떄 불변으로 선언한다.
3. 이 변수에 두 번째로 값을 대입하는 곳 앞까지의 모든 참조(이 변수가 쓰인 곳)를 새로운 변수 이름으로 바꾼다.
4. 두 번째 대입 시 변수를 원래 이름으로 다시 선언한다.
5. 테스트
6. 반복한다. 매 반복에서 변수를 새로운 이름으로 선언하고 다음번 대입 때까지의 모든 참조를 새 변수명으로 바꾼다. 이 과정을 마지막 대입까지 반복
---
### ✅ 예시
✔️초기힘을 받아 일정한 가속도로 전파되다가, 시간이 흐른 후 어떠한 계기로 두 번쨰 힘을 받아 속도 빨라지는 코드
```java
public class Haggis {

    private double primaryForce;
    private double secondaryForce;
    private double mass;
    private int delay;

    public Haggis(double primaryForce, double secondaryForce, double mass, int delay) {
        this.primaryForce = primaryForce;
        this.secondaryForce = secondaryForce;
        this.mass = mass;
        this.delay = delay;
    }

    public double distanceTravelled(int time) {
        double result;
        double acc = primaryForce / mass;
        int primaryTime = Math.min(time, delay);
        result = 0.5 * acc * primaryTime * primaryTime;

        int secondaryTime = time - delay;
        if (secondaryTime > 0) {
            double primaryVelocity = acc * delay;
            acc = (primaryForce + secondaryForce) / mass;
            result += primaryVelocity * secondaryTime + 0.5 * acc * secondaryTime + secondaryTime;
        }

        return result;
    }
}

```
⏬ acc변수에 값이 두번 대입된다는 점인데 역활이 두개라는 점이리ㅏ서 변수를 쪼개서 사용해야된다.
```java
public class Haggis {
    public double distanceTravelled(int time) {
        double result;
        //아래 acc와 중복으로 사용되어서 명칭을 고친다.
        //final을 붙여서 불변으로 해주는게 좋다.
        final double primayAcceleration = primaryForce / mass;
        int primaryTime = Math.min(time, delay);
        result = 0.5 * primayAcceleration * primaryTime * primaryTime;

        int secondaryTime = time - delay;
        if (secondaryTime > 0) {
            double primaryVelocity = primayAcceleration * delay;
            //두번쨰 가속도이니 여기는 다른 명칭으로 고친다.
            //final을 붙여서 불변으로 해주는게 좋다.
           final double secondaryAcceleration = (primaryForce + secondaryForce) / mass;
            result += primaryVelocity * secondaryTime + 0.5 * secondaryAcceleration * secondaryTime + secondaryTime;
        }

        return result;
    }
}

```


---
✏️정리
- 어떤 변수가 여러번 재할당되는 경우 반복문이나 값을 축적하는게 값을 여러번 재활용 하는 게 당연하다.
- 여러번 할당될떄 그게 옳은가 고민해봐야된다.
- 한 변수가 하나의 역할만 하도록 변수를 분리하는게 Split Variable 리팩토링이다.
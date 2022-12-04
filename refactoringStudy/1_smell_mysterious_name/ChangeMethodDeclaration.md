### 리팩토링 1. 함수 선언 변경하기
####  Change Function Declaration
#### 함수 이름 변경하기, 메소드 이름 변경하기, 매개변수 추가하기, 매개변수 제거하기, 시그니처 변경하


✅배경

- 좋은 이름을 가진 함수는 함수가 어떻게 구현되었는지 코드를 보지 않아도 이름만 보고도
  이해할 수 있다.
####
- 좋은 이름을 찾아내는 방법? 함수에 주석을 작성한 다음, 주석을 함수 이름으로 만들어 본
  다
####
- 함수의 매개변수는

  - 함수 내부의 문맥을 결정한다. (예, 전화변호 포매팅 함수)
  - 의존성을 결정한다. (예, Payment 만기일 계산 함수)




✅ 절차

- 간단한 절차
  - 매개변수를 제거하려거든 먼저 함수 본문에서 제거 대상 매개변수를 참조하는 곳은 없는지 확인한다.
  - 메서드 선언을 원하는 형태로 바꾼다.
  - 기존 메서드 선언을 참조하는 부분을 모두 찾아서 바뀐 형태로 수정한다.
  - 테스트한다.

✔️ 변경할 게 둘 이상이면 나눠서 처리하는 편이 나을 때가 많다. 따라서 이름 변경과
매개변수 추가를 모두 하고 싶다면 독립적으로 처리하자( 그러다 문제가 생기면 작업을 되돌리고 
'마이그레이션 절차'를 따른다.)

- 마이그레이션 절차
  - 이어지는 추출 단계를 수월하게 만들어야 한다면 함수의 본문을 적절히 리팩터링한다.
  - 함수 본문을 새로운 함수로 추출한다.<br>
    -> 새로 만들 함수 이름이 기존 함수와 같다면 일단 검색하기 쉬운 이름을 임시로 붙여둔다.
  - 추출한 함수에 매개변수를 추가해야한다면 '간단한 절차'를 따라 추가한다.
  - 테스트한다.
  - 기존 함수를 인라인한다.
  - 이름을 임시로 붙여뒀다면 함수 선언 바꾸기를 한 번 더 적용해서 원래 이름으로 되돌린다.
  - 테스트한다.

### ✅ 예시 : 함수 이름 바꾸기

📌 간단한 절차
```java
public class ChangeMethodDeclaration{
    public double circum(Double radius){
        return 2 * Math.PI * radius;
    }
}
```
⏬ 함수 이름 변경하기
```java
public class ChangeMethodDeclaration{
    public double circumference(Double radius){
        return 2 * Math.PI * radius;
    }
}
```
---
📌 마이그레이션 절차
```java
public class ChangeMethodDeclaration{
    public double circum(Double radius){
        return 2 * Math.PI * radius;
    }
}
```
⏬ 함수 본문 전체를 새로운 함수로 추출
```java
public class ChangeMethodDeclaration{
    public double circum(Double radius){
        return circumference(radius);
    }
    
    public double circumference(Double radius){
        return 2 * Math.PI * radius;
    }
}
```
⏬ 예전 함수를 인라인하기 그러면 예전 함수를 호출하는 부분이 모두 새 함수로 호출하도록 바뀐다.

```java
public class ChangeMethodDeclaration{
    public double circumference(Double radius){
        return 2 * Math.PI * radius;
    }
}
```
--- 

### ✅ 예시 : 매개변수 추가하기

📌 마이그레이션 절차

```java
public class BookReservation{
        addReservation(String customer){
            this.reservation.add(customer);
        }
}
```
⏬ 예약시 우선순위 큐를 지원하라는 새로운 요구사항 추가되었다.<br>
⏬ 우선순위 큐에 넣을지 여부를 결정하는 매개변수를 추가가필요한 상황
```java
public class BookReservation{
        addReservation(String customer){
            this.reservation.add(customer);
        }
}
```
⏬ addReservation()의 본문을 새로운 함수로 추출한다.
새로 추출한 함수 이름도 addReservation()이어야 하지만, 
기존 함수와 이름이 같은 상태로 둘 수는 없으니 우선은 나중에 찾기 쉬운 임시 이름을 붙인다.
```java
public class BookReservation{
        addReservation(String customer){
            this.zzReservation.add(customer);
        }
        
        zzReservation(String customer){
            this.reservation.add(customer);
        }
}
```
⏬ 그런 다음 새 함수의 선언문과 호출문에 원하는 매개변수를 추가
```java
public class BookReservation{
        addReservation(String customer){
            this.zzReservation.add(customer, false);
        }
        
        zzReservation(String customer,Boolean isPriority){
            this.reservation.add(customer);
        }
}
```
⏬ 함수 인라인하기
```java
public class BookReservation{
    addReservation(String customer,Boolean isPriority){
            this.reservation.add(customer,isPriority);
        }
}
```

---
### ✅ 예시 : 매개변수를 속성으로 바꾸기

```java
import java.util.Arrays;

public class inNewEngland {
  public void inNewEngland(String aCustomer) {
    String[] newEnglang = {'MA', 'CT', 'ME', 'VI', 'NH', 'RI'};
    return Arrays.stream(newEnglang).anyMatch(aCustomer::equals);
  }
  
  // 위에함수를 호출하는 코드
  public void inNewEnglandger(String someCustomer){
    String newEnglanders = someCustomer
            .someCustomers.filer(c -> inNewEngland(c));    
  }
}

```
⏬ 이제 함수 추출하기로 새 함수를 만든다.

```java
import java.lang.reflect.Array;
import java.util.Arrays;

public class inNewEngland {

  public void inNewEngland(String aCustomer) {
    String stateCode = aCustomer.address.stats;
    return xxNEWinNewEngland(stateCode);
  }

  public void xxNEWinNewEngland(String stateCode) {
    return Arrays.stream({'MA', 'CT', 'ME', 'VI', 'NH', 'RI'}).anyMatch(stateCode::equals);
  }

  // 위에함수를 호출하는 코드
  public void inNewEnglandger(String someCustomer) {
    String newEnglanders = someCustomer
            .someCustomers.filer(c -> inNewEngland(c));
  }
}
```
⏬ 변수로 추출해둔 입력 매개변수를 인라인하기
```java
import java.lang.reflect.Array;
import java.util.Arrays;

public class inNewEngland {

  public void inNewEngland(String aCustomer) {
    return xxNEWinNewEngland(aCustomer.address.stats);
  }
}

```
⏬ 기존함수의 본문을 호출문들에 집어넣는다
```java
import java.lang.reflect.Array;
import java.util.Arrays;

public class inNewEngland {

  // 위에함수를 호출하는 코드
  public void inNewEnglandger(String someCustomer) {
    String newEnglanders = someCustomer
            .someCustomers.filer(c -> x(inNewEnglandger(c.address.state)));
  }
}
```
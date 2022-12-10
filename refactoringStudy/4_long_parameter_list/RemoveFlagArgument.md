## 리팩토링 15. 플래그 인수 제거하기
#### Remove Flag Argument

### ✅ 배경

- 플래그는 보통 함수에 매개변수로 전달해서, 함수 내부의 로직을 분기하는데 사용한다
####
- 플래그를 사용한 함수는 차이를 파악하기 어렵다
  - bookConcert(customer, false), bookConcert(customer, true)
  - bookConcert(customer), premiumBookConcert(customer)
####
- 조건문 분해하기 (Decompose Condition)를 활용할 수 있다

✔️플래그 인수란? 호출되는 함수가 실행할 로직을 호출하는 쪽에서 선택하기 위해 전달하는 인수

---
### ✅ 절차
1. 매개변수로 주어질 수 있는 값 각각에 대응하는 명시적 함수들을 생성한다.
   - 주가 되는 함수에 깔끔한 분배 조건문이 포함되어 있다면 조건문 분해하기로 명시적 함수들을 생성. 그렇지 않다면 래핑 함수 형태로 만든다.
2. 원래 함수를 호출하는 코드들을 모두 찾아서 각 리터럴 값에 대응되는 명시적 함수를 호출하도록 수정한다.
---
### ✅ 예시

✔️배송일자를 계산하는 코드
⏬ deliverDate를 호출하는 쪽에서는 true, false는 값을 넣을텐데 그게 어떤 값을 의미하는지 알수없다.

```java
public class Shipment {

  public LocalDate deliveryDate(Order order, boolean isRush) {
    if (isRush) {
      return regularDeliveryDate(order);
    } else {
      return rushDeliveryDate(order);
    }
  }

  private static LocalDate rushDeliveryDate(Order order) {
    int deliveryTime = switch (order.getDeliveryState()) {
      case "WA", "CA" -> 2;
      case "OR", "TX", "NY" -> 3;
      default -> 4;
    };
    return order.getPlacedOn().plusDays(deliveryTime);
  }

  private static LocalDate regularDeliveryDate(Order order) {
    int deliveryTime = switch (order.getDeliveryState()) {
      case "WA", "CA", "OR" -> 1;
      case "TX", "NY", "FL" -> 2;
      default -> 3;
    };
    return order.getPlacedOn().plusDays(deliveryTime);
  }
}
```
⏬ 먼저 함수추출로 각각의 true와 false일 떄를 추출하고 그 함수를 호출하는게 더 의도를 잘 들어낸다
```java
public class Shipment {
  //함수 호출 예 Boolean값을 없애서 호출할수 있다
 rushDeliveryDate(Order order);
 
 
  private static LocalDate rushDeliveryDate(Order order) {
    int deliveryTime = switch (order.getDeliveryState()) {
      case "WA", "CA" -> 2;
      case "OR", "TX", "NY" -> 3;
      default -> 4;
    };
    return order.getPlacedOn().plusDays(deliveryTime);
  }

  private static LocalDate regularDeliveryDate(Order order) {
    int deliveryTime = switch (order.getDeliveryState()) {
      case "WA", "CA", "OR" -> 1;
      case "TX", "NY", "FL" -> 2;
      default -> 3;
    };
    return order.getPlacedOn().plusDays(deliveryTime);
  }
}
```
---
✏️정리
- 기다란 파라미터 리스트중에서 일부 자주쓰는 플래그성 매개변수가 있는데 Boolean값 Enum값 String일수 있다.
- 플래그성 매개변수는 보통 조건문에 쓰이는데 플래그가 있는게 사실 좋지는 않다.
- 플래그가 너무 많이 있으면 -> 그 메서드는 너무 많은 일을 하는 것이고
- 플래그가 1개면 -> 기존메서드에 다른일을 하도록 시작이 되는거지만 이건 이 메서드의 의미를 호출하는 쪽에서는 플래그 이름 보기전까지는 파악하기 힘들다.
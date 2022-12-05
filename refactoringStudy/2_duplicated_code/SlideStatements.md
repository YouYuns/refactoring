### 리팩토링 5. 코드 정리하기
####  Slide Statements

✅배경

- 관련있는 코드끼리 묶여있어야 코드를 더 쉽게 이해할 수 있다.
####
- 함수에서 사용할 변수를 상단에 미리 정의하기 보다는, 해당 변수를 사용하는 코드 바로 위
  에 선언하자
####
- 관련있는 코드끼리 묶은 다음, 함수 추출하기 (Extract Function)를 사용해서 더 깔끔하
  게 분리할 수도 있다.
####

---

✅ 절차

- 코드 조각을 이동할 목표를 찾는다. 다음과 같은 간섭이 있다면 리팩터링을 포기한다.
  - 코드조각에서 참조하는 요소를 선언하는 문장 앞으로는 이동할 수 없다.
  - 코드조각에서 참조하는 요소의 뒤로는 이동할 수 없다.
  - 코드조각에서 참조하는 요소를 수정하는 문장을 건너뛰어 이동할 수 없다.
  - 코드조각에서 수정하는 요소를 참조하는 요소를 건너뛰어 이동할 수 없다.
####
- 추출할 코드를 원본 함수에서 복사하여 새 함수에 붙여 넣는다.
####
- 코드 조각을 원래 위치에서 잘라내어 목표 위치에 붙여 넣는다.


### ✅ 예시 : 조건문이 있을 때의 슬라이드

```java
public class ExtractFunction {
  public void printOwing(Invoice invoice){
    Integer outstanding = 0;

    System.out.println("-------------------------");
    System.out.println("----------고객채무---------");
    System.out.println("-------------------------");

    //미해결 채무(outstanding)를 계산한다.
    for (Order order : invoice.getOrders()) {
      outstanding += order.amount;
    }

    //마감일(dueDate)을 기록한다.
    LocalDate today = LocalDate.now();
    invoice.setDueDate(LocalDate.now());

    //세부사항을 출력한다.
    System.out.println("고객명 :" + invoice.getCustomer());
    System.out.println("채무액 :" + outstanding);
    System.out.println("마감일 :" + invoice.getDueDate().toString());

  }
}
```
⏬ 배너 및 디테일를 출력하는 코드를 추출하기
```java
public class ExtractFunction{
  public void printOwing(Invoice invoice){
    Integer outstanding = 0;

    printBanner();

    //미해결 채무(outstanding)를 계산한다.
    for (Order order : invoice.getOrders()) {
      outstanding += order.amount;
    }

    //마감일(dueDate)을 기록한다.
    LocalDate today = LocalDate.now();
    invoice.setDueDate(LocalDate.now());

    printDetails(invoice, outstanding);

  }

  private static void printDetails(Invoice invoice, Integer outstanding) {
    //세부사항을 출력한다.
    System.out.println("고객명 :" + invoice.getCustomer());
    System.out.println("채무액 :" + outstanding);
    System.out.println("마감일 :" + invoice.getDueDate().toString());
  }

  private static void printBanner() {
    System.out.println("-------------------------");
    System.out.println("----------고객채무---------");
    System.out.println("-------------------------");
  }
}
```
---

### ✅ 예시 : 지역 변수를 사용할 때

```java
public class ExtractFunction{
  public void printOwing(Invoice invoice){
    Integer outstanding = 0;
    printBanner();
    //미해결 채무(outstanding)를 계산한다.
    for (Order order : invoice.getOrders()) {
      outstanding += order.amount;
    }
    recordDueDate(invoice);
    printDetails(invoice, outstanding);

  }

  private static void recordDueDate(Invoice invoice) {
    //마감일(dueDate)을 기록한다.
    LocalDate today = LocalDate.now();
    invoice.setDueDate(today);
  }

  private static void printDetails(Invoice invoice, Integer outstanding) {
    //세부사항을 출력한다.
    System.out.println("고객명 :" + invoice.getCustomer());
    System.out.println("채무액 :" + outstanding);
    System.out.println("마감일 :" + invoice.getDueDate().toString());
  }

  private static void printBanner() {
    System.out.println("-------------------------");
    System.out.println("----------고객채무---------");
    System.out.println("-------------------------");
  }
}
```
---

### ✅ 예시 : 지역 변수의 값을 변경할 떄

```java
public class ExtractFunction{
  public void printOwing(Invoice invoice){
    Integer outstanding = 0;
    printBanner();
    //미해결 채무(outstanding)를 계산한다.
    for (Order order : invoice.getOrders()) {
      outstanding += order.amount;
    }
    recordDueDate(invoice);
    printDetails(invoice, outstanding);

  }
}
```
⏬ 앞 예시에서 수행한 리팩터링들은 모두 간단해서 단번에 처리했지만 이번에는 단계로 처리<br>
⏬ 선언문을 변수가 사용되는 코드 근처로 슬라이드 한다.

```java
public class ExtractFunction{
  public void printOwing(Invoice invoice){

    printBanner();
    Integer outstanding = calculateOutstanding(invoice);
    recordDueDate(invoice);
    printDetails(invoice, outstanding);

  }

  private static Integer calculateOutstanding(Invoice invoice) {
    //미해결 채무(outstanding)를 계산한다.
    Integer outstanding = 0;
    for (Order order : invoice.getOrders()) {
      outstanding += order.amount;
    }
    return outstanding;
  }
}
```
--- 
✏️ 값을 반환할 변수가 여러 개라면?
- 함수가 값 하나만 반환하는 방식을 선호하기 때문에 각각을 반환하는 함수 여러 개로 만든다.
- 여기서는 임시 변수를 질의 함수로 바꾸거나 변수를 쪼개는 식으로 처리하면 좋다.

✏️이렇게 추출한 함수를 최상위 수준 같은 다른 문맥으로 이동하려면 어떻게 해야할까?
- 먼저 중첩 함수로 추출하고 나서 새로운 문맥으로 옮긴다.
- 중첩함수로 추출할 수 있더라도 최소한 원본 함수와 같은 수준의 문맥으로 먼저 추출해보자
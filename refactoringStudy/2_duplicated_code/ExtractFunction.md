### 리팩토링 4. 함수 추출하기
####  Extract Function

✅배경

- “의도”와 “구현” 분리하기
  - 어떤 코드가 어떤 일을 하려는지 표현하고 있다면 의도가 잘 작성되어있다.
  - 그게 아니라면 이 코드는 구현을 들어내고 있는 코드이다.
####
- 무슨 일을 하는 코드인지 알아내려고 노력해야 하는 코드라면 해당 코드를 함수로 분리하
  고 함수 이름으로 “무슨 일을 하는지” 표현할 수 있다
####
- 한줄 짜리 메소드도 괜찮은가? YES
####
- 거대한 함수 안에 들어있는 주석은 추출한 함수를 찾는데 있어서 좋은 단서가 될 수 있다


✅ 절차

- 함수를 새로 만들고 목적을 잘 드러내는 이름을 붙인다( '어떻게'가 아닌 '무엇을' 하는지가 드러나야한다.)
- 추출할 코드를 원본 함수에서 복사하여 새 함수에 붙여 넣는다.
- 추출한 코드 중 원본 함수의 지역 변수를 참조하거나 추출한 함수의 유효범위를 벗어나는 변수는 없는지 검사한다. 있다면 매개변수로 전달한다.
<br>
✔️일반적으로 함수에는 지역변수와 매개변수가 있기 마련이다. 가장 일반적인 처리 방법은 이런 변수를 모두 인수로 전달하는 것
<br>
✔️추출한 코드에서만 사용하는 변수가 추출한 함수 밖에 선언되어 있다면 추출한 함수 안에서 선언 하도록 수정
####
- 변수를 다 처리했다면 컴파일한다.
- 원본 함수에서 추출한 코드 부분을 새로 만든 함수를 호출하는 문장으로 바꾼다(즉, 추출한 함수로 일을 위임한다.)






### ✅ 예시 : 유효범위를 벗어난느 변수가 없을 때

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
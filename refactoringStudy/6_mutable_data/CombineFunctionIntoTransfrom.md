## 리팩토링 22. 여러 함수를 변환 함수로 묶기 
#### Combine Functions into Transform

### ✅ 배경

- 관련있는 여러 파생 변수를 만들어내는 함수가 여러곳에서 만들어지고 사용된다면 그러한
  파생 변수를 “변환 함수 (transform function)”를 통해 한 곳으로 모아둘 수 있다
####
- 소스 데이터가 변경될 수 있는 경우에는“여러 함수를 클래스로 묶기 (Combine
  Functions into Class)”를 사용하는 것이 적절하다.
####
- 소스 데이터가 변경되지 않는 경우에는 두 가지 방법을 모두 사용할 수 있지만, 변환 함수
  를 사용해서 불변 데이터의 필드로 생성해 두고 재사용할 수도 있다.
---
### ✅ 절차
1. 변수가 사용되기 전에 값이 확실히 결정되는지, 변수를 사용할 때마다 계란 로직이 매번 다른 결과를 내지는 않는지 확인한다.
2. 읽기전용으로 만들 수 있는 변수는 읽기전용으로 만든다.
3. 테스트
4. 변수 대입문을 함수로 추출한다.
   - 변수와 함수가 같은 이름을 가질 수 없다면 함수 이름을 임시로 짓는다.<br>
     또한, 추출한 함수가 부수효과를 일으키는지는 않는지 확인한다.<br>
     부수효과가 있다면 질의 함수와 변경 함수 분리하기로 대처한다.
5. 테스트
6. 변수 인라인하기로 임시 변수를 제거한다.
---
### ✅ 예시
✔️간단한 주문 클래스를 준비
```java
public class Order {
  Double quantity;
  Item item;

  public Order(Double quantity, Item item) {
    this.quantity = quantity;
    this.item = item;
  }

  public Double getPrice(){
    Double basePrice = this.quantity * this.item.price;
    Double discountFactor = 0.98d;

    if(basePrice > 1000) discountFactor -= 0.03;
    return basePrice * discountFactor;
  }
}
```
⏬ 임시 변수인 basePrice와 discountFacotr를 메서드로 바꾼다
⏬ 먼저 basePrice에 final을 붙여 읽기전용으로 만들고 테스트
```java
public class Order {
  Double quantity;
  Item item;

  public Order(Double quantity, Item item) {
    this.quantity = quantity;
    this.item = item;
  }

  public Double getPrice(){
    final  Double basePrice = this.quantity * this.item.price;
    Double discountFactor = 0.98d;

    if(basePrice > 1000) discountFactor -= 0.03;
    return basePrice * discountFactor;
  }
}
```
⏬ 대입문의 우변을 게터로 추출한다. 그리고 변수 인라인

```java
public class Order {
  Double quantity;
  Item item;

  public Order(Double quantity, Item item) {
    this.quantity = quantity;
    this.item = item;
  }

  public Double getPrice(){
    final  Double basePrice = getBasePrice();
    Double discountFactor = 0.98d;

    if(getBasePrice() > 1000) discountFactor -= 0.03;
    return getBasePrice() * discountFactor;
  }
  public Double getBasePrice(){
      return this.quantity * this.item.price;
  }
}
```
⏬ discountFactor도 같은 방식으로 한다
```java
public class Order {
  Double quantity;
  Item item;

  public Order(Double quantity, Item item) {
    this.quantity = quantity;
    this.item = item;
  }

  public Double getPrice(){
    return getBasePrice() * getDiscountFactor();
  }
  public Double getBasePrice(){
    return this.quantity * this.item.price;
  }
  public Double getDiscountFactor(){
    Double discountFactor = 0.98d;
    if(getBasePrice() > 1000) discountFactor -= 0.03;
      return discountFactor;
  }
}
```
---
✏️정리
- 관련있는 여러 파생 변수들을 만들어내는 함수가 여러곳에서 사용되는 경우에 그 파생변수를 변환함수를 통해서 한곳으로 가져오는 방법
- CombineFunctionsintoClass랑도 관련이 있다.
- 변환함수를 사용해서 새로운 함수를 만드는것이다.
- 기존에있는 데이터를 입력으로 받아서 새로운 데이터 형태로 만드는것이다.
- 여러함수를 클래스로 묶기와 여러함수를 변환함수로 묶기는 소스에 해당하는 데이터가 변경될 여지가있는 <br>
  세터를 가지고 있는 그러한 데이터라면 함수를 클래스로 묶는게 타당하다.
- 
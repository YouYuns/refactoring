## 리팩토링 7. 임시 변수를 질의 함수로 바꾸기
####  Replace Temp with Query

### ✅ 배경

- 변수를 사용하면 반복해서 동일한 식을 계산하는 것을 피할 수 있고, 이름을 사용해 의미를
  표현할 수도 있다
####
- 긴 함수를 리팩토링할 때, 그러한 임시 변수를 함수로 추출하여 분리한다면 빼낸 함수로 전
  달해야 할 매개변수를 줄일 수 있다

---
### ✅ 절차
1. 변수가 사용되기 전에 값이 확실히 결정되는지, 변수를 사용할 때마다 계산 로직이 매번 다른 결과를 내지는 않는지 확인한다.
2. 읽기전용으로 만들 수 있는 변수는 읽기전용으로 만든다.
3. 테스트
4. 변수 대입문을 함수로 추출한다.
    - 변수와 함수가 같은 이름을 가질 수 없다면 함수 이름을 임시로 짓는다.
    - 추출한 함수가 부수효과를 일으키는지 확인하고 부수효과가 있다면 질의 함수와 변경함수 분리하기로 대처
5.테스트
6.변수 인라인하기 임시변수를 제거

---
### ✅ 예시

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
⏬ 대입문의 우변을 게터로 추출한다.
```java

public class Order {
    Double quantity;
    Item item;

    public Order(Double quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }

    public Double getPrice(){
        Double basePrice = this.getBasePrice();
        Double discountFactor = 0.98d;

        if(basePrice > 1000) discountFactor -= 0.03;
        return basePrice * discountFactor;
    }

    private Double getBasePrice() {
        return this.quantity * this.item.price;
    }
}
```
⏬ 인라인한다.
```java
   public Double getPrice(){
        Double discountFactor = 0.98d;

        if(this.getBasePrice() > 1000) discountFactor -= 0.03;
        return this.getBasePrice() * discountFactor;
    }

```
⏬ discountFactor변수도 같은 순서로 처리한다.

```java
    public Double getPrice(){
        return this.getBasePrice() * this.getDiscountFactor();
    }

    private Double getDiscountFactor() {
        Double discountFactor = 0.98d;
        if(this.getBasePrice() > 1000) discountFactor -= 0.03;
        return discountFactor;
    }

    private Double getBasePrice() {
        return this.quantity * this.item.price;
    }
```
---

✏️ 정리
- 임시변수를 만드는것도 하나의 리팩토링이다.
####
- 하지만 거기서 더 나가가 그 표현식 자체가 여러번 반복된다던가 <br>
그리고 긴 메서드를 리팩토링 할떄 추출해내는 매개변수가 많아질때 그 매개변수를 전달하는게 아니라 <br>
뺴내는 메서드 내부에서 호출하도록 바꾸면 매개변수를 줄일수 있다.
####
- 이 리팩토링은 Extracting Function 별반 다를게 없다
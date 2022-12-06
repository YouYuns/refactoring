## 리팩토링 8. 매개변수 객체 만들기
####  Introduce Parameter Object

### ✅ 배경

- 같은 매개변수들이 여러 메소드에 걸쳐 나타난다면 그 매개변수들을 묶은 자료 구조를 만
  들 수 있다
####
- 그렇게 만든 자료구조는
  - 해당 데이터간의 관계를 보다 명시적으로 나타낼 수 있다
  - 함수에 전달할 매개변수 개수를 줄일 수 있다
  - 도메인을 이해하는데 중요한 역할을 하는 클래스로 발전할 수도 있다
---
### ✅ 절차
1. 적당한 데이터 구조가 아직 마련되어 있지 않다면 새로 만든다.
   -  클래스로 만드는 걸 추천, 나중에 동작까지 함께 묶기 좋기 때문이다.
   -  데이터 구조를 값로 만든다 (DTO, VO)
2. 테스트
3. 함수 선언 바꾸기로 새 데이터 구조를 매개변수로 추가한다.
4. 테스트
5. 함수 호출 시 새로운 데이터 구조 인스턴스를 넘기도록 수정한다. 하나씩 수정할 때마다 테스트한다.
6. 기존 매개변수를 사용하던 코드를 새 데이터 구조의 원소를 사용하도록 바꾼다.
7. 다 바꿨다면 기존 매개변수를 제거하고 테스트한다.
---
### ✅ 예시

```java
public class IntroduceParameterObject {
    public Stream<Temp> readingsOutsideRange(Station station, Integer min, Integer max){
        return station.readings.stream().filter(r-> r.temp < min || r.temp > max);
    }
}
```
⏬ 이 함수는 다음과 같이 호출할수 있다.
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
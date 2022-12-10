## 리팩토링 14. 매개변수를 질의 함수로 바꾸기
#### Replace Parameter with Query

### ✅ 배경

- 함수의 매개변수 목록은 함수의 다양성을 대변하며, 짧을수록 이해하기 좋다
####
- 어떤 한 매개변수를 다른 매개변수를 통해 알아낼 수 있다면 “중복 매개변수”라 생각할 수
  있다.
####
- 매개변수에 값을 전달하는 것은 “함수를 호출하는 쪽”의 책임이다. 가능하면 함수를 호출하
는 쪽의 책임을 줄이고 함수 내부에서 책임지도록 노력한다
####
- “임시 변수를 질의 함수로 바꾸기”와 “함수 선언 변경하기”를 통해 이 리팩토링을 적용한
  다

---
### ✅ 절차
1. 필요하다면 대상 매개변수의 값을 계산하는 코드를 별도 함수로 추출해 놓는다.
2. 함수 본문에서 대상 매개변수로의 참조를 모두 찾아서 그 매개변수의 값을 만들어주는 표현식을 참조하도록 바꾼다. 하나 수정할 때 마다 테스트
3. 함수 선언 바꾸기로 대상 매개변수를 없앤다.
---
### ✅ 예시

```java
public class Order {

  private int quantity;

  private double itemPrice;

  public Order(int quantity, double itemPrice) {
    this.quantity = quantity;
    this.itemPrice = itemPrice;
  }

  public double finalPrice() {
    double basePrice = this.quantity * this.itemPrice;
    int discountLevel = this.quantity > 100 ? 2 : 1;
    return this.discountedPrice(basePrice, discountLevel);
  }

  private double discountedPrice(double basePrice, int discountLevel) {
    return discountLevel == 2 ? basePrice * 0.90 : basePrice * 0.95;
  }
}
```
⏬ 함수를 간소화하다 보면 임시 변수를 질의 함수로 바꾸기를 적용할 떄가 많다. 앞에 finalPrice() 함수에 적용하면 다음처럼 변한다.<br>
⏬ discountedPrice()함수에 discountLevel()의 반환 값을 건넬 이유가 사라졌다<br>
⏬ 매개변수를 참조한는 코드를 모두 함수 호출로 바꾸면된다.
```java
public class Order {

    private int quantity;

    private double itemPrice;

    public Order(int quantity, double itemPrice) {
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public double finalPrice() {
        double basePrice = this.quantity * this.itemPrice;
        return this.discountedPrice(basePrice);
    }

    private int getDiscountLevel() {
        int discountLevel = this.quantity > 100 ? 2 : 1;
        return discountLevel;
    }

    private double discountedPrice(double basePrice) {
        return getDiscountLevel() == 2 ? basePrice * 0.90 : basePrice * 0.95;
    }
}

```
---

✏ ️정리
- 함수의 전달되는 매개변수 목록이 많으면 많을수록 그 함수가 어떤일을 하는지 추측하기 어렵다.
- 그래서 가능한 매개변수를 가능한 줄이는게 그 함수를 사용하는쪽에서 더 손쉽게 사용할수 있게 해준다.
- Replace Parameter with Query는 매개변수들 중에서도 어떤 다른 매개변수를 통해서 알아내는 경우일때 사용하는 리팩토링이다.
- 이러한 매개변수를 중복된 매개변수라고 생각할 수 있다. (매개변수가 다를지라도 매개변수를 다른 매개변수를 통해 알아낼수 있다면)
- 무조건 적으로 매개변수를 줄여야된다는 아니다 매개변수를 줄였는데 새로운 의존성이 생기면 그냥 두는게 낫다.
- 리팩토링 기법은 많은데 반대되는 기법들도 있다. 그러므로 상황에 따라서 쓰는게 옳다.
## 리팩토링 23. 참조를 값으로 바꾸기 
#### Change Reference to Value

### ✅ 배경

- 레퍼런스 (Reference) 객체 vs 값 (Value) 객체
  - https://martinfowler.com/bliki/ValueObject.html
  - “Objects that are equal due to the value of their properties, in this case their x
    and y coordinates, are called value objects.”
  - 값 객체는 객체가 가진 필드의 값으로 동일성을 확인한다.
  - 값 객체는 변하지 않는다.
  - 어떤 객체의 변경 내역을 다른 곳으로 전파시키고 싶다면 레퍼런스, 아니라면 값 객체를 사
    용한다
---
### ✅ 절차
1. 후보 클래스가 불변인지, 혹은 불변이 될 수 있는지 확인한다.
2. 각각의 세터를 하나씩 제거한다.
3. 이 값 객체의 필드들을 사용하는 동치성(equality) 비교 메서드를 만든다.
   - 대부분의 언어는 이런 상황에 사용할 수 있도록 오버라이딩 가능한 동치성 비교 메서드를 제공한다.<br>
   동치성 비교 메서드를 오버라이드할 때는 보통 해시코드 생성 메서드도 함께 오버라이드 해야한다.
---
### ✅ 예시
✔️사람 객체가 있고, 이 객체는 다음 코드처럼 생성 시점에는 전화번호가 <br>
올바로 설정되지 못하게 짜여 있다고 해보자.
```java
public class Person {

    private TelephoneNumber officeTelephoneNumber;

    public String officeAreaCode() {
        return this.officeTelephoneNumber.areaCode();
    }

    public void officeAreaCode(String areaCode) {
        this.officeTelephoneNumber.areaCode(areaCode);
    }

    public String officeNumber() {
        return this.officeTelephoneNumber.number();
    }

    public void officeNumber(String number) {
        this.officeTelephoneNumber.number(number);
    }

}
//TelephoneNumber 클래스
public class TelephoneNumber {

    private String areaCode;

    private String number;

    public String areaCode() {
        return areaCode;
    }

    public void areaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String number() {
        return number;
    }

    public void number(String number) {
        this.number = number;
    }
}
//클래스를 추출하다보면 이런 상황이 발생한다.
//추출해서 새로 만들어진 객체 ( 여기서는 telephoneNumber)를 갱신하는 메서드들은 여전히 
//추출 전 클래스 (여기서는 Person)에 존재할 것이다.
//어쩄든 새 클래스를 가리키는 참조가 하나뿐이므로 참조를 값으로 바꾸기에 좋은 상황
```
⏬ 전화번호를 불변으로 만들기( 세터들만 제거 )
```java
public class TelephoneNumber {

    private String areaCode;

    private String number;

    public TelephoneNumber(String areaCode, String number) {
        this.areaCode = areaCode;
        this.number = number;
    }
    public String areaCode() {
        return areaCode;
    }

    public String number() {
        return number;
    }
}
```
⏬ 세터를 호출하는 쪽을 살펴서 전화번호를 매번 다시 대입하도록 바꿔야한다

```java
public class Person {

    private TelephoneNumber officeTelephoneNumber;

    public String officeAreaCode() {
        return this.officeTelephoneNumber.areaCode();
    }

    public void officeAreaCode(String areaCode) {
        this.officeTelephoneNumber.areaCode(areaCode);
    }

    public String officeNumber() {
        return this.officeTelephoneNumber.number();
    }

    public void officeNumber(String number) {
        this.officeTelephoneNumber.number(number);
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
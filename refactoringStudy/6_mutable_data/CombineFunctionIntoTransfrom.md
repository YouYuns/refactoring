## 리팩토링 22. 여러 함수를 변환 함수로 묶기 
#### Remove Setting Method

### ✅ 배경

- 변경할 수 있는 데이터를 최대한 줄이도록 노력해야 한다
####
- 계산해서 알아낼 수 있는 변수는 제거할 수 있다
  - 계산 자체가 데이터의 의미를 잘 표현하는 경우도 있다.
  - 해당 변수가 어디선가 잘못된 값으로 수정될 수 있는 가능성을 제거할 수 있다
####
- 계산에 필요한 데이터가 변하지 않는 값이라면, 계산의 결과에 해당하는 데이터 역시 불변
  데이터기 때문에 해당 변수는 그대로 유지할 수 있다
---
### ✅ 절차
1. 변환할 레코드를 입력받아서 값을 그대로 반환하는 변환 함수를 만든다.
   - 이 작업은 대체로 깊은 복사로 처리해야 한다. 변환 함수가 원본 레코드를 바꾸지 않는지 검사하는 테스트를 마련해두면 도움될 때가 많다.
2. 묶을 함수 중 함수 하나를 골라서 본문 코드를 변환 함수로 옮기고, 처리 결과를 레코드에 새 필드로 기록한다.<br>
   그런 다음 클라이언트 코드가 이 필드를 사용하도록 수정한다.
    - 로직이 복잡하면 함수 추출하기부터 한다.
3. 테스트
4. 나머지 관련 함수도 위 과정에 따라 처리한다.
---
### ✅ 예시
✔️매달 사용자가 마신 차의 양을 측정Reading 하는 코드
```java
public class ReadingDTO {
    String customer;
    double quantity;
    Month month;
    Year year;
}

//세금을 부과할 소비량을 계산하는 코드
public class Client1 {

    double baseCharge;

    public Client1(ReadingDTO dto) {
        this.baseCharge = baseRate(dto.month, dto.year) * dto.quantity;
    }

    private double baseRate(Month month, Year year) {
        return 10;
    }

    public double getBaseCharge() {
        return baseCharge;
    }
}
```
⏬ 코드에는 이와 같은 계산 코드가 여러 곳에 반복된다
```java
public class ReadingDTO {
    public class Client2 {

        private double base;
        private double taxableCharge;

        public Client2(ReadingDTO reading) {
            this.base = baseRate(reading.month, reading.year) * reading.quantity;
            this.taxableCharge = Math.max(0, this.base - taxThreshold(reading.year));
        }

        private double taxThreshold(Year year) {
            return 5;
        }

        private double baseRate(Month month, Year year) {
            return 10;
        }

        public double getBase() {
            return base;
        }

        public double getTaxableCharge() {
            return taxableCharge;
        }
    }
}
```
⏬ 중복 코드라면 함수추출하기로 처리할 수도 있지만 추출한 함수들이 프로그램 곳곳에 흩어져서 나중에 그런 함수가<br>
있는지 조차 모르게 될 가능성이 있다.

```java
public class Client3 {

    private double basicChargeAmount;

    public Client3(ReadingDTO reading) {
        this.basicChargeAmount = calculateBaseCharge(reading);
    }
    //아래처럼 만들어 놓고 까먹을수 이따 메서드를
    private double calculateBaseCharge(ReadingDTO reading) {
        return baseRate(reading.month, reading.year) * reading.quantity;
    }

    private double baseRate(Month month, Year year) {
        return 10;
    }
    public double getBasicChargeAmount() {
        return basicChargeAmount;
    }
}
```
⏬ 이거를 해결하는 방법으로 다양한 파생 정보 계산 로직을 모두 하나의 변환 단계로 모을 수 있다

---
✏️정리
- Derived Variable는 어디선가 계산되거나 파생된 변수라고 생각하면된다.
- 어디선가 파생됬다는것은 소스가 되는 데이터가 있다는 뜻이다.
- 기존에 있는 다른 데이터를 조합하고 계산해서 만들어진 거기서부터 파생되어온 변수
- 그 변수를 사용하던 코드를 함수로 변경하기를 Replace Derived Variable라고한다.
- 변경될수는 변수를 줄이는 리팩토링 방법이다.
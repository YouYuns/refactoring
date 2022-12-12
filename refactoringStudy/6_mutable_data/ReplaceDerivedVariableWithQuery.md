## 리팩토링 20. 세터 제거하기 
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
1. 변수 값이 갱신되는 지점을 모두 찾는다. 필요하면 변수 쪼개기를 활용해 각 갱신 지점에서 변수를 분리한다.
2. 해당 변수의 값을 계산해주는 함수를 만든다.
3. 해당 변수가 사용되는 모든 곳에 어서션을 추가하여 함수의 계산 결과가 변수의 값과 같은지 확인한다.
   - 필요하면 변수 캡슐화하기를 적용하여 어서션이 들어갈 장소를 마련해준다.
4. 테스트
5. 변수를 읽는 코드를 모두 함수 호출로 대체한다.
6. 테스트
7. 변수를 선언하고 갱신하는 코드를 죽은 코드 제거하기로 없앤다.
---
### ✅ 예시
✔️
```java
public class Discount {
    //파생된 값이다 discountedTotal은 기존의 discount과 baseTotal데이터로부터 계산되어온 값
    private double discountedTotal;
    private double discount;

    private double baseTotal;

    public Discount(double baseTotal) {
        this.baseTotal = baseTotal;
    }

    public double getDiscountedTotal() {
        return this.discountedTotal;
    }

    public void setDiscount(double number) {
        this.discount = number;
        this.discountedTotal = this.baseTotal - this.discount;
    }
}

```
⏬ getDiscountedTotal은 결국 baseTotal - disscount이므로 바로 안쪽으로 인라인한다
```java
public class Discount {
    //파생된 값이다 discountedTotal은 기존의 discount과 baseTotal데이터로부터 계산되어온 값
//    private double discountedTotal;
    private double discount;

    private double baseTotal;

    public Discount(double baseTotal) {
        this.baseTotal = baseTotal;
    }

    public double getDiscountedTotal() {
        return this.baseTotal - this.discount;
    }

    public void setDiscount(double number) {
        this.discount = number;
//        this.discountedTotal = this.baseTotal - this.discount;
    }
}

```
### ✅ 예시 : 소스가 둘 이상일 때
✔️adjustmenets에 들어오는 합계를 구하는 코드
```java
public class ProductionPlan {
    //production이란느 값은 adjustment의 총합계이므로 미리 계산해서 변수에 담아둘 필요가없어서 derived variable이다
    
    private double production;
    private List<Double> adjustments = new ArrayList<>();

    public void applyAdjustment(double adjustment) {
        this.adjustments.add(adjustment);
        //아래처럼 미리 계산할 필요가없음
        this.production += adjustment;
    }

    public double getProduction() {
        return this.production;
    }
}
```
⏬ 합계를 구하는 메서드를 만들고 그 메서드를 바로 리턴하게 만든다 getProduction
```java
public class ProductionPlan {

    private double production;
    private List<Double> adjustments = new ArrayList<>();

    public void applyAdjustment(double adjustment) {
        this.adjustments.add(adjustment);
//        this.production += adjustment;
    }

    public double getProduction() {
    //calculatedProduction 메서드를 인라인한게 아래다
//        assert this.production == this.adjustments.stream().mapToDouble(Double::valueOf).sum();
        return this.adjustments.stream().mapToDouble(Double::valueOf).sum();
    }

//    private double calculatedProduction(){
//        합계를 구하는 여러가지 방법이 있다 1번쨰 방법 reduce사용하기 2개의 값이 들어가서 하나의 값으로 0은 초기값이다.
//        위에껄 더 줄이면
//        return this.adjustments.stream().reduce((double)0, Double::sum);
//
//        두번째 방법
//        return this.adjustments.stream().mapToDouble(Double::valueOf).sum();
}

```
⏬ 이렇게하면 production 변수를 제거할수 있게된다. 합계구할때 합계구하는 메서드 호출하면된다
```java
public class ProductionPlan {

    private List<Double> adjustments = new ArrayList<>();

    public void applyAdjustment(double adjustment) {
        this.adjustments.add(adjustment);
    }
    public double getProduction() {
        return this.adjustments.stream().mapToDouble(Double::valueOf).sum();
    }
}
```
---
✏️정리
- Derived Variable는 어디선가 계산되거나 파생된 변수라고 생각하면된다.
- 어디선가 파생됬다는것은 소스가 되는 데이터가 있다는 뜻이다.
- 기존에 있는 다른 데이터를 조합하고 계산해서 만들어진 거기서부터 파생되어온 변수
- 그 변수를 사용하던 코드를 함수로 변경하기를 Replace Derived Variable라고한다.
- 변경될수는 변수를 줄이는 리팩토링 방법이다.
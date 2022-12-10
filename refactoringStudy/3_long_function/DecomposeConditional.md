## 리팩토링 11. 조건문 분해하기
#### Decompose Conditional

### ✅ 배경

- 여러 조건에 따라 달라지는 코드를 작성하다보면 종종 긴 함수가 만들어지는 것을 목격할
  수 있다
####
- “조건”과 “액션” 모두 “의도”를 표현해야한다
####
- 기술적으로는 “함수 추출하기”와 동일한 리팩토링이지만 의도만 다를 뿐이다.

---
### ✅ 절차
1. 조건식과 그 조건식에 딸린 조건절 각각을 함수로 추출한다.
---
### ✅ 예시
✔여름철이면 할인율이 달라지는 어떤 서비스의 요금을 계산
```java
public class DecomposeConditional {
  public class Plan{
    LocalDate summerStart;
    LocalDate summerEnd;

    Double summerRate;
    Double regularServiceCharge;

    public Plan(LocalDate summerStart, LocalDate summerEnd) {
      this.summerStart = summerStart;
      this.summerEnd = summerEnd;
    }
  }
  public void summer(Plan plan){
    LocalDate date = LocalDate.now();
    Double charge;
    Double quantity=0d;
    if(!date.isBefore(plan.summerStart) && !date.isAfter(plan.summerEnd))
      charge = quantity * plan.summerRate;
    else
      charge = quantity * plan.summerRate + plan.regularServiceCharge;
  }

}
```
⏬ 우선 조건 부분(조건식)을 별도 함수로 추출한다.
```java

public class DecomposeConditional {
  private static boolean summer(Plan plan, LocalDate date) {
    return !date.isBefore(plan.summerStart) && !date.isAfter(plan.summerEnd);
  }

}
```
⏬ if문과 else절을 별도 함수로 추출한다.

```java
public class DecomposeConditional {
  private static double regularCharge(Plan plan, Double quantity) {
    return quantity * plan.summerRate + plan.regularServiceCharge;
  }

  private static double summerCharge(Plan plan, Double quantity) {
    return quantity * plan.summerRate;
  }
}
```
⏬ 취향에 따라 전체 조건문을 3항 연산자로 바꿀수 있다.
```java
public class DecomposeConditional{
  public void summer(Plan plan){
        LocalDate date = LocalDate.now();
        Double charge;
        Double quantity=0d;
        charge = summer(plan, date) ? summerCharge(plan, quantity) : regularCharge(plan, quantity);
    }
}
```
---

✏ ️정리
- 조건문 분해하기는 여러개의 메서드를 분해하는 과정중에 의미를 부여하는 방법
- IF 안에 조건자체가 복잡해 질수 있고, TRUE FALSE해야할일이 자체가 길어지면서 이해하기 어려워진다
- 결국 함수 추출하기를 통해서 뽑아내면서 의미를 부여한다.

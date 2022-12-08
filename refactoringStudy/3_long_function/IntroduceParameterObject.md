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
import java.time.LocalDate;

public class IntroduceParameterObject {
    public void amountInvoiced(LocalDate startDate, LocalDate endDate) {
    };
    public void amountReceived(LocalDate startDate, LocalDate endDate) {
    };
    public void amountOverdue(LocalDate startDate, LocalDate endDate) {
    };
}
```
⏬ 매개변수를 객체형태로 만들어서 매개변수를 줄인다.
```java

public class IntroduceParameterObject {
    public void amountInvoiced(DateRange dateRange) {
    };
    public void amountReceived(DateRange dateRange) {
    };
    public void amountOverdue(DateRange dateRange) {
    };
}
```

---

✏️ 진정한 값 객체로 거듭나기
- 매개변수 그룹을 객체로 교체하는 일은 진짜 값진 작업의 준비 단계일 뿐이다.
####
- 앞에서처럼 클래스로 만들어두면 관련 동작들을 이 클래스로 옮길 수 있다는 이점이 생긴다.
####
```java
public class IntroduceParameterObject{
    public void readingsOutsideRange(Station station, String range){
        return station.reading.filter(r->!range.contains(r.temp));
    }
}
```
- 진정한 값 객체로 만들기 위해 값에 기반 한 동치성 검사 메서드부터 추가할 것이다.ㄴ
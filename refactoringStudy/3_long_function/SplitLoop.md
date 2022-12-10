## 리팩토링 12. 반복문 쪼개기
#### Split Loop

### ✅ 배경

- 하나의 반복문에서 여러 다른 작업을 하는 코드를 쉽게 찾아볼 수 있다
####
- 해당 반복문을 수정할 때 여러 작업을 모두 고려하며 코딩을 해야한다
####
- 반복문을 여러개로 쪼개면 보다 쉽게 이해하고 수정할 수 있다
####
- 성능 문제를 야기할 수 있지만, “리팩토링”은 “성능 최적화”와 별개의 작업이다. 리팩토링
  을 마친 이후에 성능 최적화 시도할 수 있다

---
### ✅ 절차
1. 반복문을 복제해 두 개로 만든다.
2. 반복문이 중복되어 생기는 부수효괄ㄹ 파악해서 제거한다.
3. 테스트
4. 완료됐으면, 각 반복문을 함수로 추출할지 고민해본다.
---
### ✅ 예시


```java
import java.util.ArrayList;

public class SplitLoop {
  Double totalSalary = 0;
  List people = new ArrayList<>();
    for( String p : people){
    averageAge += p.age;
    totalSalary += p.salary;
  }
    averageAge = averageAge / people.size;
}
```
⏬ 반복문을 복제한후 중복을 제거하고 부수효과가 있으면 그 코드를 제거한다.
```java
import java.util.ArrayList;

public class SplitLoop {
  Double totalSalary = 0;
  List people = new ArrayList<>();
   for( String p : people){
    totalSalary += people.salary;
  }
    
    for(String p : people){
        averageAge += p.age;
  }
  
  averageAge = averageAge / people.size;
}
```
✔ 더 가다듬기
- 이 리팩터링을 할 떄는 나뉜 각 반복문을 각각의 함수로 추출하면<br>
  어쩔지까지 한 묶음으로 고민한다.
---

✏ ️정리
- 보통 반복문 한번에 돌고 모든 작업을 수행하는 경우가 많고 그게 성능이 더 좋긴하지만<br>
  반복문이 길어서 그안에 수정을 할떄 고려해야될 상황이 너무 많으면 반복문 쪼개기를 통해서 나눠야한다.

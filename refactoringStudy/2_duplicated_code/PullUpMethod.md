## 리팩토링 6. 메소드 올리기
####  Pull Up Method

### ✅ 배경

- 중복 코드는 당장은 잘 동작하더라도 미래에 버그를 만들어 낼 빌미를 제공한다.
  - 예) A에서 코드를 고치고, B에는 반영하지 않은 경우
####
- 여러 하위 클래스에 동일한 코드가 있다면, 손쉽게 이 방법을 적용할 수 있다
####
- 비슷하지만 일부 값만 다른 경우라면, “함수 매개변수화하기 (Parameterize Function)” 리팩토링을 적
  용한 이후에, 이 방법을 사용할 수 있다
####
- 하위 클래스에 있는 코드가 상위 클래스가 아닌 하위 클래스 기능에 의존하고 있다면, “필드 올리기
  (Pull Up Field)”를 적용한 이후에 이 방법을 적용할 수 있다
####
-  메소드가 비슷한 절차를 따르고 있다면, “템플릿 메소드 패턴 (Template Method Pattern)” 적용을
   고려할 수 있다

---
### ✅ 절차
 1. 똑같이 동작하는 메서드인지 면밀히 살펴본다.
    - 실질적으로 하는 일을 같지만 코드가 다르다면 본문 코드가 똑같아질 때까지 리팩터링한다.
 2. 메서드 안에서 호출하는 다른 메서드와 참조하는 필드들을 슈퍼클래스에서도 호출하고 참조할 수 있는지 확인한다.
 3. 메서드 시그니처가 다르다면 함수 선언 바꾸기로 슈퍼클래스에서 사용하고 싶은 형태로 통일한다.
 4. 슈퍼클래스에 새로운 메서드를 생성하고, 대상 메서드의 코드를 복사해 넣는다.
 5. 정적 검사를 수행한다.
 6. 서브클래스 중 하나의 메서드를 제거한다.
 7. 테스트
 8. 모든 서브클래스의 메서드가 없어질 때까지 다른 서브클래스의 메서드를 하나씩 제거한다.

---
### ✅ 예시 

```java
public class Department extends Party{
    public Integer annualCost(){
        return this.montylyCost * 12;
    }
}

public class Employee extends Party{

    public Integer annualCost(){
        return this.montylyCost * 12;
    }
}
```
⏬ 서브클래스 중 하나의 메서드를 슈퍼클래스로 옮긴다
```java

public class Party {
    Integer montylyCost;
    public class Employee extends Party{

        public Integer annualCost(){
            return this.montylyCost * 12;
        }
    }
}
```
---

✏️메소드올리기
-  하위 클래스에서 상위 클래스로 옮기는 리팩토링(상속구조)
-  하위 클래스에서 중복된 코드를 상위클래스로 옮기는 것
-  디자인 패턴 공부해보기
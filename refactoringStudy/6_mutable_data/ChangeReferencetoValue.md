## 리팩토링 23. 참조를 값으로 바꾸기 
#### Change Reference to Value

### ✅ 배경

- “눈에 띌만한” 사이드 이팩트 없이 값을 조회할 수 있는 메소드는 테스트 하기도 쉽고, 메소
  드를 이동하기도 편하다
  - https://martinfowler.com/bliki/ValueObject.html
  - “Objects that are equal due to the value of their properties, in this case their x
    and y coordinates, are called value objects.”
  - 값 객체는 객체가 가진 필드의 값으로 동일성을 확인한다.
  - 값 객체는 변하지 않는다.
  - 어떤 객체의 변경 내역을 다른 곳으로 전파시키고 싶다면 레퍼런스, 아니라면 값 객체를 사
    용한다

---
### ✅ 절차
1. 대상 함수를 복제하고 질의 목적에 충실한 이름을 짓는다.
   - 함수 내부를 살펴 무엇을 반환하는지 찾는다. 어떤 변수의 값을 반환한다면 그 변수 이름이 훌륭한 단초가 될 것이다.
2. 새 질의 함수에서 부수효과를 모두 제거한다.
3. 정적 검사를 수행한다.
4. 원래 함수(변경 함수)를 호출하는 곳을 모두 찾아낸다. 호출하는 곳에서 반환 값을 사용한다면 질의 함수를 호출하도록 바꾸고,<br>
   원래 함수를 호출하는 코드를 바로 아래 줄에 새로 추가한다. 하나 수정할 떄마다 테스트
5. 원래 함수에서 질의 관련 코드를 제거한다.
6. 테스트
---
### ✅ 예시
✔️범죄자를 찾으면 그 사람의 이름을 반환하고 경고를 울린다.
```java
public class Criminal {

    public String alertForMiscreant(List<Person> people) {
        for (Person p : people) {
            if (p.getName().equals("Don")) {
                setOffAlarms();
                return "Don";
            }

            if (p.getName().equals("John")) {
                setOffAlarms();
                return "John";
            }
        }

        return "";
    }

    private void setOffAlarms() {
        System.out.println("set off alarm");
    }
}

```
⏬ 함수를 복제하고 질의 목적에 맞는 이름짓고 새 질의 함수에서 부수효과를 낳는 부분을 제거한다.
```java
public class Criminal {
    public String findMiscreant(List<Person> people) {
        for (Person p : people) {
            if (p.getName().equals("Don")) {
                return "Don";
            }
            if (p.getName().equals("John")) {
                return "John";
            }
        }
        return "";
    }
}

```
⏬ 이제 원래 함수를 호출하는 곳을 찾아서 새로운 질의 함수를 호출하도록 바꾸고<br>
    이어서 원래의 변경 함수를 호출하는 코드를 바로 아래 삽입
```java
public class Criminal {
    String found = criminal.findMiscreant(List.of(new Person("Keesun"), new Person("Don")));
    alertForMiscreant();
}
```
⏬ 알고리즘 교체하기 (더 가다듬기)<br>
✔️findMiscreant와 alertForMiscreant에 각각 똑같은 반복문이 2개가 돌아서 해상 메서드를 호출하면 반복문을 한번만 돌게 할수 있다.
```java
public class Criminal{
    public void alertForMiscreant(List<Person> people) {
        if(!findMiscreant(people).isBlank()) {
            setOffAlarms();
        }
    }

}
```
---
✏️정리
- 값을 조회하는 함수들이 있고 값을 변경하는 함수들이 있는데 이 함수를 구분해서 만들자
- Command Separte규칙이라고하는데 어떤 값을 리턴 하는 함수는 즉 값을 조회하는 함수는 사이드 이펙트가 없어야한다.
- 이 책에서는 눈에 띌만한 (Obersavble) 사이드 이펙트만 분리해야된다고 한다 <br>
  하지만 캐시같은 경우 자주 조회하는 경우 캐시에 담아두기도 하는데 캐시는 분리하지 않아도된다. 눈에 띌만한 사이트이펙트는 아니다.
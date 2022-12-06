## 리팩토링 5. 코드 정리하기
####  Slide Statements

✅배경

- 관련있는 코드끼리 묶여있어야 코드를 더 쉽게 이해할 수 있다.
####
- 함수에서 사용할 변수를 상단에 미리 정의하기 보다는, 해당 변수를 사용하는 코드 바로 위
  에 선언하자
####
- 관련있는 코드끼리 묶은 다음, 함수 추출하기 (Extract Function)를 사용해서 더 깔끔하
  게 분리할 수도 있다.
####

---

✅ 절차

- 코드 조각을 이동할 목표를 찾는다. 다음과 같은 간섭이 있다면 리팩터링을 포기한다.
  - 코드조각에서 참조하는 요소를 선언하는 문장 앞으로는 이동할 수 없다.
  - 코드조각에서 참조하는 요소의 뒤로는 이동할 수 없다.
  - 코드조각에서 참조하는 요소를 수정하는 문장을 건너뛰어 이동할 수 없다.
  - 코드조각에서 수정하는 요소를 참조하는 요소를 건너뛰어 이동할 수 없다.
####
- 추출할 코드를 원본 함수에서 복사하여 새 함수에 붙여 넣는다.
####
- 코드 조각을 원래 위치에서 잘라내어 목표 위치에 붙여 넣는다.


### ✅ 예시 : 조건문이 있을 때의 슬라이드

```java
public class SlideStatements {
  public Object slideStatements(){
    Object result;
    Stack availableResources= new Stack();
    Stack allocatedResources = new Stack<>();
    if(availableResources.size()==0){
      result = createResource();
      allocatedResources.push(result);
    }else{
      result = availableResources.pop();
      allocatedResources.push(result);
    }
    return result;
  }
}
```
⏬ 중복된 문장들을 조건문 밖으로 슬라이드
```java
public class SlideStatements {
  public Object slideStatements(){
    Object result;
    Stack availableResources= new Stack();
    Stack allocatedResources = new Stack<>();
    if(availableResources.size()==0){
      result = createResource();
    }else{
      result = availableResources.pop();
    }
    allocatedResources.push(result);
    return result;
  }
}
```
---

✏️Slide Statements의 목적
- 관련 있는 코드끼리 가까이 둬서 이해하기 더 쉽게하기 위해서이다 
- 다른 리팩토링을 하기 위한 전처리 작업으로 사용되는 코드이고 중복코드를 찾아서 만들도록 하는 작업이다.

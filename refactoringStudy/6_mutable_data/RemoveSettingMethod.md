## 리팩토링 20. 세터 제거하기 
#### Remove Setting Method

### ✅ 배경

- 세터를 제공한다는 것은 해당 필드가 변경될 수 있다는 것을 뜻한다
####
- 객체 생성시 처음 설정된 값이 변경될 필요가 없다면 해당 값을 설정할 수 있는 생성자를
  만들고 세터를 제거해서 변경될 수 있는 가능성을 제거해야 한다
---
### ✅ 절차
1. 설정해야 할 값을 생성자에서 받지 않는다면 그 값을 받을 매개변수를 생성자에 추가한다(함수 선언 바꾸기). 그런 다음 생성자 안에서 적절한 세터를 호출한다.
    - 세터 여러 개를 제거하려면 해당 값 모두를 한꺼번에 생성자에 추가한다. 그러면 이후 과정이 간소해진다.
2. 생성자 밖에서 세터를 호출하는 곳을 찾아 제거하고, 대신 새로운 생성자를 사용하도록 한다. 하나 수정할 때마다 테스트한다.
    - (갱신하려는 대상이 공유 참조 객체라서) 새로운 객체를 생성하는 방식으로는 세터 호출을 대체할 수 없다면 이 리팩터링을 취소
3. 세터 메서드를 인라인한다. 가능하다면 해당 필드를 불변으로 만든다.
4. 테스트
---
### ✅ 예시
✔️간단한 사람 클래스 
```java
public class Person {

    private String name;

    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}


```
⏬ 아이디는 생성자에서 id를 받오록한다. 최초 한번은 id를 설정 할 수 있어야 하므로
```java
public class Person {
    private String name;

    private int id;

    public Person(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

//    public void setId(int id) {
//        this.id = id;
//    }
}

```
⏬ 이렇게 하면 아이디는 세터를 통해서 변경 못한다.
```java
public class Test {
    void person() {
        Person person = new Person(10);
//        person.setId(10);
        person.setName("keesun");
        assertEquals(10, person.getId());
        assertEquals("keesun", person.getName());
        person.setName("whiteship");
        assertEquals("whiteship", person.getName());
    }
}
```
---
✏️정리
- 세터를 통해서 언제든지 필드를 변경할 수 있다는 뜻이다 
- 필드를 변경되지않게 되기를 원한다면 해당 필드값은 생성자를 통해서 넘겨주고 세터는 제거해야한다.
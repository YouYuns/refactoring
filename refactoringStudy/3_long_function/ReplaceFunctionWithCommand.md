## 리팩토링 10. 함수를 명령으로 바꾸기
#### Replace Function with Command

### ✅ 배경

- 함수를 독립적인 객체인, Command로 만들어 사용할 수 있다
####
- 커맨드 패턴을 적용하면 다음과 같은 장점을 취할 수 있다
  - 부가적인 기능으로 undo 기능을 만들 수도 있다
  - 더 복잡한 기능을 구현하는데 필요한 여러 메소드를 추가할 수 있다
  - 상속이나 템플릿을 활용할 수도 있다
  - 복잡한 메소드를 여러 메소드나 필드를 활용해 쪼갤 수도 있다
####
- 대부분의 경우에 “커맨드” 보다는 “함수”를 사용하지만, 커맨드 말고 다른 방법이 없는 경우에만 사용
  한다

---
### ✅ 절차
1. 대상 함수의 기능을 옮길 빈 클래스를 만든다. 클래스 이름은 함수 이름에 기초해 짓는다.
2. 방금 생성한 빈 클래스로 함수를 옮긴다.
    - 리팩터링이 끝날 때까지는 원래 함수를 전달 함수 역할로 남겨두자.
    - 명령 관련 이름은 사용하는 프로그래밍 언어의 명명규칙을 따른다.
    - 규칙이 딱히 없다면 "execute"나 "call" 같이 명령이ㅡ 실행 함수에 흔히 쓰이는 이름을 택하자
3. 함수의 인수들 각각은 명령의 필드로 만들어 생성자를 통해 설정할지 고민해본다.
---
✏ 명령이란?
- 평범한 함수 메커니즘 보다 훨씬 유연하게 함수를 제어하고 표현할 수 있다.
- 되돌리기 undo같은 보조연산을 제공할 수 있으면 수명주기를 더 정밀하게 제어하는데 필요한 매개변수를 만들어주는 메서드도 제공할 수 있다.
- 상속과 훅hook을 이용해 사용자 맞춤형으로 만들 수도 있다.
---
### ✅ 예시
✔건가보험 애플리케이션에서 사용하는 점수 계산 함수
```java
public class ReplaceFunctionWithCommand {
    public class MedicalExam{
        Boolean isSmoker;
    }

    public class Candiate{
        Boolean originState;
    }

    public class ScoringGuide{
        public Boolean stateWithLowCertification(Boolean state){
            return state;
        }
    }

    public Integer score(Candiate candidate, MedicalExam medicalExam, ScoringGuide scoringGuide){
        Integer result = 0;
        Integer healthLevel = 0;
        Boolean highMedicalRiskFlag = false;

        if(medicalExam.isSmoker){
            healthLevel += 10;
            highMedicalRiskFlag = true;
        }

        String certificationGrade = "regular";
        if (scoringGuide.stateWithLowCertification(candidate.originState)) {
            certificationGrade = "low";
            result -= 5;
        }

        //비슷한 코드가 한참 이어짐
        result -= Math.max(healthLevel - 5, 0);
        return result;
    }
}
```
⏬ 시작은 빈 클래스를 만들고 이 함수를 그 클래스로 옮기는 일부터다
```java

public class ReplaceFunctionWithCommand {
    public Integer score(Candiate candidate, MedicalExam medicalExam, ScoringGuide scoringGuide){
        return  new Scorer().execute(candidate, medicalExam, scoringGuide);
    }
    public class Scorer{
        public Integer execute(Candiate candiate, MedicalExam medicalExam, ScoringGuide scoringGuide){
            Integer result = 0;
            Integer healthLevel = 0;
            Boolean highMedicalRiskFlag = false;

            if(medicalExam.isSmoker){
                healthLevel += 10;
                highMedicalRiskFlag = true;
            }

            String certificationGrade = "regular";
            if (scoringGuide.stateWithLowCertification(candiate.originState)) {
                certificationGrade = "low";
                result -= 5;
            }

            //비슷한 코드가 한참 이어짐
            result -= Math.max(healthLevel - 5, 0);
            return result;
        }
    }

}
```
⏬ 명령이 받는 인수들을 생성자로 옮겨서 execute()메서드는 매개변수를 받지않게 한다

```java
public class ReplaceFunctionWithCommand {
    public Integer score(Candiate candidate, MedicalExam medicalExam, ScoringGuide scoringGuide){
        return  new Scorer(candidate, medicalExam, scoringGuide).execute();
    }
    public class Scorer{
        Candiate candiate;
        MedicalExam medicalExam;
        ScoringGuide scoringGuide;

        public Scorer(Candiate candiate, MedicalExam medicalExam, ScoringGuide scoringGuide) {
            this.candiate = candiate;
            this.medicalExam = medicalExam;
            this.scoringGuide = scoringGuide;
        }

        public Integer execute(){
            Integer result = 0;
            Integer healthLevel = 0;
            Boolean highMedicalRiskFlag = false;

            if(this.medicalExam.isSmoker){
                healthLevel += 10;
                highMedicalRiskFlag = true;
            }

            String certificationGrade = "regular";
            if (this.scoringGuide.stateWithLowCertification(this.candiate.originState)) {
                certificationGrade = "low";
                result -= 5;
            }

            //비슷한 코드가 한참 이어짐
            result -= Math.max(healthLevel - 5, 0);
            return result;
        }
    }
}
```
---

✏ ️정리
- 커맨드 패턴 디자인 패턴중 하나인데 함수 하나를 독립적인 커맨드로 분리하게 되면 긴 메서드를 간추리기도 좋고<br>
  하나의 커맨드로 분리를 한 오퍼레이션 자체를 커맨드 역할을 하는 여러 메서드나 필드를 사용해서 잘게 분해하면서 코드를 간추릴수 있따.
- 단점은 그만큼 새로운 클래스나 구조가 변경이 되기 때문에 복잡도가 증가한다. 그만큼 장점도 있기 떄문에 고려한다.
- 먼저 함수분리하기를 고려하고 함수분리하기를 고려해도 이 코드의 위치가 여기가 아니거나 다른 곳에 있어야될꺼같으면 커맨드 패턴 별도의 클래스로 분리하는게 좋다.

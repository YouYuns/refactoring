package me.whiteship.refactoring._03_long_function._10_replace_function_with_command;

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

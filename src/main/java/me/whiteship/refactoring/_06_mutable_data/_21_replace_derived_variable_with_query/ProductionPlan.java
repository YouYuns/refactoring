package me.whiteship.refactoring._06_mutable_data._21_replace_derived_variable_with_query;

import java.util.ArrayList;
import java.util.List;

public class ProductionPlan {

    private double production;
    private List<Double> adjustments = new ArrayList<>();

    public void applyAdjustment(double adjustment) {
        this.adjustments.add(adjustment);
        this.production += adjustment;
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

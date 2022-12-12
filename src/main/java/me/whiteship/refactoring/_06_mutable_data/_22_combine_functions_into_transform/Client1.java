package me.whiteship.refactoring._06_mutable_data._22_combine_functions_into_transform;

import java.time.Month;
import java.time.Year;


//자바 17 버전
//public class Client1 {
//
//    double baseCharge;
//
//    public Client1(Reading reading) {
//        this.baseCharge = baseRate(reading.month(), reading.year()) * reading.quantity();
//    }
//
//    private double baseRate(Month month, Year year) {
//        return 10;
//    }
//
//    public double getBaseCharge() {
//        return baseCharge;
//    }
//}
public class Client1 {

    double baseCharge;

    public Client1(ReadingDTO dto) {
        this.baseCharge = baseRate(dto.month, dto.year) * dto.quantity;
    }

    private double baseRate(Month month, Year year) {
        return 10;
    }

    public double getBaseCharge() {
        return baseCharge;
    }
}
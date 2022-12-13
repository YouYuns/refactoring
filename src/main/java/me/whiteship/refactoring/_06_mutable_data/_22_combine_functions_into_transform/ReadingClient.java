package me.whiteship.refactoring._06_mutable_data._22_combine_functions_into_transform;

import java.time.Month;
import java.time.Year;

public class ReadingClient {
    protected double taxThreshold(Year year){
        return 5;
    }

    protected double baseRate(Month month, Year year){
        return 10;
    }

    protected EnrichReading enrichReading(ReadingDTO readingDTO){
            return new EnrichReading(readingDTO, calculateBaseCharge(readingDTO));
    }
    protected double calculateBaseCharge(ReadingDTO reading) {
        return baseRate(reading.month, reading.year) * reading.quantity;
    }
}

package me.whiteship.refactoring._06_mutable_data._22_combine_functions_into_transform;

import java.time.Month;
import java.time.Year;

public class ReadingDTO {
    String customer;
    double quantity;
    Month month;
    Year year;

    public ReadingDTO(ReadingDTO readingDTO) {
    }
}

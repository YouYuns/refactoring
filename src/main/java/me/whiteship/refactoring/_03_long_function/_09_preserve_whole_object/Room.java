package me.whiteship.refactoring._03_long_function._09_preserve_whole_object;

public class Room {

    DaysTempRange daysTempRange;

    public Room() {
    }

    public DaysTempRange getDaysTempRange() {
        return daysTempRange;
    }

    public void setDaysTempRange(DaysTempRange daysTempRange) {
        this.daysTempRange = daysTempRange;
    }
}

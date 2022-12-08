package me.whiteship.refactoring._03_long_function._09_preserve_whole_object;

public class DaysTempRange {
    Integer low;
    Integer high;

    public DaysTempRange(Integer low, Integer high) {
        this.low = low;
        this.high = high;
    }

    public Integer getLow() {
        return low;
    }

    public Integer getHigh() {
        return high;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }
}

package me.whiteship.refactoring._06_mutable_data._23_change_reference_to_value;

public class TelephoneNumber {

    private String areaCode;

    private String number;

    public TelephoneNumber(String areaCode, String number) {
        this.areaCode = areaCode;
        this.number = number;
    }

    public String areaCode() {
        return areaCode;
    }


    public String number() {
        return number;
    }

}

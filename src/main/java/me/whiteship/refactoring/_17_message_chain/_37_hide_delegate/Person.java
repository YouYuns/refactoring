package me.whiteship.refactoring._17_message_chain._37_hide_delegate;

public class Person {

    private String name;

    private Department department;

    private Photo photo;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Department getDepartment() {
        return department;
    }

    public Photo getPhoto(){ return photo;}

    public void setDepartment(Department department) {
        this.department = department;
    }
}

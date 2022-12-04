package me.whiteship.refactoring._17_message_chain._37_hide_delegate;


import java.time.LocalDate;

public class Photo {

    private  String title;

    private String contents;

    private String location;

    private LocalDate date;

    public Photo(String title, String contents, String location, LocalDate date){
        this.title = title;
        this.contents=contents;
        this.location=location;
        this.date=date;
    }

    public String getTitle(){
        return title;
    }

    public String getLocation(){
        return location;
    }

    public LocalDate getDate(){
        return date;
    }
   public String getContents() {
        return contents;
   }
}

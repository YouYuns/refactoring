package me.whiteship.refactoring._07_divergent_change._25_move_function;

import me.whiteship.refactoring._17_message_chain._37_hide_delegate.Person;
import me.whiteship.refactoring._17_message_chain._37_hide_delegate.Photo;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MoveStatementIntoFunction {


        public String renderPerson(OutputStream outputStream, Person person){

            return "<p>"+person.getName()+"</p>"
                    + renderPhoto(person.getPhoto())
                    + "<p>제목 : "+person.getPhoto().getTitle() + "</p>"
                    + emitPhotoData(person.getPhoto()) + "\n";
        }

    private String renderPhoto(Photo photo) {
            return null;
    }

    public String photoDiv(Photo p){
            return  "<div>, <p>제목 : " + p.getTitle() + "</p>," + emitPhotoData(p) +  "</div> \n";
        }

        public String emitPhotoData(Photo photo){
            return "<p>위치 : "+photo.getLocation()+"</p> <p>날짜 : "+photo.getDate().toString()+"</p> \n";


        }
}

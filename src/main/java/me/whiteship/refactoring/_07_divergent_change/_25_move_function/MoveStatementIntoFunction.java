package me.whiteship.refactoring._07_divergent_change._25_move_function;

import me.whiteship.refactoring._17_message_chain._37_hide_delegate.Person;
import me.whiteship.refactoring._17_message_chain._37_hide_delegate.Photo;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MoveStatementIntoFunction {


        public List<String> renderPerson(OutputStream outputStream, Person person){
            List<String> result = new ArrayList<>();
            result.add("<p>"+person.getName()+"</p>");
            result.add(renderPhoto(person.getPhoto()));
            result.add("<p>제목 : "+person.getPhoto().getTitle()+ "</p>"); //제목출력
            result.add(emitPhotoData(person.getPhoto()));
            result.add("\n");
            return result;
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

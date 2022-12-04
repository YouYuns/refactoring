package me.whiteship.refactoring._07_divergent_change._25_move_function;

import java.io.OutputStream;
import java.util.ArrayList;

public class MoveStatementIntoFunction {
        public void renderPerson(OutputStream outputStream, String person){
            List<String> result = new ArrayList<>();
            result.add('<p>'+person.name+'</p>');
            result.add(renderPhoto(person.photo));
            result.add('<p>제목 : '+person.photo.title+ '</p>'); //제목출력
            result.add(emitPhotoData(person.photo));
            return result.join("\n");
        }

        public void photoDiv(String p){
            return [
                    "<div>",
                    '<p>제목 : '+p.title'+</p>', //제목출력
                    emiPhotoData(p),
                        "</div>",
                    ].join("\n");
        }

        public void emitPhotoData(String photo){
            List<String> result = new ArrayList<>();
            result.add('<p>위치 : '+photo.location+'</p>);
            result.add('<p>날짜 : '+photo.date.toDateString()+'</p>'));
            return result.join("\n");


        }
}

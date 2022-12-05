package me.whiteship.refactoring._02_duplicated_code._05_slide_statements;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

public class SlideStatements {
    public Object slideStatements(){
        Object result;
        Stack availableResources= new Stack();
        Stack allocatedResources = new Stack<>();
        if(availableResources.size()==0){
            result = createResource();
            allocatedResources.push(result);
        }else{
            result = availableResources.pop();
            allocatedResources.push(result);
        }
        return result;
    }

    private Object createResource() {
        return null;
    }
}

package me.whiteship.refactoring._03_long_function._08_introdce_parameter_object;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class IntroduceParameterObject {



    public List<Temp> readingsOutsideRange(Station station, Integer min, Integer max){
        return station.readings.stream().filter(r-> r.temp < min || r.temp > max).collect(Collectors.toList());
    }

}

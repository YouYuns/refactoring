package me.whiteship.refactoring._07_divergent_change._25_move_function;

import java.awt.*;
import java.util.*;
import java.util.List;

public class TrackSummary {

        public Map<String, Double> trackSummary(List<Point> points) {
            Map<String, Double> map = new HashMap();


            Double totalTime = calculateTime();
            Double totalDistance = calculateDistance(points);
            Double pace = totalTime / 60 / totalDistance;

            map.put("time", totalTime);
            map.put("distance", totalDistance);
            map.put("pace", pace);
            return map;

        }

    private Double calculateDistance(List<Point> points) {
            List list = new ArrayList();
        Double result = 0d;
        for (int i = 1; i < points.size(); i++) {
            list.add(points.get(i));
            result += distance(points.get(i - 1), points.get(i));
        }
        return result;
    }
    private Double distance(Point p1, Point p2 ){
        Double EARTH_RADIUS = 3959d; //단위 : 마일(mile)
        Double dLat = radians(p2.getY()) - radians(p1.getX());
        Double dLon = radians(p2.getX()) - radians(p1.getY());
        Double a = Math.pow(Math.sin(dLat / 2), 2)
                + Math.cos(radians(p2.getX()))
                + Math.cos(radians(p1.getY()))
                + Math.pow(Math.sin(dLon / 2), 2);
        Double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
        return EARTH_RADIUS * c;
    }

    private Double radians(Double degrees){
            return degrees * Math.PI / 180;
    }
    private Double calculateTime() {
            return null;
    }



}

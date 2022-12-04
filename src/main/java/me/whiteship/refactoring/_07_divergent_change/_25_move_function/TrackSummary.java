package me.whiteship.refactoring._07_divergent_change._25_move_function;

import java.util.ArrayList;

public class TrackSummary {

        public void trackSummary(Double[] points) {
                    List<String> list = new ArrayList<>();


                Double totalTime = calculateTime();
                Double pace = totalTime / 60 / totalDistance(points);
                return {
                        time :totalTime,
                        distance :totalDistance(points),
                        pace:pace
                 };

                    //최상위로 복사하면서 새로운 (임시) 이름을 지어줌
                    public Double totalDistance(points) {
                    Double result = 0;
                        for (int i = 1; i < points.length; i++) {
                            list.add(points.get(i))
                            Double += distance(points.get(i - 1), point.get(i));
                        }
                        return result;
                 }

                public distance(Double p1, Double p2) {
                    Double EARTH_RADIUS = 3959; //단위 : 마일(mile)
                    Double dLat = radians(p2.lat) - radians(p1.lat);
                    Double dLon = radians(p2.lon) - radians(p1.lon);
                    Double a = Math.pow(Math.sin(dLat / 2), 2)
                            + Math.cos(radians(p2.lat))
                            + Math.cos(radians(p1.lat))
                            + Math.pow(Math.sin(dLon / 2), 2);
                    Double c = 2 * Math.atan2(Math.sqrt(a),Math.sqrt(1-a));
                    return EARTH_RADIUS * c;
                } //두 지점의 거리계산
                public radians(Double degrees) {
                return degrees * Math.PI / 180;
                } // 라디안 값으로 변환
                public calculateTime() {
                }  //총 시간계산
            }


        }

}

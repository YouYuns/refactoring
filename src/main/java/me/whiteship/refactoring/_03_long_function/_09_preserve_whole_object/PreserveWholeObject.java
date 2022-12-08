package me.whiteship.refactoring._03_long_function._09_preserve_whole_object;

public class PreserveWholeObject {

            Room room = new Room();
            DaysTempRange tempRange = room.daysTempRange;
            Integer low = tempRange.low;
            Integer high = tempRange.high;
          Boolean isWithinRange = withinRange(tempRange);
            public void 호출자(){
                if(!isWithinRange){
                    System.out.println("방 온도가 지정 범위를 벗어났습니다.");
                }
            }
    TeamperatureRange teamperatureRange = new TeamperatureRange();

    public Boolean withinRange(DaysTempRange numberRange){
      return (numberRange.low >= this.teamperatureRange.low) && (numberRange.high <= this.teamperatureRange.high);
    }

}

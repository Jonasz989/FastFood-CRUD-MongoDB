package Restaurant.System;

public class TimeParser {

    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    public static String deleteLastCharAtString(String string) { //repairing date string to make it parsable
        StringBuffer sb = new StringBuffer(string);
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

}

package Restaurant.System;

public class TimeParser {


    public static String deleteLastCharAtString(String string) { //repairing date string to make it parsable
        StringBuffer sb = new StringBuffer(string);
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

}

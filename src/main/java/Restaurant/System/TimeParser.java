/**
 * Class for parsing time to discount
 */

package Restaurant.System;

import java.time.LocalDateTime;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//CLASS
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public class TimeParser {

    public static String deleteLastCharAtString(String string) { //repairing date string to make it parsable
        StringBuilder sb = new StringBuilder(string);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    public static Boolean checkIfBonusShouldBeApplied(LocalDateTime data) {
        LocalDateTime data2 = LocalDateTime.now();
        LocalDateTime data3 = data2.minusMinutes(5);
        return data.compareTo(data3) == -1;
    }

}

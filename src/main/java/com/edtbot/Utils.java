package com.edtbot;

import java.time.Instant;
import java.util.Calendar;

/**
 * Created by Ivan on 24/09/2018.
 *
 * @author Ivan
 */
public class Utils {

    private static String adeToken = null;
    private static Instant adeTokenExpiry = null;

    public static String getSchoolYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        if (month>=Calendar.JANUARY && month<Calendar.AUGUST) return (year-1)+"-"+year;
        else return year+"-"+(year+1);
    }

    /*public static String getADEToken() {
        if (adeToken==null || adeTokenExpiry.isAfter(Instant.now())) {
            // FUCK THIS SHIT !!!!
            // Look at this : https://storage.googleapis.com/google-code-archive-downloads/v2/code.google.com/ade2ics/ade2ics-v3.3.pl
        }
        return adeToken;
    }*/

    public static String getWeekURL(String rootURL, int projectID, String resName) {
        return rootURL +
                "?clearTree=false&projectId="+projectID +
                "&name="+resName +
                "&displayConfName=esisar_standard_impression";
    }

    public static int getADEWeekID() {
        Calendar cal = Calendar.getInstance();
        int week = cal.get(Calendar.WEEK_OF_YEAR);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        if (day==Calendar.SATURDAY || day==Calendar.SUNDAY) return getADEWeekID(week+1);
        else return getADEWeekID(week);
    }

    public static int getADEWeekID(int baseWeek) {
        return baseWeek - 32;
    }

    public static String getWeekImageURL(String rootURL, String token, int projectID, int week, int[] ressources, int width, int height) {
        StringBuilder resStr = new StringBuilder();
        for (int i=0; i<ressources.length; i++) {
            resStr.append(ressources[i]);
            if (i<ressources.length-1) resStr.append("%2C");
        }
        return rootURL+"/jsp/imageEt" +
                "?identifier="+token +  // Session token
                "&projectId="+projectID +                               // ADE project
                "&idPianoWeek="+week+"&idPianoDay=0%2C1%2C2%2C3%2C4" +  // Week number, selected days
                "&idTree="+resStr.toString() +                          // Selected ressources
                "&width="+width+"&height="+height +                     // Image size
                "&lunchName=REPAS&displayMode=1057855&showLoad=false&ttl=1537801781248&displayConfId=15"; // Scrap
    }
}

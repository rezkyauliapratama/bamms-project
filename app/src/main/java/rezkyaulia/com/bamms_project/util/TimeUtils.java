package rezkyaulia.com.bamms_project.util;

import android.support.annotation.Nullable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * Created by Rezky Aulia Pratama on 14/6/18.
 */
@Singleton
public class TimeUtils {

    @Inject
    public TimeUtils() {
    }

        public String getUserFriendlyDate(Date date){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy");
            return simpleDateFormat.format(date);
        }

        public String getUserFriendlyDateTime(Date date){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy MMM,dd HH:mm");
            return simpleDateFormat.format(date);
        }


        public String getHowLongItHasBeen(Long timestamp){
        Timber.e("getHowLongItHasBeen : "+timestamp);
           /* Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(timestamp);
            Timber.e("DATE : "+getUserFriendlyDate(cal.getTime()));*/
            return calculateHowLongItHasBeen(timestamp);
        }

        public long getTriggerEveryHours(){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MINUTE,1);
            return cal.getTimeInMillis();
        }
        private String calculateHowLongItHasBeen(long milis){

            long dayCount = TimeUnit.DAYS.convert(milis,TimeUnit.MILLISECONDS);
            long hourCount = TimeUnit.HOURS.convert(milis,TimeUnit.MILLISECONDS);
            long minuteCount = TimeUnit.MINUTES.convert(milis,TimeUnit.MILLISECONDS);


            String ret = "";

            if (dayCount>0){
                if (dayCount==1){
                    ret = dayCount+" day";
                }else{
                    ret = dayCount+" days";
                }
            }else{
                if (hourCount > 0){
                    if (hourCount == 1){
                        ret = hourCount+" hour";
                    }else{
                        ret = hourCount+" hours";
                    }
                }else{
                    if (minuteCount > 0){
                        if (minuteCount == 1){
                            ret = minuteCount+" minute";
                        }else{
                            ret = minuteCount+" minutes";
                        }
                    }
                }
            }

            return ret;
        }

        @Nullable
        private Date convertStringToDate (String str){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date date = format.parse(str);
                return date;
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return null;
        }

        public int convertCalToMonth(Calendar cal){
            return cal.get(Calendar.MONTH) + 1;
        }

        public int getMonthForCal(int i){
            return i - 1;
        }

}

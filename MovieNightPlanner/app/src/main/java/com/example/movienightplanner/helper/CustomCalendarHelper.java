package com.example.movienightplanner.helper;

import android.widget.GridView;

import com.example.movienightplanner.adapter.CustomListAdapter_DayGrids;
import com.example.movienightplanner.controllers.CalendarViewActivity;
import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.DayBean;
import com.example.movienightplanner.models.EventImpl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CustomCalendarHelper {

    static Calendar calendar = Calendar.getInstance();

    //get numbers of days of current month
    public static int getCurrentMonthDay(long millSec) {
        calendar.setTimeInMillis(millSec);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int dateCount = calendar.get(Calendar.DATE);
        return dateCount;
    }

    //get the first day
    public static long getFirOfMonth(long millSec) {

        calendar.setTimeInMillis(millSec);
        calendar.set(Calendar.DATE, 1);
        return calendar.getTimeInMillis();
    }

    //get current time
    public static long getCurrentTime() {
        return calendar.getTimeInMillis();
    }

    //get last month
    public static long getLastMonth(long millSec, int count) {

        calendar.setTimeInMillis(millSec);
        calendar.add(Calendar.MONTH, count);
        return calendar.getTimeInMillis();
    }

    //get next month
    public static long getNextMonth(long millSec, int count) {

        calendar.setTimeInMillis(millSec);
        calendar.add(Calendar.MONTH, count);
        return calendar.getTimeInMillis();
    }

    //format to month
    public static String long2str(long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        return sdf.format(new Date(millSec));
    }

    public static void setCalendarList(long millSecs, List<DayBean> beans, GridView calendarGrids, CustomListAdapter_DayGrids adapter, CalendarViewActivity context) {
        beans.clear();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millSecs);
        int max = CustomCalendarHelper.getCurrentMonthDay(millSecs);

        //calendar of this month
        for (int i = 1; i <= max; i++) {
            DayBean bean = new DayBean();
            Calendar cc = Calendar.getInstance();
            cc.setTimeInMillis(millSecs);
            cc.add(Calendar.DAY_OF_MONTH, i - 1);
            setBean(bean, cc);
            bean.setIsCurrentMonth(true);

            //check if there is a event on that day
            AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

            for (EventImpl event : appEngine.eventLists) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                if (sdf.format(event.getDateTime()).equals(sdf.format(cc.getTime()))) {
                    bean.setHasEvent(true);
                }
            }

            beans.add(bean);
        }

        //calculate which days are from last month
        int fir_day_of_week = beans.get(0).getCalendar().get(Calendar.DAY_OF_WEEK);
        if (fir_day_of_week != 2) {
            if (fir_day_of_week == 1) {
                for (int i = 0; i < 6; i++) {
                    DayBean bean = new DayBean();
                    Calendar cc = Calendar.getInstance();
                    cc.setTimeInMillis(millSecs);
                    cc.add(Calendar.DAY_OF_MONTH, -i - 1);
                    setBean(bean, cc);
                    bean.setIsCurrentMonth(false);
                    beans.add(0, bean);
                }
            } else {
                for (int i = 0; i < fir_day_of_week - 2; i++) {
                    DayBean bean = new DayBean();
                    Calendar cc = Calendar.getInstance();
                    cc.setTimeInMillis(millSecs);
                    cc.add(Calendar.DAY_OF_MONTH, -i - 1);
                    setBean(bean, cc);
                    bean.setIsCurrentMonth(false);
                    beans.add(0, bean);
                }
            }

        }
        //calculate which days are from next month
        int last_day_of_week = beans.get(beans.size() - 1).getCalendar().get(Calendar.DAY_OF_WEEK);
        if (last_day_of_week != 1) {
            for (int i = last_day_of_week; i < 8; i++) {
                DayBean bean = new DayBean();
                Calendar cc = Calendar.getInstance();
                cc.setTimeInMillis(millSecs);
                cc.add(Calendar.DAY_OF_MONTH, i - last_day_of_week);
                setBean(bean, cc);
                bean.setIsCurrentMonth(false);
                beans.add(bean);
            }
        }
        adapter = new CustomListAdapter_DayGrids(context, beans);
        calendarGrids.setAdapter(adapter);

    }

    private static void setBean(DayBean bean, Calendar cc) {
        bean.setCalendar(cc);
        bean.setDay(cc.get(Calendar.DAY_OF_MONTH));
    }
}

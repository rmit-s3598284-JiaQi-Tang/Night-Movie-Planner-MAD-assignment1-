package com.example.movienightplanner.helper;

        import android.content.Context;
        import android.util.Log;
        import android.widget.GridView;

        import com.example.movienightplanner.adapter.CustomListAdapter_DayGrids;
        import com.example.movienightplanner.controllers.CalendarViewActivity;
        import com.example.movienightplanner.models.DayBean;

        import java.text.SimpleDateFormat;
        import java.util.Calendar;
        import java.util.Date;
        import java.util.List;

public class CustomCalendarHelper {

    static int count = 0;
    static Calendar calendar = Calendar.getInstance();
    //get numbers of days of current month
    public static int getCurrentMonthDay(long millSec) {
        calendar.setTimeInMillis(millSec);
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);
        int dateCount = calendar.get(Calendar.DATE);
        return dateCount;
    }

    /**
     *   * 获取当月第一天
     *   
     */
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
    public static long getLastMonth(long millSec) {
        count -=1;
        calendar.setTimeInMillis(millSec);
        calendar.add(Calendar.MONTH, count);
        return calendar.getTimeInMillis();
    }

    //get next month
    public static long getNextMonth(long millSec) {
        count +=1;
        calendar.setTimeInMillis(millSec);
        calendar.add(Calendar.MONTH, count);
        return calendar.getTimeInMillis();
    }

    /**
     *   * 格式化到月份
     *   
     */
    public static String long2str(long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        return sdf.format(new Date(millSec));
    }

    public static void setCalendarList(long millSecs, List<DayBean> beans, GridView calendarGrids, CalendarViewActivity context) {
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
            beans.add(bean);
        }
        //calculate which days are from last month
        int fir_day_of_week = beans.get(0).getCalendar().get(Calendar.DAY_OF_WEEK);
        Log.e("AAA", "week_last:" + fir_day_of_week);
        if (fir_day_of_week != 2) {
            if (fir_day_of_week == 1) {
                for (int i = 0; i < 6; i++) {
                    DayBean bean = new DayBean();
                    Calendar cc = Calendar.getInstance();
                    cc.setTimeInMillis(millSecs);
                    cc.add(Calendar.DAY_OF_MONTH, -i - 1);
                    setBean(bean, cc);
                    Log.e("AAA", "last:" + bean.getDay());
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
                    Log.e("AAA", "last:" + bean.getDay());
                    bean.setIsCurrentMonth(false);
                    beans.add(0, bean);
                }
            }

        }
        //calculate which days are from next month
        int last_day_of_week = beans.get(beans.size() - 1).getCalendar().get(Calendar.DAY_OF_WEEK);
        Log.e("AAA", "week_next:" + last_day_of_week);
        if (last_day_of_week != 1) {
            for (int i = last_day_of_week; i < 8; i++) {
                DayBean bean = new DayBean();
                Calendar cc = Calendar.getInstance();
                cc.setTimeInMillis(millSecs);
                cc.add(Calendar.DAY_OF_MONTH, i - last_day_of_week);
                setBean(bean, cc);
                Log.e("AAA", "next:" + bean.getDay());
                bean.setIsCurrentMonth(false);
                beans.add(bean);
            }
        }
        CustomListAdapter_DayGrids adapter = new CustomListAdapter_DayGrids(context, beans);
        calendarGrids.setAdapter(adapter);
    }

    private static void setBean(DayBean bean, Calendar cc) {
        bean.setCalendar(cc);
        bean.setDay(cc.get(Calendar.DAY_OF_MONTH));
    }
}

package com.eric.ui;

import android.os.Bundle;
import android.view.View;

import com.eric.ui.common.BaseActivity;
import com.eric.ui.utils.DateAction;
import com.eric.view.ButtonTextView;
import com.eric.view.wheelWidget.OnItemSelectedListener;
import com.eric.view.wheelWidget.WheelViewDialogHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

/**
 * @author li
 * @Package com.eric.ui
 * @Title: TimerSelectActivity
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */

public class TimerSelectActivity extends BaseActivity {


    private String[] mDayChoose = {"现在", "今天", "明天"};
    private String[] mHourChoose = {"00点", "01点", "02点", "03点", "04点", "05点", "06点", "07点", "08点", "09点", "10点", "11点", "12点",
            "13点", "14点", "15点", "16点", "17点", "18点", "19点", "20点", "21点", "22点", "23点"};
    private String[] mHourNoPointChoose = {"00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13",
            "14", "15", "16", "17", "18", "19", "20", "21", "22", "23"};
    private String[] mSecondNopointChoose = {"00", "30"};
    private String[] mSecondChoose = {"00分", "30分"};

    private ButtonTextView btn1;
    private ButtonTextView btn2;
    private ButtonTextView btn3;

    private WheelViewDialogHelper dialogHelper1;
    private WheelViewDialogHelper dialogHelper2;
    private WheelViewDialogHelper dialogHelper3;

    private List<String> data1;

    private List<String> mDayList = new ArrayList<>();
    private List<List<String>> mHourList = new ArrayList<>();
    private List<List<List<String>>> mSecondList = new ArrayList<>();


    private List<String> mDayList3 = new ArrayList<>();
    private List<List<String>> mHourList3 = new ArrayList<>();
    private List<List<List<String>>> mSecondList3 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_select);
        init1();
        init2();
        init3();
    }

    private void init1() {
        btn1 = findViewById(R.id.view_button_1);
        dialogHelper1 = new WheelViewDialogHelper(this);
        dialogHelper1.setSelectOptions(0);
        data1 = getData1();
        dialogHelper1.setPicker(data1);
        dialogHelper1.setOnOptionsSelectListener(new WheelViewDialogHelper.OnOptionsSelectListener() {
            @Override
            public boolean onOptionsSelect(int options1, int option2, int options3) {
                showToast(data1.get(options1) + "被选中了");
                return true;
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHelper1.show();
            }
        });
    }

    private List<String> getData1() {
        List<String> list = new ArrayList();
        list.add("今天");
        list.add("明天");
        list.add("后天");
        list.add("后天");
        list.add("后天");
        list.add("后天");
        list.add("后天");
        list.add("后天");
        list.add("后天");
        return list;
    }

    private void init2() {

        btn2 = findViewById(R.id.view_button_2);
        initDialog2Data();
        dialogHelper2 = new WheelViewDialogHelper(this);
        dialogHelper2.setOption1WheelListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (index == 0) {
                    dialogHelper2.setCurrentItems(0, 0, 0);
                    dialogHelper2.setPicker(mDayList);
                } else {
                    dialogHelper2.setCurrentItems(index, 0, 0);
                    dialogHelper2.setPicker(mDayList, mHourList, mSecondList, false);
                }
            }
        });
        dialogHelper2.setOption2WheelListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                dialogHelper2.setSelectOptions3(0);
            }
        });
        dialogHelper2.setOnOptionsSelectListener(new WheelViewDialogHelper.OnOptionsSelectListener() {
            @Override
            public boolean onOptionsSelect(int options1, int option2, int options3) {
                showToast("被选中了" + options1 + "   " + option2 + "   " + options3 + "   ");
                return true;
            }
        });
        dialogHelper2.setPicker(mDayList);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHelper2.show();
            }
        });
    }

    private void initDialog2Data() {

        mDayList = Arrays.asList(mDayChoose);


        final List<String> hourslists = Arrays.asList(mHourChoose);
        mHourList.add(hourslists);

        List<String> secondLists = Arrays.asList(mSecondChoose);
        final List<String> secondList2 = Arrays.asList(mSecondNopointChoose);
        List<List<String>> seconds = new ArrayList<>();
        seconds.add(secondLists);
        mSecondList.add(seconds);
    }


    private void init3() {
        initDialog3Data();
        btn3 = findViewById(R.id.view_button_3);
        dialogHelper3 = new WheelViewDialogHelper(this);
        dialogHelper3.setPicker(mDayList3, mHourList3, mSecondList3, true);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHelper3.show();
            }
        });
    }


    public void initDialog3Data() {
        int cureentItemLeft = 0, cureentItemRight = 0;
        ArrayList<String> leftStrs = new ArrayList<String>();
        // 当前日期
        long availableDate = System.currentTimeMillis();
        String str;
        String week;
        int availableHour = Integer.parseInt(DateAction.dateFormate(availableDate, "HH")) + 1;
        int availableMinutes = Integer.parseInt(DateAction.dateFormate(availableDate, "mm"));
        boolean startFromTomorrow = false;
        if (availableHour > 23) {
            availableDate += 86400000l;
            startFromTomorrow = true;
        }
        for (int i = 0; i < 30; i++) {
            if (startFromTomorrow) {
                if (i == 0) {
                    week = "明天";
                } else {
                    week = getWeek(availableDate);
                }
            } else {
                if (i == 0) {
                    week = "今天";
                } else if (i == 1) {
                    week = "明天";
                } else {
                    week = getWeek(availableDate);
                }
            }
            str = week + " " + DateAction.dateFormate(availableDate, "MM月dd日");
            leftStrs.add(str);
            availableDate += 86400000l;
        }
        ArrayList<String> rightStrs = new ArrayList<String>();
        int startHour = availableHour;
        for (int i = startHour; i <= 23; i++) {
            rightStrs.add(i + "时");
        }
        ArrayList minutesStrs = new ArrayList<String>();
        for (int i = availableMinutes; i <= 59; i++) {
            minutesStrs.add(i + "分");
        }
        mDayList3.addAll(leftStrs);
        mHourList3.add(rightStrs);
        ArrayList<String> temp;
        for (int i = 0; i < 29; i++) {
            temp = new ArrayList<String>();
            for (int j = 0; j <= 23; j++) {
                temp.add(j + "时");
            }
            mHourList3.add(temp);
        }

        ArrayList<List<String>> hour;
        ArrayList<String> minute;
        for (int i = 0; i < 30; i++) {
            hour = new ArrayList<>();
            for (int j = 0; j < 24; j++) {
                minute = new ArrayList<>();
                for (int k = 0; k < 60; k++) {
                    minute.add(k + "分");
                }
                hour.add(minute);
            }
            mSecondList3.add(hour);
        }
    }

    public static String getWeek(long date) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekDays[w];
    }

}

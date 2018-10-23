package com.eric.ui.utils;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * @author li
 * @Package com.eric.ui.utils
 * @Title: Utils
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class Utils {
    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add(i + "");
        }
        return data;
    }

    public static String geneUserName() {
        String[] familyNames = {"诸葛", "吴", "王", "李", "张", "孔", "韩", "赵", "蔡"};
        String[] middleNames = {"天", "一", "锦", "泽", "明", "星", "新", "东", "空"};
        String[] firstNames = {"刚", "琼", "华", "楷", "文", "", "健", "玮", ""};
        return familyNames[getRadom() % 9] + middleNames[getRadom() % 9] + firstNames[getRadom() % 9];
    }

    public static String geneNumber() {
        String[] seconds = {"3", "5", "8"};
        String number = "1" + seconds[getRadom() % 3] + getRadom() + "58" + getRadom() + getRadom() + getRadom() + getRadom() + getRadom() + getRadom();
        return number;
    }

    private static int getRadom() {
        return (int) (Math.random() * 10) % 10;
    }
}

package com.sundar.sundarpinyintext;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //单独汉字
    PinyinText textPT;
    //汉字设置字体
    PinyinText text_ttf;
    //单独拼音
    PinyinText pinyinPT;
    //拼音带字体
    PinyinText pinyin_ttf;
    //拼音汉字
    PinyinText pinyinTextPT;
    //拼音汉字带字体
    PinyinText pinyinText_ttf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textPT = (PinyinText) findViewById(R.id.textPT);
        text_ttf = (PinyinText) findViewById(R.id.text_ttf);
        pinyinPT = (PinyinText) findViewById(R.id.pinyinPT);
        pinyin_ttf = (PinyinText) findViewById(R.id.pinyin_ttf);
        pinyinTextPT = (PinyinText) findViewById(R.id.pinyinTextPT);
        pinyinText_ttf = (PinyinText) findViewById(R.id.pinyinText_ttf);

        init();
    }

    private void init() {
        String pinyinText="#yǒng#yuǎn# #Shēngrì# # #yǒng#yuǎn# #Shēngrì# # #yǒng#yuǎn# #Shēngrì# #";
        String chineseText="#永#远# ____ 。#生日# ____！# #永#远# ____ 。#生日# ____！# #永#远# ____ 。#生日# ____！#";
        String chineseNorText="生日生日生日生日";
        String pinyinNorText="ShēngrìShēngrìShēngrì";
        List<Pair<String, String>> pairList = new ArrayList<>();
        String[] mChinese = chineseText.split("\\#");
        String[] mPinyin = pinyinText.split("\\#");
        for (int i = 0; i < mChinese.length; i++) {
            if (i < mPinyin.length) {
                pairList.add(Pair.create("" + mChinese[i], "" + mPinyin[i]));
                Log.e("aaaaaa", "字：" + mChinese[i] + "拼音" + mPinyin[i]);
            }
        }
        //汉字
        textPT.setText(chineseNorText);
        textPT.setTextColor(0xefef6688);
        //汉字带字体
        text_ttf.setText(chineseNorText,"mengmengda.ttf");
        //拼音
        pinyinPT.setText(pinyinNorText);
        //拼音带字体
        pinyin_ttf.setText(pinyinNorText,"mengmengda.ttf");
        pinyin_ttf.setTextColor(0x88FF9912);
        pinyin_ttf.setTextSize(60);
        //普通的拼音汉字结构
        pinyinTextPT.setPinyinText(pairList);
        //普通的拼音汉字结构带字体
        pinyinText_ttf.setPinyinText(pairList,"mengmengda.ttf");
        pinyinText_ttf.setPinyinTextColor(0x889933FA);
        pinyinText_ttf.setPinyinTextSize(50);
        pinyinText_ttf.setVerticalSpacing(100);
    }
}

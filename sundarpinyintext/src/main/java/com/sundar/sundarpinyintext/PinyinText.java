package com.sundar.sundarpinyintext;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SundarFung on 2017/3/14.
 * email : SundarFung@gmail.com
 */
public class PinyinText extends View{
    //该控件用到的属性
    private static final int[] ATTRS = {android.R.attr.textSize, android.R.attr.textColor,
            android.R.attr.textColorHint, android.R.attr.horizontalSpacing, android.R.attr.verticalSpacing};
    /**
     * 像素单位的Size 与android:textSize对应
     * */
    private int mTextSize;
    /**
     * 两个item之间的距离
     * 等同于android:horizontalSpacing
     */
    private int mHorizontalSpacing;
    /**
     * 两行之间的距离(text过长换行之后两行)
     * 对应android:verticalSpacing
     */
    private int mVerticalSpacing;
    /**
     * 每组拼音和汉字之间的距离 {@link #mVerticalSpacing} / 2.
     */
    private int mPinyinTextSpacing;
    private static final float PINYIN_TEXT_SIZE_RADIO = 0.8F;
    //拼音字体的size 等同于 {@link #mTextSize} * {@value #PINYIN_TEXT_SIZE_RADIO}  是汉字的0.8倍
    private int mPinyinTextSize;
    //汉字文本
    private String mTextString;
    //拼音文本
    private String mPinyinString;
    //画笔，用来绘制汉字和拼音
    private TextPaint mPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG | Paint.LINEAR_TEXT_FLAG);
    //汉字计算的高度
    private int mTextHeight;
    //拼音计算的高度
    private int mPinyinHeight;
    // bounds
    private Rect mBounds = new Rect();
    //是否为该text设置字体
    private boolean isHasType = false;
    //字体库路径/名
    private String textType= "";
    //汉字的颜色 等同于android:textColor
    private int mTextColor;
    /**
     * 要绘制的模式. Must be one value of {@link #TYPE_PINYIN_AND_TEXT} or {@link #TYPE_PLAIN_TEXT}
     */
    private int mDrawType = TYPE_PLAIN_TEXT;
    /**
     * 非拼音汉字上下结构，只显示其中一个的模式
     */
    public static final int TYPE_PLAIN_TEXT = 1;
    /**
     * 上拼音下汉字的模式
     */
    public static final int TYPE_PINYIN_AND_TEXT = 2;

    //拼音的颜色 android:textColorHint
    private int mPinyinTextColor;
    // 文本数据
    private List<PinyinCompat> mPinyinCompats = new ArrayList<PinyinCompat>();
    public PinyinText(Context context) {
        super(context);
        init(context, null);
    }

    public PinyinText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PinyinText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (this.isInEditMode()) { // 解决可视化编辑器无法识别自定义控件的问题
            return;
        }
        //初始化默认值
        initDefault();

        if (attrs == null) {
            return;
        }
        //获取属性与值
        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        mTextSize = a.getDimensionPixelSize(0, mTextSize);
        a.recycle();
        mPinyinTextSpacing = mVerticalSpacing / 2;

        setTextSize(mTextSize);
    }
    /**
     * 初始化默认值
     * **/
    private void initDefault() {
        Context c = getContext();
        Resources r;

        if (c == null) {
            r = Resources.getSystem();
        } else {
            r = c.getResources();
        }

        DisplayMetrics dm = r.getDisplayMetrics();

        // 汉字默认 14sp
        mTextSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, dm);
        //拼音size
        mPinyinTextSize = (int) (mTextSize * PINYIN_TEXT_SIZE_RADIO);
        // spacing
        mVerticalSpacing   = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, dm);
        mHorizontalSpacing = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, dm);
        mPinyinTextSpacing = mHorizontalSpacing / 2;

        // 设置默认的颜色(8位16进制)
        mTextColor = 0xff333333;
        mPinyinTextColor = 0xff999999;
        mPaint.setStyle(Paint.Style.FILL);
    }
    /**
     * 设置只有拼音或者汉字的内容
     * @param text plain text to display.
     */
    public void setText(String text) {
        mDrawType = TYPE_PLAIN_TEXT; // set draw type

        clearAll();

        this.mTextString = text;

        requestLayout();
        invalidate();
    }

    /***
     * 只显示拼音字体并设置拼音的字体
     * **/
    public void setText(String answerPinyin, String type) {
        setText(answerPinyin);
        isHasType=false;
        textType="";
        if(null!=type && !type.equals("")){
            isHasType = true ;
            textType = type;
        }
    }
    /**
     * 设置单独文字的size 拼音是汉字的0.8倍
     * @param px - text size in pixels
     */
    public void setTextSize(int px) {
        if (px < 2) {
            throw new IllegalArgumentException("Text size must larger than 2px");
        }
        mTextSize = px;
        setPinyinTextSize((int) (px * PINYIN_TEXT_SIZE_RADIO));
    }
    /**
     * 设置拼音的size,如果没有设置的话会调用getTextSize()去获取
     * @param px - pinyin text size in pixels
     */
    public void setPinyinTextSize(int px) {
        mPinyinTextSize = px;
        if (mPinyinTextSize <= 0) {
            throw new IllegalArgumentException("Pinyin text size must larger than 1px");
        }

        // 计算汉字和拼音的高度
        calTextHeight();
        requestLayout();
        invalidate();
    }
    private void calTextHeight() {
        // 计算汉字高度
        if (!TextUtils.isEmpty(mTextString)) {
            mPaint.setTextSize(mTextSize);
            mPaint.getTextBounds(mTextString, 0, mTextString.length(), mBounds);
            mTextHeight = mBounds.height();
        } else {
            mTextHeight = 0;
        }

        // 计算拼音高度
        if (!TextUtils.isEmpty(mPinyinString)) {
            mPaint.setTextSize(mPinyinTextSize);
            mPaint.getTextBounds(mPinyinString, 0, mPinyinString.length(), mBounds);
            mPinyinHeight = mBounds.height();
        } else {
            mPinyinHeight = 0;
        }
    }
    /**
     * 这是汉字颜色
     * 等同于android:textColor
     *  8位16进制颜色
     * @param color text color.
     */
    public void setTextColor(int color) {
        mTextColor = color;
        invalidate();
    }

    /**
     * 设置拼音颜色
     * 等同于android:textColorHint
     * 8位16进制颜色
     * @param color pinyin text color.
     */
    public void setPinyinTextColor(int color) {
        mPinyinTextColor = color;
        invalidate();
    }
    /**
     * 直接设置拼音和汉字之间的距离
     */
    public void setLineSpacing(int px) {
        setVerticalSpacing(px);
    }
    /**
     * 设置两行之间的距离
     */
    public void setVerticalSpacing(int px) {
        mVerticalSpacing = px;
        //汉字与拼音的距离是两行距离的一半
        mPinyinTextSpacing = mVerticalSpacing / 2;
        requestLayout();
        invalidate();
    }
    /**
     * 设置两个item之间的距离
     */
    public void setHorizontalSpacing(int px) {
        mHorizontalSpacing = px;
        requestLayout();
        invalidate();
    }

    /**
     * 设置上拼音下汉字的结构内容
     */
    public void setPinyinText(List<Pair<String, String>> pinyinList) {
        //设置要绘制的模式
        mDrawType = TYPE_PINYIN_AND_TEXT;

        clearAll(); // clear what is shown

        StringBuilder textBuilder = new StringBuilder();
        StringBuilder pinyinBuilder = new StringBuilder();
        for (Pair<String, String> pair : pinyinList) {
            String src = pair.first;
            String trg = pair.second;
            if (src == null) {
                src = "";
            }
            if (trg == null) {
                trg = "";
            }
            textBuilder.append(src);
            pinyinBuilder.append(trg);

            PinyinCompat compat = new PinyinCompat();
            compat.text = src;
            compat.pinyin = trg;
            compat.textRect = new Rect();
            compat.pinyinRect = new Rect();
            mPinyinCompats.add(compat);
        }

        // string buffer
        mTextString = textBuilder.toString();
        mPinyinString = pinyinBuilder.toString();

        // 计算拼音和汉字的高度
        calTextHeight();

        requestLayout();
        invalidate();
    }
    private void clearAll() {
        mPinyinCompats.clear(); // clear

        mTextString = null;
        mPinyinString = null;

        mTextHeight = 0;
        mPinyinHeight = 0;
    }
    /***
     * 设置上拼音下汉字的结构内容 同时指定拼音的字体
     * */
    public void setPinyinText(List<Pair<String, String>> answer, String pinyinType) {
        setPinyinText(answer);
        isHasType=false;
        textType="";
        if(null!=pinyinType && !pinyinType.equals("")){
            isHasType = true ;
            textType = pinyinType;
        }
    }
    static class PinyinCompat {
        String text;
        String pinyin;

        Rect textRect;
        Rect pinyinRect;
    }
}

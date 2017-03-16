
## 目前更新到 1.0.6版本

可显示上拼音下汉字的text格式或普通text格式 该项目源于[pinyin-text-view](https://github.com/titanseason/pinyin-text-view)，对其做了一些修正后进行了进一步的优化与扩展


![1.0.6_1.jpg](http://upload-images.jianshu.io/upload_images/4905074-52608f6c5de9b05c.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/600)


## Gradle:

#### Step 1.
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
#### Step 2.
```
dependencies {
      compile 'com.github.SundarFung:sundarPinyinText:1.0.6'
            }
```
## maven:

#### Step 1.

```
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
#### Step 2.

```
<dependency>
    <groupId>com.github.SundarFung</groupId>
    <artifactId>sundarPinyinText</artifactId>
    <version>1.0.6</version>
</dependency>
```

> 使用方法

 	<com.sundar.sundarpinyintext.PinyinText
        android:id="@+id/textPT"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="28dp"
        android:layout_centerHorizontal="true"
        />
	
***
```
   	 //汉字
        textPT.setText(chineseNorText);
        textPT.setTextColor(0xefef6688);//颜色是8位16进制
        //汉字带字体
        text_ttf.setText(chineseNorText,"mengmengda.ttf");//设置非拼音汉字结构的字体
        //拼音
        pinyinPT.setText(pinyinNorText);
        //拼音带字体
        pinyin_ttf.setText(pinyinNorText,"mengmengda.ttf");
        pinyin_ttf.setTextColor(0x88FF9912);
        pinyin_ttf.setTextSize(60);
        //普通的拼音汉字结构
        pinyinTextPT.setPinyinText(pairList);
        //普通带间距
        pinyinTextPT1.setPinyinText(pairList);
        pinyinTextPT1.setTextSize(50);//汉字的大小
        pinyinTextPT1.setVerticalSpacing(60);//每行字之间的距离(字多换行间)
        pinyinTextPT1.setHorizontalSpacing(100);//横向每个字之间的距离
        //普通的拼音汉字结构带字体,字体限制只对拼音有效
        pinyinText_ttf.setPinyinText(pairList,"mengmengda.ttf");//设置上下结构的拼音字体
        pinyinText_ttf.setPinyinTextColor(0x889933FA);
        pinyinText_ttf.setPinyinTextSize(50);//拼音的大小
        pinyinText_ttf.setLineSpacing(20);//拼音和汉字之间的距离(默认是行距的一半)
```

##### Email: SundarFung@gmail.com

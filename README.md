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

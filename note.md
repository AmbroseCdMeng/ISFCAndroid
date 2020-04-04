# Android 开发笔记

1. 全屏 & 取消默认标题栏

	```xml
	<!-- Base application theme. -->
    <style name="Theme.NoActionFullScreenTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="windowNoTitle">true</item>
        <item name="windowActionBar">false</item>
        <item name="android:windowFullscreen">true</item>
        <item name="android:windowContentOverlay">@null</item>
    </style>
	```
	
2. px/dp/sp 单位的区别
	- px:
		像素单位。依赖于屏幕分辨率。
	- dp:
		与像素密度密切相关。对角线每英寸的像素点个数。依赖于屏幕大小和屏幕分辨率。
		屏幕大小相同，分辨率越高，dp越高
		分辨率相同，屏幕大小越小，dp越高
	- sp:
		与缩放无关的抽象像素。会根据用户字体大小缩放
		
	> 使用：
	> 
	> px 不建议使用；dp 适合长宽单位；sp 适合字体大小单位
	
3. 自定义标题栏

	- 取消默认标题栏
	- 在 layout 下自定义标题栏 title_bar.xml
	- 在页面中引用自定义的布局
		```xml
		<include layout="@layout/title_bar" />
		```
		
	> 以上只是一种方法，自定义标题栏的方法有很多，其他待研究
	

4. 自定义 CheckBox 样式

	- 定义 checkbox 图标 (drawable/checkbox_style.xml)
		```xml
		<?xml version="1.0" encoding="utf-8"?>
		<selector xmlns:android="http://schemas.android.com/apk/res/android">
			<item android:drawable="@mipmap/spwd_1" android:state_checked="true"/>
			<item android:drawable="@mipmap/spwd_2" android:state_checked="false"/>
			<item android:drawable="@mipmap/spwd_2"/>
		</selector>
		```
	- 定义 checkbox 主题 (values/styles.xml)
		```xml
		<style name="CheckBoxForRememberPasswordTheme" parent="@android:style/Widget.CompoundButton.CheckBox">
			<item name="android:button">@drawable/checkbox_style</item>
		</style>
		```
	- 引用自定义主题 (layout - login.xml)
		```xml
		<CheckBox
			style="@style/CheckBoxForRememberPasswordTheme"/>
		```
		
5. 自定义 Button 样式
	
	- 定义 button 样式 (drawable/button_style.xml)
		```xml
		<?xml version="1.0" encoding="utf-8"?>
		<shape xmlns:android="http://schemas.android.com/apk/res/android"
			android:shape="rectangle">
			<solid android:color="@color/colorLakeGreen" />
			<corners android:radius="6.67dp" />
			<padding
				android:bottom="10dp"
				android:left="10dp"
				android:right="10dp"
				android:top="10dp" />
		</shape>
		```
	- 引用 button 样式 (layout/login.xml)
		```xml
		<Button
			android:background="@drawable/button_style" />
		```
	
6. 自定义 Button 样式(圆角 + 阴影 + 图片 + 文字)

	- 定义 button 圆角 + 阴影样式(drawable/menu_button_style)
		```xml
		<?xml version="1.0" encoding="utf-8"?>
		<shape android:shape="rectangle"
			xmlns:android="http://schemas.android.com/apk/res/android">
			<solid android:color="#ffffffff" />
			<item android:name="android:shadowDx">0</item>
			<item android:name="android:shadowDy">8</item>
			<item android:name="android:shadowColor">#3d969696</item>
			<corners android:radius="10dp" />
		</shape>
		```
	- 使用 Button 组件的 background 属性引用自定义样式
		```xml
		<Button
			android:background="@drawable/menu_button_style" />
		```
	- 使用 Button 组件的 drawableTop/drawableBottom/drawableLeft/drawableRight 引用图片，text 属性设置文字
		```xml
		<Button
			android:background="@drawable/menu_button_style"
			android:paddingTop="12dp" />
		```
	- 使用 Button 组件的 paddingTop/paddingBottom/paddingLeft/paddingRight 和 drawablePadding 属性调整图片和文字的位置
		```xml
		<Button
			android:layout_marginStart="26dp"
			android:layout_marginTop="40dp"
			android:background="@drawable/menu_button_style"
			android:drawableTop="@mipmap/asn"
			android:drawablePadding="-10dp"
			android:paddingTop="12dp" />
		```
		
7. 动态修改自定义标题栏的内容
	
	疑问点：
		- setText 方法无效果
	
	调试：	
		- 断点调试，setText 方法生效。但页面中 title_bar 的文字未刷新
		- 设置 title_bar 为启动页，setText 方法生效。
		
	初步判断：
		- 线程原因，setText 修改值成功后，title_bar 未刷新
		- 缓存原因，setText 修改值成功后，title_bar 未刷新
		
	测试结果(2020年4月4日12:27:47)：
		- 并非以上两种原因。
		- 问题原因：
			> 因 TitleBarActivity 界面的组件初始化写在 onCreate 方法中；
			> HomeActivity 继承了 TitleBarActivity 类，在 onCreate 方法中先执行了 super.onCreate 方法，此时 TitleBarActivity 的组件已经初始化完毕，然后 HomeActivity 才 setContentView(R.layout.home)；
			> 此时 TitleBarActivity 中组件的 setText 虽然成功修改了 TitleBarActivity 界面中的值，但 HomeActivity 界面并未刷新。
			
		- 解决方法：
			> 在 HomeActivity setContentView(R.layout.home) 方法之后再对 TitleBarActivity 界面的组件初始化
		```xml
		public class HomeActivity extends TitleBarActivity {
			@Override
			protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.home);
				initTitleView();
			}
		}
		
		public class TitleBarActivity extends AppCompatActivity {
			private TextView mleft;
			private TextView mtitle;
			private TextView mright;
			
			protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.title_bar);
				//initTitleView();
			}
			protected void initTitleView() {
				mleft = findViewById(R.id.tv_left);
				mtitle = findViewById(R.id.tv_title);
				mright = findViewById(R.id.tv_right);
			}
		}
		```
	
	
	
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
	
8. 页面崩溃程序退出

	- 检查页面 context 属性的值是否与 Activity 名称对应
		```xml
		tools:context=".InStorageWorkOrderActivity"
		```
	
	- 检查 AndroidMainifest 文件是否添加 activity 属性
		```xml
		<activity android:name=".InStorageWorkOrderActivity"/>
		```
		
	- 检查页面代码是否报错
	
9. ListView 的样式设定
	
	- 自定义 ListView 的样式文件
	- 自定义 ListView 的布局文件，并运用其样式
	- 动态绑定数据
	- 自定义分割线样式文件
	- 调整 ListView 的间距，并引用分割线样式
	
10. TextView 和 EditView 中 drawableRight 图标的点击事件

11. 自动获取焦点问题

	- 在外层布局加入如下代码，防止界面内 TextView EditView 等元素自动获取焦点产生黑色阴影
	
		```xml
		android:focusable="true"
		android:focusableInTouchMode="true"
		```
		
12. TextView 对齐方式
	- gravity
		内容对齐方式
			* left	左对齐
			* center  垂直水平居中
			* center_horizontal	水平居中
			* center_vertical 	垂直居中
			* center_vertical|left	垂直居中且左对齐
	
	- layout_gravity
		组件相对于父组件的对齐方式
	
13. EditView 的 textType 类型转换（密码的明文密文切换）
	```
	if (isChecked)
            mpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
	else
		mpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
	mpassword.setSelection(mpassword.getText().length());//光标位置默认置尾
	```
	
14. ListView 的嵌套

	参考： www.jianshu.com/p/b805091bd4e5
	
	常见的三种解决方案：
		
	- 手动遍历子 View， 设置 ListView 的高度。
		缺点：在 Item 的根布局是 RelativeLayout 时无法测量高度，且在 Android 17 及以下时， RelativeLayou.measure(w, h) 会出现空指针，只能外层再加一层 Layout
	
	- 重写 ListView 的 onMeasure() 方法
		```java
		protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
			int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
			super.onMeasure(widthMeasureSpec, expandSpec);
		}
		```		
		
		优点：代码量少，逻辑简单
		缺点：Adapter 的 getView() 会被重复调用多次，如果嵌套两次，调用次数成倍增加，性能伤害大；在每个 Item 的高度不一样时，ListView 的高度计算不准确
		
	- LinearLayout 模拟 ListView 
		
		优点：复用性强，性能高
		缺点：代码量大，逻辑复杂
		
	- RecyclerView 嵌套（推荐）
		
15. Cleartext HTTP traffic to 10.161.139.45 not permitted
	
	```java
	/**
	 * Google 针对 Android P 应用程序要求默认使用加密链接，否则将在 httpURL Connection 时出现以下异常：
	 *
	 *  Cleartext HTTP traffic to 10.161.139.45 not permitted
	 *
	 * 解决方法：
	 *  1. 降低 targetSdkVersion 版本到 27 以下
	 *  2. APP 改用 HTTPS 加密请求
	 *  3. 更改网络配置
	 */
	```
	
16. socket failed: EACCES (Permission denied)

	```java
	<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	```
	
17. android.os.NetworkOnMainThreadException

	- 安卓 `4.0` 及以上版本禁止在主线程中访问 HTTP 请求。
	
		理由是为了防止应用 ANR（Application Not Response）异常导致的界面假死情况发生
		
18. Android AVD 无法访问局域网 

	- 配置 DNS 与主机相同
		`C:\Users\Administrator>emulator -avd 4_WVGA_Nexus_S_API_28 -dns-server 10.151.7.160,10.151.7.196`
		
		
19. Only the original thread that created a view hierarchy can touch its views

	- Android系统中的视图组件并不是线程安全的，如果要更新视图，必须在主线程中更新，不可以在子线程中执行更新的操作
	
	- 此处需要采用 Handler对象消息分发机制，在子线程中完成获取数据，然后通知主线程更新视图
	
20. ConstraintLayout 布局的 match_parent 值会铺满
	
	- 如果想实现宽度match_parent，就设置宽度为0dp，再设置左约束和右约束；如果想实现高度match_parent，就设置高度为0dp，再设置上约束和下约束
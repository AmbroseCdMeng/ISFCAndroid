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
	

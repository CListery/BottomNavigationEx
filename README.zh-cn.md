# BottomNavigationEx

一个增强BottomNavigationView的android库。（扩展自https://github.com/ittianyu/BottomNavigationViewEx）


![MIT License](https://img.shields.io/github/license/mashape/apistatus.svg) ![api 14+](https://img.shields.io/badge/API-14%2B-green.svg)

## API ##

|Method|Description
|---|---|
|realView|对应 material 库版本的 BottomNavigationView 实例（如果你需要直接调用原生的API）
|setIconVisibility|设置图片可见性。
|setTextVisibility|设置文本可见性。
|enableAnimation|开启或关闭点击动画(文字放大效果和图片移动效果)。 默认为 true.
|enableLabelVisibility|设置导航项的标签可见性。
|enableItemHorizontalTranslation|设置当组合项宽度填满屏幕时菜单项是否在选择时水平平移。
|getCurrentIndex|获取当前选中项的index
|menuItemPositionAt|获取menuItem所在position
|setCurrentItem|设置当前选中的index
|getMenuListener|获取监听器
|setMenuListener|设置监听器
|setMenuDoubleClickListener|设置双击监听器
|setInnerListener|设置内部监听器（内部函数，不能直接调用）
|getBNMenuView|获取 BottomNavigationMenuView 对象
|clearIconTintColor|清除设置的图标 Tint 颜色
|getAllBNItemView|获取所有 BottomNavigationItemView
|getBNItemView|获取指定 position 对应的 BottomNavigationItemView
|getIconAt|获取指定 position 对应的 Icon's view
|getSmallLabelAt|获取指定 position 对应的 SmallLabel's view
|getLargeLabelAt|获取指定 position 对应的 LargeLabel's view
|getBNItemViewCount|获取 BottomNavigationItemView count
|setSmallTextSize|设置 SmallText 的字体大小
|setLargeTextSize|设置 LargeText 的字体大小
|setTextSize|设置 SmallText、LargeText 的字体大小
|setIconSizeAt|设置指定 position 对应的 Icon 宽高
|setIconSize|设置 Icon 宽高
|setBNMenuViewHeight|设置 BottomNavigationMenuView 高度
|getBNMenuViewHeight|获取 BottomNavigationMenuView 高度
|setTypeface|设置字体和样式
|setupWithViewPager|绑定到 ViewPager
|setupWithViewPager2|绑定到 ViewPager2
|enableBNItemViewLabelVisibility|设置 BottomNavigationItemView 的标签可见性
|setBNItemViewBackgroundRes|设置指定 position 的 BottomNavigationItemView 的 background 资源
|setIconTintList|设置 Icon 的颜色
|setTextTintList|设置字体的颜色
|setIconsMarginTop|设置 Icon 的顶部边距
|setIconMarginTop|设置设置指定 position 的 Icon 的顶部边距
|setEmptyMenuIds|设置 empty menu ids，一般用于添加特殊按钮
|getMenu|获取 menu
|getMenuItems|获取 MenuItems
|setItemOnTouchListener|设置Menu的点击事件（内部函数，无特殊需求请勿调用）

## 示例 ##

**样式**

![](/read_me_images/normal.gif)

![](/read_me_images/no_animation.gif)

![](/read_me_images/no_shifting_mode.gif)

![](/read_me_images/no_item_shifting_mode.gif)

![](/read_me_images/no_text.gif)

![](/read_me_images/no_icon.gif)

![](/read_me_images/no_animation_shifting_mode.gif)

![](/read_me_images/no_animation_item_shifting_mode.gif)

![](/read_me_images/no_animation_shifting_mode_item_shifting_mode.gif)

![](/read_me_images/no_shifting_mode_item_shifting_mode_text.gif)

![](/read_me_images/no_animation_shifting_mode_item_shifting_mode_text.gif)

![](/read_me_images/no_shifting_mode_item_shifting_mode_and_icon.gif)

![](/read_me_images/no_item_shifting_mode_icon.gif)

![](/read_me_images/no_animation_shifting_mode_item_shifting_mode_icon.gif)

**注意：这个 style 在安卓 4.x 上有 bug**

![](/read_me_images/with_padding.jpg)

![](/read_me_images/center_icon_only.jpg)

![](/read_me_images/smaller_text.jpg)

![](/read_me_images/bigger_icon.jpg)

![](/read_me_images/custom_typeface.jpg)

![](/read_me_images/icon_selector_1.jpg) ![](/read_me_images/icon_selector_2.jpg)

![](/read_me_images/icon_margin_top.jpg)

![](/read_me_images/unchecked_first_time.jpg)


**和 ViewPager 或 ViewPage2 一起使用**

![](/read_me_images/with_view_pager.gif)

**带数字的小红点**

![](/read_me_images/view_badger.gif)

**中间悬浮按钮**

![](/read_me_images/center_fab.jpg)

## 引入项目 ##

### Sdk 版本 ###
`compileSdkVersion` >= 25

### 导入本库(选择其中一种) ###

#### Gradle例子: ####

步骤 1. 在工程根目录的 `build.gradle` 最后添加如下代码:
```groovy
allprojects {
	repositories {
		...
		google()
        mavenCentral()
	}
}
```

步骤 2. 添加依赖

1.使用 `material`1.4.0 以下版本
```groovy
implementation 'io.github.clistery:bottomnavigationex-ex:1.1.1'
implementation 'io.github.clistery:bottomnavigationex-130:1.1.1'
```
1.使用 `material`1.4.0 及以上版
```groovy
implementation 'io.github.clistery:bottomnavigationex-ex:1.1.1'
implementation 'io.github.clistery:bottomnavigationex-140:1.1.1'
```
3. AndroidX
   使用新版本，然后在 gradle.properties 中添加如下配置
```
android.useAndroidX=true
android.enableJetifier=true
```

### 手动导入: ###

在 `xml` 布局中添加自定义控件:
```xml
<com.yh.bottomnavigationex.BottomNavigationViewEx
    android:id="@+id/bnve"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_alignParentBottom="true"
    android:background="@color/colorPrimary"
    app:itemIconTint="@color/selector_item_color"
    app:itemTextColor="@color/selector_item_color"
    app:menu="@menu/menu_navigation_with_view_pager" />
```

在 `Activity` 中绑定控件:
```java
BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
```

#### 禁止所有动画效果 ####
```java
bnve.enableAnimation(false);
bnve.enableLabelVisibility(false);
bnve.enableItemHorizontalTranslation(false);
```

#### 自定义图标和文本大小 ####
```java
bnve.setIconSize(widthDp, heightDp);
bnve.setTextSize(sp);
```

#### 和 ViewPager 绑定 ####
```java
// set adapter
adapter = new VpAdapter(getSupportFragmentManager(), fragments);
bind.vp.setAdapter(adapter);

// binding with ViewPager
bind.bnve.setupWithViewPager(bind.vp);
```

#### 和 ViewPager2 绑定 ####
```java
// set adapter
adapter = new Vp2Adapter(getSupportFragmentManager());
bind.vp.setAdapter(adapter);

// binding with ViewPager
bind.bnve.setupWithViewPager2(bind.vp);
```

#### 添加带数字的小红点 ####
```java
BadgeDrawable bd = bind.bnve.getRealView().getOrCreateBadge(R.id.i_friends);
bd.setNumber(1);
bd.setHorizontalOffset(12);
bd.setVerticalOffset(2);
```

#### 其他 BottomNavigationViewEx 的用法 ####
请参考demo。

#### 其他 BottomNavigationView 的用法 ####
其他用法和官方 `BottomNavigationView` 一样。
详情[点击这里](https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView)

## License ##

	MIT License
	
	Copyright (c) 2021 clistery
	
	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:
	
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.
	
	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.

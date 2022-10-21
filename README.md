**Other Languages:**

[![Chinese](https://img.shields.io/badge/Language-Chinese-blueviolet?style=for-the-badge)](README.zh-cn.md)

# BottomNavigationEx

An android lib for enhancing BottomNavigationView.(Extended from https://github.com/ittianyu/BottomNavigationViewEx)

![MIT License](https://img.shields.io/github/license/mashape/apistatus.svg) ![api 14+](https://img.shields.io/badge/API-14%2B-green.svg)

## API ##

|Method|Description
|---|---|
|realView|The BottomNavigationView instance corresponding to the material library version (if you need to call the native API directly)
|setIconVisibility|Set image visibility.
|setTextVisibility|Set text visibility.
|enableAnimation|Enable or disable click animation (text enlargement effect and image movement effect). Defaults to true.
|enableLabelVisibility|Sets the tab visibility of the navigation item.
|enableItemHorizontalTranslation|Sets whether the menu item pans horizontally on selection when the combined item width fills the screen.
|getCurrentIndex|Get the index of the currently selected item
|menuItemPositionAt|Get the position where the menuItem is located
|setCurrentItem|Set the currently selected index
|getMenuListener|get listener
|setMenuListener|set listener
|setMenuDoubleClickListener|Set up double click listener
|setInnerListener|Set internal listener (internal function, cannot be called directly)
|getBNMenuView|Get the BottomNavigationMenuView object
|clearIconTintColor|Clear Set Icon Tint Color
|getAllBNItemView|Get all BottomNavigationItemView
|getBNItemView|Get the BottomNavigationItemView corresponding to the specified position
|getIconAt|Get the Icon's view corresponding to the specified position
|getSmallLabelAt|Get the SmallLabel's view corresponding to the specified position
|getLargeLabelAt|Get the LargeLabel's view corresponding to the specified position
|getBNItemViewCount|Get BottomNavigationItemView count
|setSmallTextSize|Set the font size of SmallText
|setLargeTextSize|Set the font size of LargeText
|setTextSize|Set the font size of SmallText, LargeText
|setIconSizeAt|Set the width and height of the Icon corresponding to the specified position
|setIconSize|Set Icon width and height
|setBNMenuViewHeight|Set BottomNavigationMenuView height
|getBNMenuViewHeight|Get BottomNavigationMenuView height
|setTypeface|Set fonts and styles
|setupWithViewPager|Bind to ViewPager
|setupWithViewPager2|Bind to ViewPager2
|enableBNItemViewLabelVisibility|Set the label visibility of BottomNavigationItemView
|setBNItemViewBackgroundRes|Set the background resource of BottomNavigationItemView at the specified position
|setIconTintList|Set the color of the Icon
|setTextTintList|Set font color
|setIconsMarginTop|Set the top margin of the Icon
|setIconMarginTop|Sets the top margin of the Icon with the specified position
|setEmptyMenuIds|Set empty menu ids, generally used to add special buttons
|getMenu|get menu
|getMenuItems|Get MenuItems
|setItemOnTouchListener|Set the click event of Menu (internal function, please do not call without special requirements)

## Example ##

**Style**

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

**Attention: Something wrong on Android 4.x**

![](/read_me_images/with_padding.jpg)

![](/read_me_images/center_icon_only.jpg)

![](/read_me_images/smaller_text.jpg)

![](/read_me_images/bigger_icon.jpg)

![](/read_me_images/custom_typeface.jpg)

![](/read_me_images/icon_selector_1.jpg) ![](/read_me_images/icon_selector_2.jpg)

![](/read_me_images/icon_margin_top.jpg)

![](/read_me_images/unchecked_first_time.jpg)


**With ViewPager or ViewPage2**

![](/read_me_images/with_view_pager.gif)

**Add ViewBadger**

![](/read_me_images/view_badger.gif)

**Center Floating Action Button**

![](/read_me_images/center_fab.jpg)


## Adding to project ##

### Sdk Version ###
`compileSdkVersion` >= 25

### Importing to project(choose one) ###

#### Example for Gradle: ####

Step 1. Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
  repositories {
    ...
    google()
    mavenCentral()
  }
}
```

Step 2. Add the dependency
1. Use material: versions below 1.4.0
```groovy
implementation 'io.github.clistery:bottomnavigationex-ex:1.1.1'
implementation 'io.github.clistery:bottomnavigationex-130:1.1.1'
```
2. Use material: 1.4.0 and above
```groovy
implementation 'io.github.clistery:bottomnavigationex-ex:1.1.1'
implementation 'io.github.clistery:bottomnavigationex-140:1.1.1'
```
3. AndroidX
   use new version，and add config into gradle.properties
```
android.useAndroidX=true
android.enableJetifier=true
```

## Getting started ##

Adding a custom widget in `xml` :
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

Binding view in `Activity`:
```java
BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
```

#### Disable all animations ####
```java
bnve.enableAnimation(false);
bnve.enableLabelVisibility(false);
bnve.enableItemHorizontalTranslation(false);
```

#### Custom text and icon size ####
```java
bnve.setIconSize(widthDp, heightDp);
bnve.setTextSize(sp);
```

#### Binding with ViewPager ####
```java
// set adapter
adapter = new VpAdapter(getSupportFragmentManager(), fragments);
bind.vp.setAdapter(adapter);

// binding with ViewPager
bind.bnve.setupWithViewPager(bind.vp);
```

#### Binding with ViewPager2 ####
```java
// set adapter
adapter = new Vp2Adapter(getSupportFragmentManager());
bind.vp.setAdapter(adapter);

// binding with ViewPager
bind.bnve.setupWithViewPager2(bind.vp);
```

#### Add badge view ####
```java
BadgeDrawable bd = bind.bnve.getRealView().getOrCreateBadge(R.id.i_friends);
bd.setNumber(1);
bd.setHorizontalOffset(12);
bd.setVerticalOffset(2);
```

#### Other usage in BottomNavigationViewEx ####
You can see the demo.

#### Usage in BottomNavigationView ####
Other usage is the same as official `BottomNavigationView`.
You can [click here](https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView) for detail.

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

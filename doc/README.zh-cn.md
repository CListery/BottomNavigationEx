# BottomNavigationEx

![MIT License](https://img.shields.io/github/license/mashape/apistatus.svg) ![api 14+](https://img.shields.io/badge/API-14%2B-green.svg)

> 一个增强BottomNavigationView的android库。（[~~BottomNavigationViewEx(已废弃)~~](https://github.com/ittianyu/BottomNavigationViewEx)的延续版本）

## [APIs](./doc/API-zh-cn.md)

## [示例](./doc/example-zh-cn.md)

## 依赖

### Gradle

1. 版本设置

    - `material`1.7.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-ex:2.0.0'
        implementation 'io.github.clistery:bottomnavigationex-17x:2.0.0'
        ```

    - `material`1.6.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-ex:2.0.0'
        implementation 'io.github.clistery:bottomnavigationex-16x:2.0.0'
        ```

    - `material`1.5.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-ex:2.0.0'
        implementation 'io.github.clistery:bottomnavigationex-15x:2.0.0'
        ```

    - `material`1.4.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-ex:2.0.0'
        implementation 'io.github.clistery:bottomnavigationex-14x:2.0.0'
        ```

    - `material`1.3.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-ex:2.0.0'
        implementation 'io.github.clistery:bottomnavigationex-13x:2.0.0'
        ```

2. 在 `gradle.properties` 中添加如下配置

   ```properties
   android.useAndroidX=true
   android.enableJetifier=true
   ```

### 使用

- 在 `xml` 布局中添加自定义控件

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

- 在 `Activity` 中绑定控件

    ```java
    BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
    ```

- 禁止所有动画效果

    ```java
    bnve.enableAnimation(false);
    bnve.enableLabelVisibility(false);
    bnve.enableItemHorizontalTranslation(false);
    ```

- 自定义图标和文本大小

    ```java
    bnve.setIconSize(widthDp, heightDp);
    bnve.setTextSize(sp);
    ```

- 和 ViewPager 绑定

    ```java
    // set adapter
    adapter = new VpAdapter(getSupportFragmentManager(), fragments);
    bind.vp.setAdapter(adapter);

    // binding with ViewPager
    bind.bnve.setupWithViewPager(bind.vp);
    ```

- 和 ViewPager2 绑定

    ```java
    // set adapter
    adapter = new Vp2Adapter(getSupportFragmentManager());
    bind.vp.setAdapter(adapter);

    // binding with ViewPager
    bind.bnve.setupWithViewPager2(bind.vp);
    ```

- 添加带数字的小红点

    ```java
    BadgeDrawable bd = bind.bnve.getRealView().getOrCreateBadge(R.id.i_friends);
    bd.setNumber(1);
    bd.setHorizontalOffset(12);
    bd.setVerticalOffset(2);
    ```

- 通过 getRealView 获取到原始 BottomNavigationView 的用法请参考[官方文档](https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView)

- 其他更多的用法，请参考demo。

## License

```license
MIT License

Copyright (c) 2022 clistery

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
```

# BottomNavigationEx

[中文文档](./doc/README.zh-cn.md)

![MIT License](https://img.shields.io/github/license/mashape/apistatus.svg) ![api 14+](https://img.shields.io/badge/API-14%2B-green.svg)

> An android library that enhances BottomNavigationView. (Continuation of [~~BottomNavigationViewEx(obsolete)~~](https://github.com/ittianyu/BottomNavigationViewEx))

## [APIs](./doc/API.md)

## [Example](./doc/example.md)

## Dependencies

### Gradle

1. Ex library

    ```groovy
    implementation 'io.github.clistery:bottomnavigationex-ex:x.x.x'
    ```

2. Version base on `material`

    - `material`1.9.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-19x:x.x.x'
        ```

    - `material`1.8.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-18x:x.x.x'
        ```

    - `material`1.7.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-17x:x.x.x'
        ```

    - `material`1.6.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-16x:x.x.x'
        ```

    - `material`1.5.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-15x:x.x.x'
        ```

    - `material`1.4.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-14x:x.x.x'
        ```

    - `material`1.3.x

        ```groovy
        implementation 'io.github.clistery:bottomnavigationex-13x:x.x.x'
        ```

3. Add the following configuration in `gradle.properties`

   ```properties
   android.useAndroidX=true
   android.enableJetifier=true
   ```

### USE

- Adding a custom widget in `xml`

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

- Binding view in `Activity`

    ```java
    BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);
    ```

- Disable all animations

    ```java
    bnve.enableAnimation(false);
    bnve.enableLabelVisibility(false);
    bnve.enableItemHorizontalTranslation(false);
    ```

- Custom text and icon size

    ```java
    bnve.setIconSize(widthDp, heightDp);
    bnve.setTextSize(sp);
    ```

- Binding with ViewPager

    ```java
    // set adapter
    adapter = new VpAdapter(getSupportFragmentManager(), fragments);
    bind.vp.setAdapter(adapter);

    // binding with ViewPager
    bind.bnve.setupWithViewPager(bind.vp);
    ```

- Binding with ViewPager2

    ```java
    // set adapter
    adapter = new Vp2Adapter(getSupportFragmentManager());
    bind.vp.setAdapter(adapter);

    // binding with ViewPager
    bind.bnve.setupWithViewPager2(bind.vp);
    ```

- Add badge view

    ```java
    BadgeDrawable bd = bind.bnve.getRealView().getOrCreateBadge(R.id.i_friends);
    bd.setNumber(1);
    bd.setHorizontalOffset(12);
    bd.setVerticalOffset(2);
    ```

- For the usage of the original BottomNavigationView obtained through getRealView, please refer to [Official Documentation](https://developer.android.com/reference/com/google/android/material/bottomnavigation/BottomNavigationView)

- For more usage, please refer to demo.

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

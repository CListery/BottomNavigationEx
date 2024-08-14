# APIs

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
|configDynamic|动态配置 menu item
|getMenuMaxItemCount|menu item 最大个数 (V13x: 至少为5)

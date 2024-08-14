# APIs

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
|configDynamic|Dynamic config menu item
|getMenuMaxItemCount|Get menu max item size (V13x has a minimum of 5)

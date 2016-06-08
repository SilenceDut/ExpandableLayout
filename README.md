# ExpandableLayout

A expandable Layout to save space and  reduce jump between Activity and Fragment
[![](https://jitpack.io/v/SilenceDut/ExpandableLayout.svg)](https://jitpack.io/#SilenceDut/ExpandableLayout)

**_SimpleUse_**

![intro](media/simple_use.gif)
####[sample.apk](https://github.com/SilenceDut/DayNightToggleButton/blob/master/apk/expandable.apk?raw=true) 
(It runs smoothly, but gif is not appear well)
Adding to your project
----------------------
This library is available through JitPack.

Step 1. Add the JitPack repository to your build file

```groovy
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
    }
}
```

Step 2. Add the dependency

```groovy
compile 'com.github.SilenceDut:ExpandableLayout:{latest-version}'
```

Basic Usage
-----------
**Supported Attributes**

```xml
<declare-styleable name="ExpandableLayout">
        <attr name="expDuration" format="integer|reference"/> //expand duration
        <attr name="expWithParentScroll" format="boolean"/>  // the parent view should scroll automatically if the view expand out of device screen 
        <attr name="expExpandScrollTogether" format="boolean"/> // the parent view should scroll together with view expanding 
</declare-styleable>
```

**ExpandableLayout inherited from LinearLayout,and the default setOrientation is **VERTICAL****

```xml
<com.silencedut.expandablelayout.ExpandableLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:expWithParentScroll="true"
    app:expDuration = "300"
    app:expExpandScrollTogether = "false"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <layout1
    ...
    />
    
    <layout2
    ...
    />

</com.silencedut.expandablelayout.ExpandableLayout>
```

**Use in RecyclerView**

_expWithParentScroll = "true"_
app:expExpandScrollTogether = "false"

![intro](media/recyclerview_withParentScroll_together.gif)

**Use in listView**

_expWithParentScroll = "false"_

![intro](media/listview_withoutParentScroll.gif)

License
-------

    Copyright 2015-2016 SilenceDut

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
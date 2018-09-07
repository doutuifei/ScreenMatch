# 屏幕适配心得

> 先参考[Android dp方式的屏幕适配-原理](https://blog.csdn.net/fesdgasdgasdg/article/details/82054971)，不然下面的看不懂！！！<br/>
安装```ScreenMatch```插件！！！

## 屏幕参数获取

* dpi
```
DisplayMetrics dm = getResources().getDisplayMetrics();
float density = dm.density * 160;
```

* 屏幕可用高宽(去除虚拟导航栏高度)
```
DisplayMetrics dm = getResources().getDisplayMetrics();
int heightPixels = dm.heightPixels;
int widthPixels = dm.widthPixels;
```

* 屏幕实际高宽（加上虚拟导航栏高度）
```
WindowManager windowManager =
        (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
final Display display = windowManager.getDefaultDisplay();
Point outPoint = new Point();
if (Build.VERSION.SDK_INT >= 19) {
    // 可能有虚拟按键的情况
    display.getRealSize(outPoint);
} else {
    // 不可能有虚拟按键
    display.getSize(outPoint);
}
int mRealSizeWidth = outPoint.x;//手机屏幕真实宽度
int mRealSizeHeight = outPoint.y;//手机屏幕真实高度
```

## 基准图计算 base_dp

比如设计图以1920x1200，dpi为240，density为1.5设计的。

```800=1200/1.5(sw=width/density)```

width:为屏幕实际的最短边（可用距离+虚拟导航栏）
代码如下
```
DisplayMetrics dm = getResources().getDisplayMetrics();
float density = dm.density;

WindowManager windowManager =
        (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
final Display display = windowManager.getDefaultDisplay();
Point outPoint = new Point();
if (Build.VERSION.SDK_INT >= 19) {
    // 可能有虚拟按键的情况
    display.getRealSize(outPoint);
} else {
    // 不可能有虚拟按键
    display.getSize(outPoint);
}
int mRealSizeWidth;//手机屏幕真实宽度
int mRealSizeHeight;//手机屏幕真实高度
mRealSizeHeight = outPoint.y;
mRealSizeWidth = outPoint.x;

int swWidth = (int) (Math.floor(Math.min((mRealSizeHeight / density), (mRealSizeWidth / density))));//sw
```

## values适配
> 只会适配```dimens.xml```下的距离，不想做适配的可以放到```dimen.xml```下

1. ```screenMatch.properties```的```match_dp```下添加需要适配屏幕的```sw```值

2. 右击->```ScreenMath```选择对应Module即可

## drawable适配

命名格式
```drawable-hdpi-1223x800```、```drawable-hdpi-1280x736```

```1223x800```为可用高度和宽度，代码获取如下
```
DisplayMetrics dm = getResources().getDisplayMetrics();
int heightPixels = dm.heightPixels;
int widthPixels = dm.widthPixels;
String dimens = heightPixels + "x" + widthPixels;
```

## python获取屏幕参数脚本[windows适用][python3.6]
> 必须配置```adb```命令，且连接设备。```adb devices```能获取设备信息才可以使用！

```
python dimens.py
```
![python shell](https://github.com/mzyq/ScreenMath/blob/bb4cccb4077ef8a536ed6e7de34466cb43ff14c2/img/shell.png)

## 适配目录结构
![img](https://github.com/mzyq/ScreenMath/blob/7faacacebcd3942049ec995183405b46570ad2cb/img/dir.png)
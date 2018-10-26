# 屏幕适配（SW适配）

## 一 value适配

### 1 SW说明

#### 1.1 说明

[Google解释：可用屏幕区域的最小尺寸](https://developer.android.google.cn/guide/practices/screens_support)

![sw](http://p7rrs468p.bkt.clouddn.com/sw.png)

> 可用屏幕区域包括导航栏的高度/宽度。（dm.widthPixels():获取的高度不包括导航栏）

#### 1.2 计算方式

比如联想的TB3-X70F：分辨率：1920x1200，density：1.5，dpi：240-hdpi

计算公式：sw=1200/1.5=800

> 市场主流手机设备sw：360

### 2 Plugin ScreenMatch

[作者说明](https://blog.csdn.net/fesdgasdgasdg/article/details/82054971)

#### 2.1 使用

首选项目必须使用```dimens.xml```，任意文件上右击->```ScreenMatch```->选择```module```即可

1. 项目必须有```dimens.xml```文件。
2. ```ScreenMatch```只会适配```dimens.xml```目录下的尺寸，不想适配的尺寸可以放到别的文件下。
3. 尺寸向下兼容，比如：sw601dp如果没有适配，会使用sw600dp。

#### 2.2 配置文件```screenMatch.properties```

> ```screenMatch.properties```文件在```settings.gradle```同级目录下

1. ```base_dp```:适配的基准，以UI设计图对应的设备为准。定制平板设备为：800dp。
2. ```match_dp```:需要适配的尺寸。
3. ```ignore_dp```:不需要适配的尺寸。

## 二 drawable适配

> 有些设备dpi较小，物理尺寸较大，比如联想TB3-X70F,DPI为hdpi，正常情况下使```drawab-hdpi```目录下的icon，但是物理尺寸过大，icon显示较小，就需要使用```drawab-xdpi```的icon，这时肯定不能将```drawab-xdpi```的icon放到```drawab-hdpi```目录下。

###  1 新建drawable目录

比如联想TB3-X70F，可以新建一个```drawable-hdpi-1920x1128```。

## 三 工具

###  1 自动生成dimens

####  1.1 不带参数

```java
java -jar Dimens.jar
```

> ```
> 默认参数：DP_FROM:-2560.0f,DP_TO:2560.0f,DP_STEP_SIZE=0.5f;
>          SP_FROM = 9f,SP_TO = 30f,SP_STEP_SIZE = 1f
> ```

#### 1.2 带参数

```java
java -jar Dimens.jar -100.0 100 0.5 9.0 30.0 1.0
```

> ```
> 1:DP_FROM,2:DP_TO,3:DP_STEP_SIZE;
> 4:SP_FROM,5:SP_TO,6:SP_STEP_SIZE(不需要修改的参数可以传非数字：例如"-")
> ```

### 2 获取屏幕参数apk

![apk](http://p7rrs468p.bkt.clouddn.com/apk.png)

### 3 获取屏幕参数，生成drawable

> 必须配置```adb```命令，且连接设备。```adb devices```能获取设备信息才可以使用！

```
python pixel.py
```
![python shell](http://p7rrs468p.bkt.clouddn.com/pixel_py.png)

## 四 适配完成目录结构

![img](http://p7rrs468p.bkt.clouddn.com/dirs.png)
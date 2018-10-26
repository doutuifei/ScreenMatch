# -*-coding:utf-8-*-
import os
import re
import math


def execCmd(cmd):
    r = os.popen(cmd)
    text = r.read()
    r.close()
    return text


def calDpi(densityDpi):
    if (densityDpi <= 120):
        return 'ldpi'
    elif (densityDpi > 120 and densityDpi <= 160):
        return 'mdpi'
    elif (densityDpi > 160 and densityDpi <= 240):
        return 'hdpi'
    elif (densityDpi > 240 and densityDpi <= 320):
        return 'xdpi'
    elif (densityDpi > 320 and densityDpi <= 480):
        return 'xxdpi'
    elif (densityDpi > 480 and densityDpi <= 640):
        return 'xxxdpi'


def creatDrawable(dpi, piexl):
    drawable_name = 'drawable-' + dpi + '-' + piexl
    if drawable_name not in os.listdir():
        os.makedirs(drawable_name)
        print('创建文件夹：' + drawable_name)


if __name__ == '__main__':
    display = 'adb.exe shell dumpsys window displays'
    result = execCmd(display).strip()
    if (not result == None and len(result) > 0):
        real_dimensions = re.findall('cur=(\\d+x\\d+)', result)[0]
        available_dimensions = re.findall('app=(\\d+x\\d+)', result)[0]
        densityDpi = (int)(re.findall('(\\d+)dpi', result)[0])
        density = densityDpi / 160
        dpi = calDpi(densityDpi)
        creatDrawable(dpi, available_dimensions)  # 创建文件夹
        print('实际分辨率:' + real_dimensions)
        print('可用分辨率:' + available_dimensions)
        print('densityDpi:', densityDpi, 'dpi-' + dpi, sep='')
        print('density:', density, sep='')

        sw = min((int)(real_dimensions.split('x')[0]) / density, (int)(real_dimensions.split('x')[1]) / density)
        print('sw:', math.floor(sw), 'dp', sep='')

package com.muzi.screenmath;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;


/**
 * smallwidth适配
 *
 * @link {https://blog.csdn.net/fesdgasdgasdg/article/details/82054971}
 */
public class MainActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);
        text.setText(null);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        float density = dm.density;
        int densityDpi = dm.densityDpi;
        float scaledDensity = dm.scaledDensity;
        int heightPixels = dm.heightPixels;
        int widthPixels = dm.widthPixels;


        text.append("density:" + density + '\n');
        text.append("densityDpi:" + densityDpi + '\n');
        text.append("scaledDensity:" + scaledDensity + '\n');
        text.append("heightPixels:" + heightPixels + '\n');
        text.append("widthPixels:" + widthPixels + '\n');
        text.append("100dp:" + getResources().getDimension(R.dimen.dp_100) + "\n");
        text.append("100/" + density + "dp:" + (getResources().getDimension(R.dimen.dp_100) / density) + "dp\n");
        text.append("\n");

        /**
         * 屏幕真实尺寸
         */
        int mRealSizeWidth = getRealSize().x;//手机屏幕真实宽度
        int mRealSizeHeight = getRealSize().y;//手机屏幕真实高度
        text.append("RealSizeHeight:" + mRealSizeHeight + "\n");
        text.append("RealSizeWidth:" + mRealSizeWidth + "\n");
        text.append("\n");

        /**
         * value适配-swxxxdp
         */
        int swWidth = (int) (Math.floor(Math.min((mRealSizeHeight / density), (mRealSizeWidth / density))));
        text.append("sw:values-" + swWidth + "dp\n");


        /**
         * drawable分辨率适配
         */
        String drawable = null;
        if (densityDpi <= 120) {
            drawable = "drawable-ldpi-";
        } else if (densityDpi > 120 && densityDpi <= 160) {
            drawable = "drawable-mdpi-";
        } else if (densityDpi > 160 && density <= 240) {
            drawable = "drawable-hdpi-";
        } else if (densityDpi > 240 && density <= 320) {
            drawable = "drawable-xdpi-";
        } else if (densityDpi > 320 && density <= 480) {
            drawable = "drawable-xxdpi-";
        } else if (densityDpi > 480 && density <= 640) {
            drawable = "drawable-xxxdpi-";
        }
        text.append("pixel:" + drawable + widthPixels + "x" + heightPixels);

    }


    private Point getRealSize() {
        WindowManager windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
        final Display display = windowManager.getDefaultDisplay();
        Point outPoint = new Point();
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint);
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint);
        }
        return outPoint;
    }

}

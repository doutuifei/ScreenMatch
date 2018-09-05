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
        float widthDP = widthPixels / density;
        float heightDP = heightPixels / density;

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

        text.append("density:" + density + '\n');
        text.append("densityDpi:" + densityDpi + '\n');
        text.append("scaledDensity:" + scaledDensity + '\n');
        text.append("heightPixels:" + heightPixels + '\n');
        text.append("widthPixels:" + widthPixels + '\n');
        text.append("widthDP:" + widthDP + '\n');
        text.append("heightDP:" + heightDP + '\n');
        text.append("100dp:" + getResources().getDimension(R.dimen.dp100) + "\n");
        text.append("100/" + density + "dp:" + (getResources().getDimension(R.dimen.dp100) / density) + "dp\n");

        text.append("\n");
        text.append("RealSizeHeight:" + mRealSizeHeight + "\n");
        text.append("RealSizeWidth:" + mRealSizeWidth + "\n");
        text.append("\n");
        text.append("sw:" + Math.min((mRealSizeHeight / density), (mRealSizeWidth / density)) + "\n");
        text.append("pixel:" + heightPixels + "x" + widthPixels);
    }

}

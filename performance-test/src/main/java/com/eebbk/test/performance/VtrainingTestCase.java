package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.PackageConstants.Vtraining;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class VtrainingTestCase extends PerforTestCase {
    @Test
    public void launchVtraining() throws IOException, UiObjectNotFoundException, InterruptedException, JSONException {
        BySelector byVtrain = By.text("名师辅导班");
        Bitmap source_png = getHomeSourceScreen(byVtrain, Vtraining.PACKAGE, "my_plan_banner_scale_id", 20000);
        Rect refreshPngRect = new Rect(0, 0, source_png.getWidth(), source_png.getHeight() - 70);
        Rect loadPngRect = new Rect(0, source_png.getHeight() - 70, source_png.getWidth(), source_png.getHeight());
        for (int i = 0; i < mCount; i++) {
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(byVtrain), WAIT_TIME);
            UiObject2 byVtraining = mDevice.findObject(byVtrain);
            startTestRecord();
            byVtraining.clickAndWait(Until.newWindow(), WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "my_plan_banner_scale_id")), WAIT_TIME);
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            mDevice.pressHome();
            clearRunprocess();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    //点击首页更多精彩→课程列表页面，加载完成
    //点击首页视频缩略图→视频播放加载完成
    //点击选课→科目→课程包封面，加载完成
    //点击课程包封面→视频播放界面加载完成
    //选课界面，点击banner图名师在这里图片→名师页面加载完成
    //点击名师头像→名师详情加载完成
    //已加入5个课程，进入我的界面，点击已加入课程→列表内容加载完成
    //已下载5个课程，进入我的界面，点击已下载课程→列表内容加载完成
    //我的界面，点击排行榜→排行榜页面，加载完成
}

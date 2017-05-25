package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.PackageConstants.SynMath;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class SyncMathTestCase extends PerforTestCase {
    // 同步数学
    @Test
    public void launchSynMath() throws IOException, UiObjectNotFoundException, JSONException, InterruptedException {
        BySelector bySynMath = By.text("同步数学");
        Bitmap source_png = getHomeSourceScreen(bySynMath, SynMath.PACKAGE, "refreshBtnId", 1000);
        Rect loadPngRect = new Rect(0, 0, source_png.getWidth(), source_png.getHeight());
        for (int i = 0; i < mCount; i++) {
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySynMath), WAIT_TIME);
            UiObject2 synMath = mDevice.findObject(bySynMath);
            startTestRecord();
            synMath.clickAndWait(Until.newWindow(), WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, loadPngRect,new Date());
            mDevice.wait(Until.hasObject(By.res(SynMath.PACKAGE, "refreshBtnId")), WAIT_TIME);
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            mDevice.pressHome();
            clearRunprocess();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    //点击添加按钮→下载界面加载完成
    @Test
    public void addMathBook() {

    }

    //点击书本→书本目录加载完成
    @Test
    public void showSynMathBook() {

    }

    //进入课本目录界面，点击左边换书按钮→书架界面显示完成
    @Test
    public void changeSynMathBook() {

    }

    //进入课本目录界面，点击动画讲解→动画讲解界面加载完成
    @Test
    public void showExplanationSynMathContent() {

    }

    //进入课本目录界面，点击动画讲解右边的下载按钮→下载页面加载完成
    @Test
    public void downloadExplanatinSynMathContent() {

    }

    //点击教辅目录→进入课本详情
    @Test
    public void showDetailsSynMathBook() {

    }

    //书架界面10本书，点击刷新→刷新完成
    @Test
    public void refreshSynMath() {

    }

    //书本内容界面，点击左上角目录按钮，点击知识讲解→知识讲解内容加载完成
    @Test
    public void showExplanationSynMathBook() {

    }
}

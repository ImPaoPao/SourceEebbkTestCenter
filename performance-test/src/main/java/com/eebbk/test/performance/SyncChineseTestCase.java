package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.PackageConstants.SynChinese;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class SyncChineseTestCase extends PerforTestCase {
    @Test
    public void launchSyncChinese() throws IOException, UiObjectNotFoundException, JSONException, RemoteException,
            InterruptedException {
        BySelector synchinese = By.text("同步语文");
        Bitmap source_png = getHomeSourceScreen(synchinese, SynChinese.PACKAGE, "refresh", 0);
        Rect loadPngRect = new Rect(0,0,source_png.getWidth(),source_png.getHeight());
        for (int i = 0; i < mCount; i++) {
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(synchinese), WAIT_TIME);
            UiObject2 synChineseObj = mDevice.findObject(synchinese);
            startTestRecord();
            //将源图片转换为Bitmap
            synChineseObj.clickAndWait(Until.newWindow(), WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect,loadPngRect,new Date());
            mDevice.wait(Until.hasObject(By.res(SynChinese.PACKAGE, "refresh")), WAIT_TIME);
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            mDevice.pressHome();
            clearRunprocess();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }



    //添加按钮 界面加载完成
    @Test
    public void addChineseBook() {

    }

    //点击书本→书本内容界面显示完成
    @Test
    public void showSynChineseBook() {

    }

    //点击教辅目录→进入课本详情
    @Test
    public void showDetailsSynChineseBook() {

    }

    //点击查字词→调转到词典界面
    @Test
    public void syncChineseAccessDict() {

    }


    //书本内容界面点击头像→个人信息页面加载完成
    @Test
    public void synChineseSelfInfo() {

    }

    //生字页面，点击一个生字，点击写一写→进入写一写界面
    @Test
    public void synChineseNewWord() {

    }

    //书架界面10本书，点击刷新→刷新完成
    @Test
    public void synChineseRefresh() throws IOException, JSONException {
        //首页:刷新 com.eebbk.synchinese:id/refresh
        //图书列表:android.widget.ListView
        //删除 com.eebbk.synchinese:id/edit
        mHelper.openSynChinese();
        mDevice.wait(Until.hasObject(By.res(SynChinese.PACKAGE, "refresh")), WAIT_TIME);
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        //中间刷新的那个黑色的带彩色点的小方块
        Rect loadPngRect = new Rect(source_png.getWidth() / 2 - 40, source_png.getHeight() / 2 - 40,
                source_png.getWidth() / 2 + 40, source_png.getHeight() / 2 + 40);
        SystemClock.sleep(1000);
        clearRunprocess();
        for (int i = 0; i < mCount; i++) {
            mHelper.openSynChinese();
            UiObject2 refresh = mDevice.findObject(By.res(SynChinese.PACKAGE, "refresh"));
            startTestRecord();
            refresh.clickAndWait(Until.newWindow(), WAIT_TIME);
            SystemClock.sleep(200);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, new Date());
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            mDevice.waitForIdle();
            SystemClock.sleep(2000);
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

}
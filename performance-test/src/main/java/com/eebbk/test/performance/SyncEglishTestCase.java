package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.BitmapHelper;
import com.eebbk.test.common.PackageConstants.SyncEnglish;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;


@RunWith(AndroidJUnit4.class)
public class SyncEglishTestCase extends PerforTestCase {
    BySelector bySelector = By.text("同步英语");

    @Test
    public void launchSyncEnglish() throws IOException, UiObjectNotFoundException, InterruptedException, JSONException {
        JSONObject obj = new JSONObject();
        Bitmap source_png = getHomeSourceScreen(bySelector, SyncEnglish.PACKAGE, "imageview_mainbookshelf_blackboard",
                5000);
        for (int i = 0; i < mCount; i++) {
            String startScreen = "";
            String endScreen = "";
            String compareTime = "";
            int compareResult = 10;
            Date timeStamp1 = new Date();
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySelector), WAIT_TIME);
            UiObject2 synMath = mDevice.findObject(bySelector);
            startTestRecord();
            synMath.clickAndWait(Until.newWindow(), WAIT_TIME);
            int m = 0;
            do {
                m++;
                startScreen = getCurrentDate();
                Bitmap des_png = mAutomation.takeScreenshot();
                endScreen = getCurrentDate();
                compareResult = BitmapHelper.compare(source_png, des_png);
                obj.put("compareResult" + String.valueOf(m), compareResult);
                compareTime = getCurrentDate();
                if (!des_png.isRecycled()) {
                    des_png.recycle();
                }
                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME * 5) {
                    break;
                }
            } while (compareResult >= 5);
            instrumentationStatusOut(obj);
            String loadTime = getCurrentDate();
            mDevice.wait(Until.hasObject(By.res(SyncEnglish.PACKAGE, "imageview_mainbookshelf_blackboard")), WAIT_TIME);
            stopTestRecord(loadTime, startScreen, endScreen, compareTime, String.valueOf(compareResult));
            mDevice.pressHome();
            clearRunprocess();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }


    //前置条件：首页下载好十本书
    @Test
    public void synEnglishRefresh() throws IOException, JSONException, InterruptedException {
        JSONObject obj = new JSONObject();
        mHelper.openSyncEnglish();
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        UiObject2 refresh = mDevice.findObject(By.res(SyncEnglish.PACKAGE, "refresh"));
        for (int i = 0; i < mCount; i++) {
            String startScreen = "";
            String endScreen = "";
            String compareTime = "";
            int compareResult = 10;
            Date timeStamp1 = new Date();
            startTestRecord();
            refresh.clickAndWait(Until.newWindow(), WAIT_TIME);
            SystemClock.sleep(200);
            int m = 0;
            do {
                m++;
                startScreen = getCurrentDate();
                Bitmap des_png = Bitmap.createBitmap(mAutomation.takeScreenshot(), mDevice.getDisplayWidth()
                        / 2 - 30, mDevice.getDisplayHeight() / 2 - 30, 60, 60);
                endScreen = getCurrentDate();
                compareResult = BitmapHelper.compare(Bitmap.createBitmap(source_png, mDevice.getDisplayWidth()
                        / 2 - 30, mDevice.getDisplayHeight() / 2 - 30, 60, 60), des_png);
                compareTime = getCurrentDate();
                obj.put("compare:" + String.valueOf(m), compareResult);
                if (!des_png.isRecycled()) {
                    des_png.recycle();
                }
                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME * 2) {
                    break;
                }
            } while (compareResult >= 1);
            String loadTime = getCurrentDate();
            stopTestRecord(loadTime, startScreen, endScreen, compareTime, String.valueOf(compareResult));
            mDevice.waitForIdle();
            instrumentationStatusOut(obj);
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    //点击添加按钮→下载界面加载完成
    @Test
    public void addSyncEnglishBook() {

    }

    //点击书本→书本内容界面显示完成
    @Test
    public void showSyncEnglishBook() {

    }

    //书本内容界面点击头像→个人信息页面加载完成
    @Test
    public void syncEnglishSelfInfo() {

    }
    //书本内容界面点击趣味测验→测验页面内容加载完成
    @Test
    public void syncEnglishFunTest() {

    }
    //书本内容界面点击flash按钮→flas页面加载完成
    @Test
    public void syncEnglishFlash() {

    }
    //点读页面，点击句子选择单词--查，点击反查→词典列表弹出框加载完成
    @Test
    public void syncEnglishAccessDict() {

    }
    //趣味测验点击欧拉学英语→跳转到商店页面加载完成
    @Test
    public void syncEnglishOlaAccessBbkMarket() {

    }
}

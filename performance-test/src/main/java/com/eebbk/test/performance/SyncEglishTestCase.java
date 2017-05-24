package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.os.SystemClock;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;
import android.widget.ListView;

import com.eebbk.test.common.BitmapHelper;
import com.eebbk.test.common.PackageConstants.SyncEnglish;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;


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
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySelector), WAIT_TIME);
            UiObject2 synMath = mDevice.findObject(bySelector);
            Date timeStamp1 = new Date();
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
        UiObject2 refresh = mDevice.findObject(By.res(SyncEnglish.PACKAGE, "refresh"));
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(1000);
        for (int i = 0; i < 3; i++) {
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
                obj.put("compare:" + String.valueOf(m) + String.valueOf(i), compareResult);
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
            SystemClock.sleep(1000);
            instrumentationStatusOut(obj);
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    //点击添加按钮→下载界面加载完成
    @Test
    public void addSyncEnglishBook() throws IOException, JSONException {
        JSONObject obj = new JSONObject();
        //获取屏幕截图
        mHelper.openSyncEnglish();
        UiObject2 add = mDevice.findObject(By.res(SyncEnglish.PACKAGE, "add_id"));
        add.clickAndWait(Until.newWindow(), WAIT_TIME);
        mDevice.wait(Until.hasObject(By.res(SyncEnglish.PACKAGE, "iv_cover")), WAIT_TIME * 5);
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(1000);
        clearRunprocess();
        for (int i = 0; i < mCount; i++) {
            String startScreen = "";
            String endScreen = "";
            String compareTime = "";
            int compareResult = 1;
            mHelper.openSyncEnglish();
            mDevice.wait(Until.hasObject(By.res(SyncEnglish.PACKAGE, "add_id")), WAIT_TIME);
            UiObject2 add2 = mDevice.findObject(By.res(SyncEnglish.PACKAGE, "add_id"));
            Date timeStamp1 = new Date();
            startTestRecord();
            add2.clickAndWait(Until.newWindow(), WAIT_TIME);
            int m = 0;
            do {
                m++;
                startScreen = getCurrentDate();
                Bitmap des_png = Bitmap.createBitmap(mAutomation.takeScreenshot(), mDevice.getDisplayWidth()
                        / 2 - 100, mDevice.getDisplayHeight() / 2 - 100, 100, 100);
                endScreen = getCurrentDate();
                compareResult = BitmapHelper.compare(Bitmap.createBitmap(source_png, mDevice.getDisplayWidth()
                        / 2 - 100, mDevice.getDisplayHeight() / 2 - 100, 100, 100), des_png);
                compareTime = getCurrentDate();
                obj.put("compare:" + String.valueOf(m), compareResult);
                if (!des_png.isRecycled()) {
                    des_png.recycle();
                }
                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME * 5) {
                    break;
                }
            } while (compareResult >= 1);
            String loadTime = getCurrentDate();
            stopTestRecord(loadTime, startScreen, endScreen, compareTime, String.valueOf(compareResult));
            mDevice.waitForIdle();
            mDevice.pressBack();
            instrumentationStatusOut(obj);
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    //点击书本→书本内容界面显示完成
    @Test
    public void showSyncEnglishBook() throws JSONException {
        JSONObject obj = new JSONObject();
        mHelper.openSyncEnglish();
        mDevice.wait(Until.hasObject(By.clazz(ListView.class)), WAIT_TIME);
        UiObject2 booklist = mDevice.findObject(By.clazz(ListView.class));
        List<UiObject2> children = booklist.getChildren();
        UiObject2 child = children.get(children.size() / 2);
        child.getChildren().get(0).clickAndWait(Until.newWindow(), WAIT_TIME);
        SystemClock.sleep(5000);
        instrumentationStatusOut(obj);
    }

    //书本内容界面点击头像→个人信息页面加载完成
    @Test
    public void syncEnglishSelfInfo() {
//        mHelper.openSyncEnglish();//回到书列表界面
        mHelper.openSyncEnglishMain();
//        if (!mDevice.wait(Until.hasObject(By.res(SyncEnglish.PACKAGE, "toptoolbar_id")), WAIT_TIME * 1)) {
//            mDevice.wait(Until.hasObject(By.clazz(ListView.class)), WAIT_TIME);
//            UiObject2 booklist = mDevice.findObject(By.clazz(ListView.class));
//            List<UiObject2> children = booklist.getChildren();
//            UiObject2 child = children.get(children.size() / 2);
//            child.getChildren().get(0).clickAndWait(Until.newWindow(), WAIT_TIME);
//
//        }else{
//            //带下拉环的菜单
//            UiObject2 dropDown = mDevice.findObject(By.res(SyncEnglish.PACKAGE, "toptoolbar_id"));
//            if (dropDown!=null){
//                Rect rt = dropDown.getVisibleBounds();
//                //点击下拉环
//                mHelper.longClick(rt.right-35,rt.height()/2);
//                SystemClock.sleep(1000);
//                //点击头像
//                //longClick(60,rt.height()/2);
//
//                //点击趣味测试
//                mHelper.longClick(rt.right-45,rt.height()/2);
//            }
//        }

    }

    //书本内容界面点击趣味测验→测验页面内容加载完成
    @Test
    public void syncEnglishFunTest() {

    }

    //书本内容界面点击flash按钮→flash页面加载完成
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

    private void openOneBook() {
        //打开一本课本，非教材资料书
        JSONObject obj = new JSONObject();
        mHelper.openSyncEnglish();
        mDevice.waitForIdle();
//        if(mDevice.findObject(By.res(SyncEnglish.PACKAGE,"toptoolbar_id"),WAIT_TIME)){
//
//        }
        mDevice.wait(Until.hasObject(By.clazz(ListView.class)), WAIT_TIME);
        UiObject2 booklist = mDevice.findObject(By.clazz(ListView.class));
        List<UiObject2> children = booklist.getChildren();
        UiObject2 child = children.get(children.size() / 2);
        child.getChildren().get(0).clickAndWait(Until.newWindow(), WAIT_TIME);
        SystemClock.sleep(5000);
        instrumentationStatusOut(obj);
    }

}

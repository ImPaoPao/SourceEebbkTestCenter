package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.os.RemoteException;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.BitmapHelper;
import com.eebbk.test.common.PackageConstants.SynChinese;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import static android.os.SystemClock.sleep;

@RunWith(AndroidJUnit4.class)
public class SyncChineseTestCase extends PerforTestCase {
    @Test
    public void launchSyncChinese() throws IOException, UiObjectNotFoundException, JSONException, RemoteException,
            InterruptedException {
        BySelector synchinese = By.text("同步语文");
        Bitmap source_png = getHomeSourceScreen(synchinese, SynChinese.PACKAGE, "refresh", 0);
        for (int i = 0; i < mCount; i++) {
            String startScreen = "";
            String endScreen = "";
            String compareTime = "";
            int compareResult = 10;
            Date timeStamp1 = new Date();
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(synchinese), WAIT_TIME);
            UiObject2 synChineseObj = mDevice.findObject(synchinese);
            startTestRecord();
            //将源图片转换为Bitmap
            synChineseObj.clickAndWait(Until.newWindow(), WAIT_TIME);
            do {
                startScreen = getCurrentDate();
                Bitmap des_png = mAutomation.takeScreenshot();
                endScreen = getCurrentDate();
                compareResult = BitmapHelper.compare(source_png, des_png);
                compareTime = getCurrentDate();
                if (!des_png.isRecycled()) {
                    des_png.recycle();
                }
                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME * 4) {
                    break;
                }
            } while (compareResult >= 5);
            String loadTime = getCurrentDate();
            mDevice.wait(Until.hasObject(By.res(SynChinese.PACKAGE, "refresh")), WAIT_TIME);
            stopTestRecord(loadTime, startScreen, endScreen, compareTime, String.valueOf(compareResult));
            mDevice.pressHome();
            clearRunprocess();
            sleep(1000);
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
    public void synChineseRefresh() {

    }

}
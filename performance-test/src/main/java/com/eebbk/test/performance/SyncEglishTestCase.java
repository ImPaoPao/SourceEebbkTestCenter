package com.eebbk.test.performance;

import android.graphics.Bitmap;
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

import static android.os.SystemClock.sleep;


@RunWith(AndroidJUnit4.class)
public class SyncEglishTestCase extends PerforTestCase {
    BySelector bySelector = By.text("同步英语");
    @Test
    public void launchSyncEnglish() throws IOException, UiObjectNotFoundException, InterruptedException, JSONException {
        JSONObject obj = new JSONObject();
        BySelector bySynEng = By.text("同步英语");
        Bitmap source_png = getHomeSourceScreen(bySynEng, SyncEnglish.PACKAGE, "imageview_mainbookshelf_blackboard",
                5000);
        for (int i = 0; i < mCount; i++) {
            String startScreen = "";
            String endScreen = "";
            String compareTime = "";
            int compareResult = 10;
            Date timeStamp1 = new Date();

            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySynEng), WAIT_TIME);
            UiObject2 synMath = mDevice.findObject(bySynEng);
            startTestRecord();
            synMath.clickAndWait(Until.newWindow(), WAIT_TIME);
            sleep(1000);
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
                    obj.put("break========:" + String.valueOf(m), compareResult);
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
    public void refreshEnglishBook() throws IOException, JSONException, InterruptedException {
        JSONObject obj = new JSONObject();
        obj.put("refresh:","===========");
        mHelper.openSyncEnglish();
//        BySelector bySynEng = By.text("英语听说");
        //com.eebbk.syncenglish:id/refresh
//        openEnglishTalkHome();
//        Bitmap source_png = Bitmap.createBitmap(getHomeSourceScreen(bySynEng, EnglishTalk.PACKAGE,
//                "refresh", 5000), mDevice.getDisplayWidth()
//                / 2 - 30, mDevice.getDisplayHeight() / 2 - 30, 60, 60);
//        sleep(5000);
//        for (int i = 0; i < mCount; i++) {
//            String startScreen = "";
//            String endScreen = "";
//            String compareTime = "";
//            int compareResult = 10;
//            Date timeStamp1 = new Date();
//            startTestRecord();
//            mDevice.click(mDevice.getDisplayWidth() * 16 / 100, mDevice.getDisplayHeight() * 8 / 100);
//            int m = 0;
//            do {
//                m++;
//                startScreen = getCurrentDate();
//                Bitmap des_png = Bitmap.createBitmap(mAutomation.takeScreenshot(), mDevice.getDisplayWidth()
//                        / 2 - 30, mDevice.getDisplayHeight() / 2 - 30, 60, 60);
//                endScreen = getCurrentDate();
//                compareResult = BitmapHelper.compare(source_png, des_png);
//                compareTime = getCurrentDate();
//                obj.put("compare:" + String.valueOf(m), compareResult);
//                if (!des_png.isRecycled()) {
//                    des_png.recycle();
//                }
//                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME * 5) {
//                    break;
//                }
//            } while (compareResult >= 5);
//            String loadTime = getCurrentDate();
//            stopTestRecord(loadTime, startScreen, endScreen, compareTime, String.valueOf(compareResult));
//            mDevice.waitForIdle();
//            instrumentationStatusOut(obj);
//        }
//        if (!source_png.isRecycled()) {
//            source_png.recycle();
//        }
    }

}

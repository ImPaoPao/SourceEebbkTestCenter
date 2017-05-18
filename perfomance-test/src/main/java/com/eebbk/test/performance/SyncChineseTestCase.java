package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.BitmapHelper;
import com.eebbk.test.common.PackageConstants.SynChinese;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.IOException;

import static android.os.SystemClock.sleep;

@RunWith(AndroidJUnit4.class)
public class SyncChineseTestCase extends PerforTestCase {
    @Test
    public void launchSyncChinese() throws IOException, UiObjectNotFoundException, JSONException {
        //mType 0:冷启动
        BySelector synchinese = By.text("同步语文");
        for (int i = 0; i < mCount; i++) {
            JSONObject obj = new JSONObject();
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(By.text("同步语文")), WAIT_TIME);
            UiObject2 synChineseObj = mDevice.findObject(synchinese);
            FileInputStream source_fis = new FileInputStream("/sdcard/1.png");
            Bitmap source_png = BitmapFactory.decodeStream(source_fis);
            startTestRecord();
            //将源图片转换为Bitmap
            synChineseObj.clickAndWait(Until.newWindow(), WAIT_TIME);
            int compare_result = 10;
            int m = 3;
            do {
                m++;
                obj.put("startScreen" + String.valueOf(m), getCurrentDate());
                Bitmap des_png = mAutomation.takeScreenshot();
                obj.put("endScreen" + String.valueOf(m), getCurrentDate());
                compare_result = BitmapHelper.compare(source_png, des_png);
                obj.put("compareTime" + String.valueOf(m), getCurrentDate());
                obj.put("compare_result:" + String.valueOf(m), compare_result);
//                stopTime=getCurrentDate();
            } while (compare_result >= 10);
            String stopTime=getCurrentDate();
            obj.put("stopTime:", stopTime);
            instrumentationStatusOut(obj);
            //button
            mDevice.wait(Until.hasObject(By.res(SynChinese.PACKAGE, "refresh")), WAIT_TIME);
            stopTestRecord(stopTime);
            mDevice.pressHome();
            clearRunprocess();
            sleep(1000);
        }
    }
}
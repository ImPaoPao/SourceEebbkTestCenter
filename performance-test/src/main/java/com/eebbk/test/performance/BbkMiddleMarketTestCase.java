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
import com.eebbk.test.common.PackageConstants.BbkMiddleMarket;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class BbkMiddleMarketTestCase extends PerforTestCase {

    @Test
    public void compareTest() throws JSONException, FileNotFoundException {
        //匹配度测试
        stopTestRecord();
        JSONObject obj = new JSONObject();
        FileInputStream fis1 = new FileInputStream("/sdcard/performance-test/1.png");
        FileInputStream fis2 = new FileInputStream("/sdcard/performance-test/2.png");
//        Bitmap png1 = Bitmap.createBitmap(BitmapFactory.decodeStream(fis1),mDevice.getDisplayWidth()
//                / 2 - 30, mDevice.getDisplayHeight() / 2 - 30, 60, 60);
//        Bitmap png2 = Bitmap.createBitmap(BitmapFactory.decodeStream(fis2),mDevice.getDisplayWidth()
//                / 2 - 30, mDevice.getDisplayHeight() / 2 - 30, 60, 60);
//        obj.put("compare result", BitmapHelper.compare(Bitmap.createBitmap(png1, 0, 310, mDevice.getDisplayWidth(),
//                mDevice.getDisplayHeight() - 310), Bitmap.createBitmap(png2, 0, 310, mDevice.getDisplayWidth(),
//                mDevice.getDisplayHeight() - 310)));
        obj.put("compare result 2 ", BitmapHelper.compare(BitmapFactory.decodeStream
                (fis1), BitmapFactory.decodeStream(fis2)));
//        obj.put("compare result", BitmapHelper.compare(png1,png2));
        instrumentationStatusOut(obj);
        stopTestRecord();
    }


    @Test
    public void launchBbkMiddleMarket() throws IOException, UiObjectNotFoundException, JSONException,
            InterruptedException {

        BySelector bySynBbkMarket = By.text("应用商店");
        Bitmap source_png = getHomeSourceScreen(bySynBbkMarket, BbkMiddleMarket.PACKAGE, "apk_button", 2000);
        for (int i = 0; i < mCount; i++) {
            JSONObject obj = new JSONObject();
            String loadTime = "";
            int loadResult = 1;
            int refreshResult = 1;
            boolean loadFlag = true;
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySynBbkMarket), WAIT_TIME);
            UiObject2 bbkMarket = mDevice.findObject(bySynBbkMarket);
            Date timeStamp1 = new Date();
            startTestRecord();
            bbkMarket.clickAndWait(Until.newWindow(), WAIT_TIME);
            int m = 0;
            do {
                m++;
                Bitmap des_png = mAutomation.takeScreenshot();
                if (loadFlag) {
                    obj.put(String.valueOf(m) + ":", loadFlag);
                    loadResult = BitmapHelper.compare(Bitmap.createBitmap(source_png, 0, source_png.getHeight() - 70,
                            source_png.getWidth(), 70), Bitmap.createBitmap(des_png, 0, des_png.getHeight() - 70,
                            des_png
                                    .getWidth(), 70));
                    obj.put(String.valueOf(m) + "loadResult===:", loadResult);
                }
                if (loadResult <= 1 && loadFlag) {
                    obj.put(String.valueOf(m) + "loadResult***:", loadResult);
                    loadTime = getCurrentDate();
                    loadFlag = false;
                }
                refreshResult = BitmapHelper.compare(Bitmap.createBitmap(source_png, 0, 0, source_png.getWidth(),
                        source_png.getHeight() - 100), Bitmap.createBitmap(des_png, 0, 0, des_png.getWidth(), des_png
                        .getHeight() - 100));
                obj.put(String.valueOf(m) + "refreshResult:", refreshResult);
                if (des_png != null && !des_png.isRecycled()) {
                    des_png.recycle();
                }
                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME * 5) {
                    break;
                }
            } while (loadResult >= 1 || refreshResult >= 1);
            String refreshTime = getCurrentDate();
            mDevice.wait(Until.hasObject(By.res(BbkMiddleMarket.PACKAGE, "apk_button")), WAIT_TIME);
            stopTestRecord(loadTime, refreshTime,String.valueOf(loadResult), String.valueOf(refreshResult));
            mDevice.pressHome();
            instrumentationStatusOut(obj);
            clearRunprocess();
        }
        if (source_png!=null && !source_png.isRecycled()) {
            source_png.recycle();
        }
    }
}

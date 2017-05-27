package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
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
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class BbkMiddleMarketTestCase extends PerforTestCase {

    @Test
    public void compareTest() throws JSONException, FileNotFoundException {
        //匹配度测试
        startTestRecord();
        JSONObject obj = new JSONObject();
        FileInputStream fis1 = new FileInputStream("/sdcard/tt.png");
        FileInputStream fis2 = new FileInputStream("/sdcard/tt1.png");
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
//        mHelper.takeScreenshot(new Rect(0, mDevice.getDisplayHeight() / 4, mDevice.getDisplayWidth() * 2 / 5,
//                mDevice.getDisplayHeight() * 3 / 4), new File("/sdcard/tt1.png"), 1);
        instrumentationStatusOut(obj);
        stopTestRecord();
    }


    @Test
    public void launchBbkMiddleMarket() throws IOException, UiObjectNotFoundException, JSONException,
            InterruptedException {
        BySelector bySynBbkMarket = By.text("应用商店");
        Bitmap source_png = getHomeSourceScreen(bySynBbkMarket, BbkMiddleMarket.PACKAGE, "apk_button", 10000);
        Rect refreshPngRect = new Rect(0, 0, source_png.getWidth(), source_png.getHeight() - 80);
        Rect loadPngRect = new Rect(0, source_png.getHeight() - 70, source_png.getWidth(), source_png.getHeight());
        for (int i = 0; i < mCount; i++) {
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySynBbkMarket), WAIT_TIME);
            UiObject2 bbkMarket = mDevice.findObject(bySynBbkMarket);
            startTestRecord();
            bbkMarket.clickAndWait(Until.newWindow(), WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            mDevice.wait(Until.hasObject(By.res(BbkMiddleMarket.PACKAGE, "apk_button")), WAIT_TIME);
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            mDevice.pressHome();
            clearRunprocess();
        }
        if (source_png != null && !source_png.isRecycled()) {
            source_png.recycle();
        }
    }
}

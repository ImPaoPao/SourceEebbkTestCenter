package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.PackageConstants.Vision;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class VisionTestCase extends PerforTestCase {
    //视力保护:优化点，动态画面的获取，获取控件 ，计时。
    @Test
    public void launchVision() throws IOException, UiObjectNotFoundException, JSONException, InterruptedException {
        JSONObject obj = new JSONObject();
        BySelector byVision = By.text("视力保护");
        Bitmap source_png = getHomeSourceScreen(byVision, Vision.PACKAGE, 2000);
        Rect loadPngRect = new Rect(0, 0, source_png.getWidth(), source_png.getHeight());
        for (int i = 0; i < mCount; i++) {
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(byVision), WAIT_TIME);
            UiObject2 vision = mDevice.findObject(byVision);
            startTestRecord();
            vision.clickAndWait(Until.newWindow(), WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, new Date());
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            mDevice.pressHome();
            clearRunprocess();
        }
        if(!source_png.isRecycled()){
            source_png.recycle();
        }
    }
}

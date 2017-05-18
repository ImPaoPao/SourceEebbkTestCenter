package com.eebbk.test.performance;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static android.os.SystemClock.sleep;

@RunWith(AndroidJUnit4.class)
public class VisionTestCase extends PerforTestCase {
    //视力保护:优化点，动态画面的获取，获取控件 ，计时。
    @Test
    public void launchVision() throws IOException, UiObjectNotFoundException, JSONException {
        //mType 0:冷启动
        BySelector byVision = By.text("视力保护");
        for (int i = 0; i < mCount; i++) {
            JSONObject obj = new JSONObject();
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(byVision), WAIT_TIME);
            UiObject2 vision = mDevice.findObject(byVision);
            startTestRecord();
            vision.clickAndWait(Until.newWindow(), WAIT_TIME);
            obj.put("begaiwait", getCurrentDate());
//            mDevice.dumpWindowHierarchy("/sdcard/vision.txt");
//            String loadtime = getCurrentDate();
//            obj.put("loadtime", loadtime);
//            stopTestRecord(loadtime);
            sleep(2000);
            stopTestRecord();
            instrumentationStatusOut(obj);
            mDevice.pressHome();
            clearRunprocess();
        }
    }
}

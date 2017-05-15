package com.eebbk.test.performance;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.PackageConstants.SynMath;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class SyncMathTestCase extends PerforTestCase {
    // 同步数学
    @Test
    public void launchSynMath() throws IOException, UiObjectNotFoundException, JSONException {
        //mType 0:冷启动
        BySelector bySynMath = By.text("同步数学");
        for (int i = 0; i < mCount; i++) {
            JSONObject obj = new JSONObject();
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySynMath), WAIT_TIME);
            UiObject2 synMath = mDevice.findObject(bySynMath);
            startTestRecord();
            synMath.clickAndWait(Until.newWindow(), WAIT_TIME);
            mDevice.wait(Until.hasObject(By.res(SynMath.PACKAGE, "refreshBtnId")), WAIT_TIME);
            String loadtime = getCurrentDate();
            obj.put("loadtime",loadtime);
            stopTestRecord(loadtime);
            instrumentationStatusOut(obj);
            mDevice.pressHome();
            clearRunprocess();
        }
    }
}

package com.eebbk.test.performance;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.PackageConstants.BbkMiddleMarket;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class BbkMiddleMarketTestCase extends PerforTestCase {
    @Test
    public void launchBbkMiddleMarket() throws IOException, UiObjectNotFoundException, JSONException {
        //mType 0:冷启动
        BySelector bySynEng = By.text("应用商店");
        for (int i = 0; i < mCount; i++) {
            JSONObject obj = new JSONObject();
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySynEng), WAIT_TIME);
            UiObject2 synMath = mDevice.findObject(bySynEng);
            startTestRecord();
            synMath.clickAndWait(Until.newWindow(), WAIT_TIME);
            mDevice.wait(Until.hasObject(By.res(BbkMiddleMarket.PACKAGE, "apk_button")), WAIT_TIME*2);
            String loadtime = getCurrentDate();
            stopTestRecord(loadtime);
            mDevice.pressHome();
            clearRunprocess();
        }
    }
}

package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.BitmapHelper;
import com.eebbk.test.common.PackageConstants.SynMath;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class SyncMathTestCase extends PerforTestCase {
    // 同步数学
    @Test
    public void launchSynMath() throws IOException, UiObjectNotFoundException, JSONException, InterruptedException {
        BySelector bySynMath = By.text("同步数学");
        Bitmap source_png = getHomeSourceScreen(bySynMath, SynMath.PACKAGE,"refreshBtnId",1000);

        for (int i = 0; i < mCount; i++) {
            String startScreen = "";
            String endScreen = "";
            String compareTime = "";
            int compareResult = 5;
            Date timeStamp1 = new Date();
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySynMath), WAIT_TIME);
            UiObject2 synMath = mDevice.findObject(bySynMath);
            startTestRecord();
            synMath.clickAndWait(Until.newWindow(), WAIT_TIME);
            do {
                startScreen = getCurrentDate();
                Bitmap des_png = mAutomation.takeScreenshot();
                endScreen = getCurrentDate();
                compareResult = BitmapHelper.compare(source_png, des_png);
                compareTime = getCurrentDate();
                if(!des_png.isRecycled()){
                    des_png.recycle();
                }

                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME*4) {
                    break;
                }
            } while (compareResult >=5);
            String loadTime = getCurrentDate();
            mDevice.wait(Until.hasObject(By.res(SynMath.PACKAGE, "refreshBtnId")), WAIT_TIME);
            stopTestRecord(loadTime, startScreen, endScreen, compareTime, String.valueOf(compareResult));
            mDevice.pressHome();
            clearRunprocess();
        }
        if(!source_png.isRecycled()){
            source_png.recycle();
        }
    }
}

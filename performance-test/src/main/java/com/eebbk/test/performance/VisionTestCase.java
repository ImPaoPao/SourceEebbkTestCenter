package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.BitmapHelper;
import com.eebbk.test.common.PackageConstants.Vision;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import static android.os.SystemClock.sleep;

@RunWith(AndroidJUnit4.class)
public class VisionTestCase extends PerforTestCase {
    //视力保护:优化点，动态画面的获取，获取控件 ，计时。
    @Test
    public void launchVision() throws IOException, UiObjectNotFoundException, JSONException, InterruptedException {
        JSONObject obj = new JSONObject();
        BySelector byVision = By.text("视力保护");
        Bitmap source_png = getHomeSourceScreen(byVision, Vision.PACKAGE, 2000);
        for (int i = 0; i < mCount; i++) {
            String startScreen = "";
            String endScreen = "";
            String compareTime = "";
            int compareResult = 10;
            Date timeStamp1 = new Date();
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(byVision), WAIT_TIME);
            UiObject2 vision = mDevice.findObject(byVision);
            startTestRecord();
            vision.clickAndWait(Until.newWindow(), WAIT_TIME);
            do {
                startScreen = getCurrentDate();
                Bitmap des_png = mAutomation.takeScreenshot();
                endScreen = getCurrentDate();
                compareResult = BitmapHelper.compare(source_png, des_png);
                compareTime = getCurrentDate();
                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME) {
                    break;
                }
            } while (compareResult >= 10);
            String loadTime = getCurrentDate();
            sleep(1000);
            stopTestRecord(loadTime, startScreen, endScreen, compareTime, String.valueOf(compareResult));
            mDevice.pressHome();
            clearRunprocess();

        }
    }
}

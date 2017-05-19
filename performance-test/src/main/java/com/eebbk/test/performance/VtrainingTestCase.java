package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.BitmapHelper;
import com.eebbk.test.common.PackageConstants.Vtraining;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class VtrainingTestCase extends PerforTestCase {
    @Test
    public void launchVtraining() throws IOException, UiObjectNotFoundException, InterruptedException {
        BySelector byVtrain = By.text("名师辅导班");
        Bitmap source_png = getHomeSourceScreen(byVtrain, Vtraining.PACKAGE, "my_plan_banner_scale_id", 5000);
        for (int i = 0; i < mCount; i++) {
            String startScreen = "";
            String endScreen = "";
            String compareTime = "";
            int compareResult = 10;
            Date timeStamp1 = new Date();
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(byVtrain), WAIT_TIME);
            UiObject2 byVtraining = mDevice.findObject(byVtrain);
            startTestRecord();
            byVtraining.clickAndWait(Until.newWindow(), WAIT_TIME);
            do {
                startScreen = getCurrentDate();
                Bitmap des_png = mAutomation.takeScreenshot();
                endScreen = getCurrentDate();
                compareResult = BitmapHelper.compare(source_png, des_png);
                compareTime = getCurrentDate();
                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME*2) {
                    break;
                }
            } while (compareResult >= 10);
            String loadTime = getCurrentDate();
            mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "my_plan_banner_scale_id")), WAIT_TIME);
            stopTestRecord(loadTime, startScreen, endScreen, compareTime, String.valueOf(compareResult));
            mDevice.pressHome();
            clearRunprocess();
        }
    }
}

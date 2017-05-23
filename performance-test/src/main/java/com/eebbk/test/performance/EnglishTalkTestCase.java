package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.BitmapHelper;
import com.eebbk.test.common.PackageConstants.EnglishTalk;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import static android.os.SystemClock.sleep;

@RunWith(AndroidJUnit4.class)
public class EnglishTalkTestCase extends PerforTestCase {

    @Test
    public void launchEnglishTalk() throws IOException, UiObjectNotFoundException, InterruptedException {
        BySelector bySynEng = By.text("英语听说");
        Bitmap source_png = Bitmap.createBitmap(getHomeSourceScreen(bySynEng, EnglishTalk.PACKAGE,
                "tab_view_root_id", 5000), 0, 310, mDevice.getDisplayWidth(), mDevice.getDisplayHeight() - 310);
        for (int i = 0; i < mCount; i++) {
            String startScreen = "";
            String endScreen = "";
            String compareTime = "";
            int compareResult = 10;
            Date timeStamp1 = new Date();
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySynEng), WAIT_TIME);
            UiObject2 synMath = mDevice.findObject(bySynEng);
            startTestRecord();
            synMath.clickAndWait(Until.newWindow(), WAIT_TIME);
            do {
                startScreen = getCurrentDate();
                Bitmap des_png = Bitmap.createBitmap(mAutomation.takeScreenshot(), 0, 310, mDevice
                        .getDisplayWidth(), mDevice.getDisplayHeight() - 310);
                endScreen = getCurrentDate();
                compareResult = BitmapHelper.compare(source_png, des_png);
                compareTime = getCurrentDate();
                if (!des_png.isRecycled()) {
                    des_png.recycle();
                }
                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME * 5) {
                    break;
                }
            } while (compareResult >= 5);
            String loadTime = getCurrentDate();
            mDevice.wait(Until.hasObject(By.res(EnglishTalk.PACKAGE, "tab_view_root_id")), WAIT_TIME);
            sleep(3000);
            stopTestRecord(loadTime, startScreen, endScreen, compareTime, String.valueOf(compareResult));
            mDevice.pressHome();
            clearRunprocess();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

}

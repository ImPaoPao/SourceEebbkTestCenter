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

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import static android.os.SystemClock.sleep;

@RunWith(AndroidJUnit4.class)
public class EnglishTalkTestCase extends PerforTestCase {

    @Test
    public void launchEnglishTalk() throws IOException, UiObjectNotFoundException, InterruptedException, JSONException {
        BySelector bySynEng = By.text("英语听说");
        Bitmap source_png = Bitmap.createBitmap(getHomeSourceScreen(bySynEng, EnglishTalk.PACKAGE,
                "tab_view_root_id", 5000), 0, 310, mDevice.getDisplayWidth(), mDevice.getDisplayHeight() - 310);
        for (int i = 0; i < mCount; i++) {
            JSONObject obj = new JSONObject();
            String loadTime = "";
            int loadResult = 1;
            int refreshResult = 1;
            boolean loadFlag = true;
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySynEng), WAIT_TIME);
            UiObject2 synMath = mDevice.findObject(bySynEng);
            Date timeStamp1 = new Date();
            startTestRecord();
            synMath.clickAndWait(Until.newWindow(), WAIT_TIME);
            int m=0;
            do {
                m++;
                Bitmap des_png = mAutomation.takeScreenshot();
                if (loadFlag) {
                    obj.put(String.valueOf(m) + ":", loadFlag);
                    loadResult = BitmapHelper.compare(Bitmap.createBitmap(source_png, 0, source_png.getHeight() - 70,
                            source_png.getWidth(), 70), Bitmap.createBitmap(des_png, 0, des_png.getHeight() - 70,
                            des_png.getWidth(), 70));
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
                if (!des_png.isRecycled()) {
                    des_png.recycle();
                }
                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME * 5) {
                    break;
                }
            } while (loadResult >= 1 || refreshResult >= 1);
            String refreshTime = getCurrentDate();
            mDevice.wait(Until.hasObject(By.res(EnglishTalk.PACKAGE, "tab_view_root_id")), WAIT_TIME);
            sleep(3000);
            stopTestRecord(loadTime, refreshTime, String.valueOf(loadResult),String.valueOf(refreshResult));
            mDevice.pressHome();
            clearRunprocess();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

}

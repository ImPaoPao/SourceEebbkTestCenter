package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.BitmapHelper;
import com.eebbk.test.common.PackageConstants.OneSearchDark;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class OneSearchDarkTestCase extends PerforTestCase {
    @Test
    public void launchOneSearch() throws IOException, UiObjectNotFoundException, JSONException {
        JSONObject obj = new JSONObject();
        BySelector byOneSearch = By.text("一键搜");
        Bitmap source_png = getSourceScreen(byOneSearch);
        for (int i = 0; i < mCount; i++) {
            String startScreen = "";
            String endScreen = "";
            String compareTime = "";
            int compareResult = 10;
            Date timeStamp1 = new Date();
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(byOneSearch), WAIT_TIME);
            UiObject2 oneSearch = mDevice.findObject(byOneSearch);
            startTestRecord();
            oneSearch.clickAndWait(Until.newWindow(), WAIT_TIME);
            //compare
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
            mDevice.wait(Until.hasObject(By.res(OneSearchDark.PACKAGE, "btn_start_one_search")), WAIT_TIME);
            stopTestRecord(loadTime, startScreen, endScreen, compareTime, String.valueOf(compareResult));
            instrumentationStatusOut(obj);
            mDevice.pressHome();
            clearRunprocess();
        }
    }

    private Bitmap getSourceScreen(BySelector bySelector) throws IOException {
        swipeCurrentLauncher();
        mDevice.wait(Until.hasObject(bySelector), WAIT_TIME);
        UiObject2 synChineseObj = mDevice.findObject(bySelector);
        synChineseObj.clickAndWait(Until.newWindow(), WAIT_TIME);
        mDevice.wait(Until.hasObject(By.res(OneSearchDark.PACKAGE, "btn_start_one_search")), WAIT_TIME);
        mDevice.takeScreenshot(new File("/sdcard/performance-test/" + mNumber + "/" + mNumber + ".png"));
        FileInputStream source_fis = new FileInputStream("/sdcard/performance-test/" + mNumber + "/" + mNumber + ".png");
        Bitmap source_png = BitmapFactory.decodeStream(source_fis);
        mDevice.pressHome();
        clearRunprocess();
        return source_png;
    }

}

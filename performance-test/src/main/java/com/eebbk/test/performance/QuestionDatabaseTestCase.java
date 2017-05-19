package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.BitmapHelper;
import com.eebbk.test.common.PackageConstants.QuestionDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;

import static android.os.SystemClock.sleep;

@RunWith(AndroidJUnit4.class)
public class QuestionDatabaseTestCase extends PerforTestCase {
    @Test
    public void launchQuestionDatabase() throws IOException, UiObjectNotFoundException, InterruptedException,
            JSONException {
        JSONObject obj = new JSONObject();
        BySelector byQuestionDb = By.text("好题精练");
        Bitmap source_png = getHomeSourceScreen(byQuestionDb, QuestionDatabase.PACKAGE,
                "exercise_main_default_banner", 5000);
        for (int i = 0; i < mCount; i++) {
            String startScreen = "";
            String endScreen = "";
            String compareTime = "";
            int compareResult = 10;
            Date timeStamp1 = new Date();

            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(byQuestionDb), WAIT_TIME);
            UiObject2 questionDb = mDevice.findObject(byQuestionDb);
            startTestRecord();
            questionDb.clickAndWait(Until.newWindow(), WAIT_TIME);
            sleep(1000);
            int m = 0;
            do {
                startScreen = getCurrentDate();
                Bitmap des_png = mAutomation.takeScreenshot();
                endScreen = getCurrentDate();
                compareResult = BitmapHelper.compare(source_png, des_png);
                compareTime = getCurrentDate();
                obj.put("compareResult:" + String.valueOf(m), compareResult);
                if ((new Date().getTime() - timeStamp1.getTime()) > WAIT_TIME * 4) {
                    obj.put("break========:" + String.valueOf(m), compareResult);
                    break;
                }
            } while (compareResult >= 10);
            String loadTime = getCurrentDate();
            instrumentationStatusOut(obj);
            mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "exercise_main_default_banner")), WAIT_TIME);
            sleep(3000);
            stopTestRecord(loadTime, startScreen, endScreen, compareTime, String.valueOf(compareResult));
            mDevice.pressHome();
            clearRunprocess();
        }
    }
}

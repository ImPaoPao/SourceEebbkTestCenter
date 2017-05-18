package com.eebbk.test.performance;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.PackageConstants.QuestionDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class QuestionDatabaseTestCase extends PerforTestCase {
    @Test
    public void launchQuestionDatabase() throws IOException, UiObjectNotFoundException {
        //mType 0:冷启动
        BySelector bySynEng = By.text("好题精练");
        for (int i = 0; i < mCount; i++) {
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySynEng), WAIT_TIME);
            UiObject2 synMath = mDevice.findObject(bySynEng);
            startTestRecord();
            synMath.clickAndWait(Until.newWindow(), WAIT_TIME);
            mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "exercise_main_default_banner")), WAIT_TIME);
            stopTestRecord();
            mDevice.pressHome();
            clearRunprocess();
        }
    }
}

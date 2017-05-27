package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.PackageConstants.Vtraining;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class VtrainingTestCase extends PerforTestCase {
    @Test
    public void launchVtraining() throws IOException, UiObjectNotFoundException, InterruptedException, JSONException {
        BySelector byVtrain = By.text("名师辅导班");
        Bitmap source_png = getHomeSourceScreen(byVtrain, Vtraining.PACKAGE, "my_plan_banner_scale_id", 10000);
        Rect refreshPngRect = new Rect(0,0,source_png.getWidth(),source_png.getHeight()-70);
        Rect loadPngRect = new Rect(0,source_png.getHeight()-70,source_png.getWidth(),source_png.getHeight());
        for (int i = 0; i < mCount; i++) {
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(byVtrain), WAIT_TIME);
            UiObject2 byVtraining = mDevice.findObject(byVtrain);
            startTestRecord();
            byVtraining.clickAndWait(Until.newWindow(), WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect,refreshPngRect, new Date());
            mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "my_plan_banner_scale_id")), WAIT_TIME);
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            mDevice.pressHome();
            clearRunprocess();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }
}

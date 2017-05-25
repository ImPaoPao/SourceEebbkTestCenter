package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.PackageConstants.OneSearchDark;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class OneSearchDarkTestCase extends PerforTestCase {
    @Test
    public void launchOneSearch() throws IOException, UiObjectNotFoundException, JSONException, InterruptedException {
        BySelector byOneSearch = By.text("一键搜");
        Bitmap source_png = getHomeSourceScreen(byOneSearch,OneSearchDark.PACKAGE,"btn_start_one_search",1000);
        Rect refreshPngRect = new Rect(0,0,source_png.getWidth(),source_png.getHeight()-100);
        Rect loadPngRect = new Rect(0,source_png.getHeight()-100,source_png.getWidth(),source_png.getHeight());
        for (int i = 0; i < mCount; i++) {
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(byOneSearch), WAIT_TIME);
            UiObject2 oneSearch = mDevice.findObject(byOneSearch);
            startTestRecord();
            oneSearch.clickAndWait(Until.newWindow(), WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            mDevice.wait(Until.hasObject(By.res(OneSearchDark.PACKAGE, "btn_start_one_search")), WAIT_TIME);
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            mDevice.pressHome();
            clearRunprocess();
        }
        if(!source_png.isRecycled()){
            source_png.recycle();
        }
    }
}

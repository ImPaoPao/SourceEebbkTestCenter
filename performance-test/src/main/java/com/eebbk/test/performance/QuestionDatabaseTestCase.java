package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.PackageConstants.QuestionDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static android.os.SystemClock.sleep;

@RunWith(AndroidJUnit4.class)
public class QuestionDatabaseTestCase extends PerforTestCase {
    @Test
    public void launchQuestionDatabase() throws IOException, UiObjectNotFoundException, InterruptedException,
            JSONException {
        JSONObject obj = new JSONObject();
        BySelector byQuestionDb = By.text("好题精练");
        Bitmap source_png = getHomeSourceScreen(byQuestionDb, QuestionDatabase.PACKAGE,
                "exercise_main_default_banner", 10000);
        Rect refreshPngRect = new Rect(0,0,source_png.getWidth(),source_png.getHeight()-60);
        Rect loadPngRect = new Rect(0,source_png.getHeight()-60,source_png.getWidth(),source_png.getHeight());
        for (int i = 0; i < mCount; i++) {
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(byQuestionDb), WAIT_TIME);
            UiObject2 questionDb = mDevice.findObject(byQuestionDb);
            startTestRecord();
            questionDb.clickAndWait(Until.newWindow(), WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "exercise_main_default_banner")), WAIT_TIME);
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            sleep(3000);
            mDevice.pressHome();
            clearRunprocess();
        }
        if(!source_png.isRecycled()){
            source_png.recycle();
        }
    }
    //点击智能练习目录→题目加载完成
    //点击例题讲解目录→题目加载完成
    //点击真题密卷科目→真题目录界面加载完成
    //点击真题目录界面目录→题目加载完成
    //有做题记录，点击做题概况→做题概况页面加载完成
    //有错题记录，点击错题本→错题界面加载完成
    //点击排行榜→排行榜页面加载完成
}

package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.SystemClock;
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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class OneSearchDarkTestCase extends PerforTestCase {
    @Test
    public void launchOneSearch() throws IOException, UiObjectNotFoundException, JSONException, InterruptedException {
        BySelector byOneSearch = By.text("一键搜");
        Bitmap source_png = getHomeSourceScreen(byOneSearch, OneSearchDark.PACKAGE, "btn_start_one_search", 5000);
        Rect refreshPngRect = new Rect(0, 0, source_png.getWidth(), source_png.getHeight() - 100);
        Rect loadPngRect = new Rect(0, source_png.getHeight() - 100, source_png.getWidth(), source_png.getHeight());
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
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }


    //com.eebbk.onesearchdark:id/btn_start_one_search 拍照按钮
    //com.eebbk.onesearchdark:id/btn_select_search_question 搜难题
    //搜题时间（点击拍照按钮→出现搜题结果）
    @Test
    public void showOneSearch() throws FileNotFoundException, JSONException {
        mHelper.openOnesearch();
        mDevice.waitForIdle();
        mDevice.wait(Until.hasObject(By.res(OneSearchDark.PACKAGE, "btn_select_search_question")), WAIT_TIME * 4);
        UiObject2 search = mDevice.findObject(By.res(OneSearchDark.PACKAGE, "btn_select_search_question"));
        search.click();
        UiObject2 camera = mDevice.findObject(By.res(OneSearchDark.PACKAGE, "btn_start_one_search"));
        camera.clickAndWait(Until.newWindow(), WAIT_TIME);
        //select_type_layout 菜单条：搜难题 查单词 翻译
        UiObject2 select = mDevice.findObject(By.res(OneSearchDark.PACKAGE, "select_type_layout"));
        Rect rt  = select.getVisibleBounds();
        //dark_hint_text_main 搜难题
        mDevice.wait(Until.gone(By.res(OneSearchDark.PACKAGE, "dark_hint_text_main")), WAIT_TIME * 4);
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        Rect loadPngRect = new Rect(0, 0, source_png.getWidth(), source_png.getHeight()-rt.height());
        Rect refreshPngRect = loadPngRect;
        mDevice.pressBack();
        for (int i = 0; i < mCount; i++) {
            mDevice.wait(Until.hasObject(By.res(OneSearchDark.PACKAGE, "btn_start_one_search")), WAIT_TIME);
            camera = mDevice.findObject(By.res(OneSearchDark.PACKAGE, "btn_start_one_search"));
            startTestRecord();
            camera.click();
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            mDevice.pressBack();
            mDevice.waitForIdle();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    //取单词时间（点击长按取词按钮→出现单词）
    public void showOneSearchSelectWords() {
        //com.eebbk.onesearchdark:id/btn_select_search_word 长按取词
    }

    //翻译时间（点击拍照按钮→出现翻译结果）
    @Test
    public void showOneSearchTranslating() throws FileNotFoundException, JSONException {
        //com.eebbk.onesearchdark:id/btn_select_translate_word 翻译单词
        mHelper.openOnesearch();
        mDevice.waitForIdle();
        mDevice.wait(Until.hasObject(By.res(OneSearchDark.PACKAGE, "btn_select_translate_word")), WAIT_TIME * 4);
        //select_type_layout 菜单条：搜难题 查单词 翻译
        UiObject2 select = mDevice.findObject(By.res(OneSearchDark.PACKAGE, "select_type_layout"));
        Rect rt  = select.getVisibleBounds();
        UiObject2 translate = mDevice.findObject(By.res(OneSearchDark.PACKAGE, "btn_select_translate_word"));
        translate.click();//点击翻译菜单
        //拍照
        UiObject2 camera = mDevice.findObject(By.res(OneSearchDark.PACKAGE, "btn_start_one_search"));
        camera.click();
        SystemClock.sleep(3000);
        camera.clickAndWait(Until.newWindow(),WAIT_TIME);
        mDevice.wait(Until.gone(By.res(OneSearchDark.PACKAGE, "restart_take_photo")), WAIT_TIME * 5);//拍照按钮消失
        //点击拍照后等待联网翻译 com.eebbk.onesearchdark:id/restart_take_photo
        mDevice.wait(Until.hasObject(By.res(OneSearchDark.PACKAGE, "layout_trans_have_result")), WAIT_TIME * 4);
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        Rect loadPngRect = new Rect(0, 0, source_png.getWidth(), source_png.getHeight()-rt.height());
        Rect refreshPngRect = loadPngRect;
        mDevice.pressBack();
        for (int i = 0; i < mCount; i++) {
            mDevice.wait(Until.hasObject(By.res(OneSearchDark.PACKAGE, "btn_start_one_search")), WAIT_TIME);
            camera = mDevice.findObject(By.res(OneSearchDark.PACKAGE, "btn_start_one_search"));
            camera.click();
            SystemClock.sleep(3000);
            startTestRecord();
            camera.click();
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            mDevice.waitForIdle();
            mDevice.pressBack();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }
}

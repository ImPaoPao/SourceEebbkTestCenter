package com.eebbk.test.performance;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.Until;

import com.eebbk.test.common.PackageConstants.EnglishTalk;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import static android.os.SystemClock.sleep;

@RunWith(AndroidJUnit4.class)
public class EnglishTalkTestCase extends PerforTestCase {

    @Test
    public void launchEnglishTalk() throws IOException, UiObjectNotFoundException, InterruptedException, JSONException {
        BySelector bySynEng = By.text("英语听说");
        Bitmap source_png = getHomeSourceScreen(bySynEng, EnglishTalk.PACKAGE, "tab_view_root_id", 10000);
        Rect refreshPngRect = new Rect(0, 0, source_png.getWidth(), source_png.getHeight() - 80);
        Rect loadPngRect = new Rect(0, source_png.getHeight() - 80, source_png.getWidth(), source_png.getHeight());
        for (int i = 0; i < mCount; i++) {
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(bySynEng), WAIT_TIME);
            UiObject2 synMath = mDevice.findObject(bySynEng);
            startTestRecord();
            synMath.clickAndWait(Until.newWindow(), WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            mDevice.wait(Until.hasObject(By.res(EnglishTalk.PACKAGE, "homepage_banner_view_layout_id")), WAIT_TIME);
            sleep(3000);
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            mDevice.pressHome();
            clearRunprocess();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    //打开英语听说界面后 com.eebbk.englishtalk:id/main_mp3_play 暂停播放
    //item_join_book_child_info_layout_id 书列表

    //选择教材添加后，点击教材目录→进入播放界面

    //播放界面，点击右下角目录按钮→目录加载完成


    //播放界面，点击目录按钮中任意目录→播放内容加载完成
    @Test
    public void showPlayAudio() throws IOException, UiObjectNotFoundException, InterruptedException, JSONException {
        //com.eebbk.englishtalk:id/item_book_menu_child_root_id 目录条目
        //播放界面dump不下来
        mHelper.openEnglishTalk();
        //mDevice.wait(Until.hasObject(By.res(EnglishTalk.PACKAGE,"item_join_book_child_info_layout_id")),WAIT_TIME);
        UiObject2 bookChild = mDevice.findObject(By.res(EnglishTalk.PACKAGE,
                "item_join_book_child_info_layout_id"));
        if(bookChild!=null){
            bookChild.click();
        }
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        Rect refreshPngRect = new Rect(0, 0, source_png.getWidth(), source_png.getHeight() - 80);
        Rect loadPngRect = new Rect(0, source_png.getHeight() - 80, source_png.getWidth(), source_png.getHeight());
    }
    //点击banner口语达人→界面内容加载完成
    @Test
    public void showSpokenEnglish() throws IOException, UiObjectNotFoundException, InterruptedException, JSONException {

    }


    //点击我的排行→排行榜加载完成
    @Test
    public void showRanking() throws IOException, UiObjectNotFoundException, InterruptedException, JSONException {
        //com.eebbk.englishtalk:id/homepage_func_rank_id
    }

}

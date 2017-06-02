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

import com.eebbk.test.common.PackageConstants;
import com.eebbk.test.common.PackageConstants.QuestionDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static android.os.SystemClock.sleep;

@RunWith(AndroidJUnit4.class)
public class QuestionDatabaseTestCase extends PerforTestCase {
//    @Test
    public void launchQuestionDatabase() throws IOException, UiObjectNotFoundException, InterruptedException,
            JSONException {
        BySelector byQuestionDb = By.text("好题精练");
        Bitmap source_png = getHomeSourceScreen(byQuestionDb, QuestionDatabase.PACKAGE,
                "exercise_main_default_banner", 10000);
        Rect refreshPngRect = new Rect(0, 0, source_png.getWidth(), source_png.getHeight() - 60);
        Rect loadPngRect = new Rect(0, source_png.getHeight() - 60, source_png.getWidth(), source_png.getHeight());
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
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    /**
     * com.eebbk.questiondatabase:id/home_img_tab_exercise 智能练习
     * com.eebbk.questiondatabase:id/home_img_tab_select_exercises 例题精讲
     * com.eebbk.questiondatabase:id/home_img_tab_paper 真题密卷
     * com.eebbk.questiondatabase:id/home_img_tab_mine 我
     * com.eebbk.questiondatabase:id/exercise_main_rank 我的排行
     */
    //点击智能练习目录→题目加载完成
    //com.eebbk.questiondatabase:id/exercise_text_title 题目小节标题 点击显示详细题目
    //@Test
    public void showQdExample() throws IOException, JSONException {
        openQd();
        mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "exercise_text_title")), WAIT_TIME * 4);
        UiObject2 exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "exercise_text_title"));
        exam.clickAndWait(Until.newWindow(), WAIT_TIME);
        Rect loadPngRect = new Rect(80,20,160,60);
        JSONObject obj = new JSONObject();
        obj.put("======","00000000000000");
        obj.put("left",loadPngRect.left);
        obj.put("top",loadPngRect.top);
        obj.put("right",loadPngRect.right);
        obj.put("bottom",loadPngRect.bottom);
        mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE,"exercise_webview")), WAIT_TIME * 4);
        SystemClock.sleep(10000);
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        Rect refreshPngRect = new Rect(0,0,source_png.getWidth(),source_png.getHeight()/6);
        obj.put("left1",refreshPngRect.left);
        obj.put("top1",refreshPngRect.top);
        obj.put("right1",refreshPngRect.right);
        obj.put("bottom1",refreshPngRect.bottom);
        instrumentationStatusOut(obj);
        clearRunprocess();
        for (int i = 0; i < mCount; i++) {
            openQd();
            mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "exercise_text_title")), WAIT_TIME * 2);
            exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "exercise_text_title"));
            startTestRecord();
            exam.clickAndWait(Until.newWindow(),WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            SystemClock.sleep(1000);
            mDevice.pressBack();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    //点击例题讲解目录→题目加载完成
    //@Test
    public void showQdExamExplanation() throws IOException, JSONException {
        openQd("home_img_tab_select_exercises");
        mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "select_subject_current_chapter_layout")),
                WAIT_TIME * 4);
        SystemClock.sleep(5000);
        UiObject2 exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "select_exercise_view_pager"));
        Rect refreshPngRect = exam.getVisibleBounds();
        exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "select_exercise_filter_view"));
        Rect loadPngRect = exam.getVisibleBounds();
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        clearRunprocess();
        for (int i = 0; i < mCount; i++) {
            openQd();
            mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "home_img_tab_select_exercises")), WAIT_TIME * 2);
            exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "home_img_tab_select_exercises"));
            startTestRecord();
            exam.click();
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            SystemClock.sleep(1000);
            mDevice.pressBack();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    //点击真题密卷科目→真题目录界面加载完成
    //@Test
    public void showQdRealExamList() throws IOException, JSONException {
        openQd("home_img_tab_paper");
        //paper_main_item_grid_subject 真题 gradview
        mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "paper_main_item_grid_subject")),
                WAIT_TIME * 4);
        SystemClock.sleep(5000);
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        //home_viewpager_content  整个界面
        UiObject2 exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "paper_main_search_layout"));//title
        Rect rt = exam.getVisibleBounds();
        Rect loadPngRect = new Rect(rt.width()/4,rt.top,rt.width()*3/4,rt.bottom);
        exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "home_viewpager_content"));
        rt = exam.getVisibleBounds();
        exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "home_linear_tab_container"));
        Rect refreshPngRect =new Rect(rt.left,rt.top,rt.right,rt.bottom- exam.getVisibleBounds().height());
        clearRunprocess();
        for (int i = 0; i < mCount; i++) {
            openQd();
            mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "home_img_tab_paper")), WAIT_TIME * 2);
            exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "home_img_tab_paper"));
            startTestRecord();
            exam.click();
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            SystemClock.sleep(1000);
            mDevice.pressBack();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    //点击真题目录界面目录→题目加载完成
    @Test
    public void showQdRealExamContent() throws IOException, JSONException {
        openQd("home_img_tab_paper");
        mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "paper_main_item_grid_subject")),
                WAIT_TIME * 4);
        UiObject2 exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "paper_main_item_grid_subject"));
        List<UiObject2> children = exam.getChildren();
        exam = children.get(0);
        exam.clickAndWait(Until.newWindow(),WAIT_TIME);
        //paper_normal_item_text_paper_title
        //paper_normal_item_text_paper_title
        mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "paper_normal_item_text_paper_title")),
                WAIT_TIME * 4);
        exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "paper_normal_item_text_paper_title"));
        exam.clickAndWait(Until.newWindow(),WAIT_TIME);
        SystemClock.sleep(20000);
        //试卷 paper_webview
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        Rect refreshPngRect = new Rect(0,0,source_png.getWidth(),source_png.getHeight());
        Rect loadPngRect = new Rect(source_png.getWidth()/8,20,source_png.getWidth()/3,80) ;
        clearRunprocess();
        for (int i = 0; i < mCount; i++) {
            openQd("home_img_tab_paper");
            mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "paper_main_item_grid_subject")),
                    WAIT_TIME * 4);
            exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "paper_main_item_grid_subject"));
            if(exam!=null){
                children = exam.getChildren();
                exam = children.get(0);
                exam.clickAndWait(Until.newWindow(),WAIT_TIME);
            }
            mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "paper_normal_item_text_paper_title")),
                    WAIT_TIME * 4);
            exam = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "paper_normal_item_text_paper_title"));
            startTestRecord();
            exam.click();
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            SystemClock.sleep(1000);
            mDevice.pressBack();
            mDevice.wait(Until.hasObject(By.text("确定")),WAIT_TIME);
            exam = mDevice.findObject(By.text("确定"));
            exam.click();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }




    }

    //点击排行榜→排行榜页面加载完成
    //@Test
    public void showQdRanking() throws IOException, JSONException {
        openQd();
        mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "exercise_main_rank")), WAIT_TIME * 4);
        UiObject2 rank = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "exercise_main_rank"));
        rank.clickAndWait(Until.newWindow(), WAIT_TIME);
        mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "rank")), WAIT_TIME * 6);
        rank = mDevice.findObject(By.res(PackageConstants.Android.PACKAGE, "list"));
        Rect refreshPngRect = rank.getVisibleBounds();//list 包含了整个排行榜页面
//        rank = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "week_rank_back_id"));上方的标题
        Rect loadPngRect = new Rect(refreshPngRect.left,refreshPngRect.top,refreshPngRect.right,refreshPngRect
                .bottom/2);
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        clearRunprocess();
        for (int i = 0; i < mCount; i++) {
            openQd();
            mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "exercise_main_rank")), WAIT_TIME * 2);
            rank = mDevice.findObject(By.res(QuestionDatabase.PACKAGE, "exercise_main_rank"));
            startTestRecord();
            rank.clickAndWait(Until.newWindow(),WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
            stopTestRecord(compareResult.get("loadTime"), compareResult.get("refreshTime"), compareResult.get
                    ("loadResult"), compareResult.get("refreshResult"));
            SystemClock.sleep(1000);
            mDevice.pressBack();
        }
        if (!source_png.isRecycled()) {
            source_png.recycle();
        }
    }

    private void openQd() {
        mHelper.openQuestionDatabse();
        //mDevice.wait(Until.hasObject(By.res(PackageConstants.Android.PACKAGE, "list")), WAIT_TIME * 2);
        mDevice.wait(Until.hasObject(By.res(QuestionDatabase.PACKAGE, "exercise_text_title")), WAIT_TIME * 4);
        mDevice.waitForIdle();
    }

    private void openQd(String menuId) {
        openQd();
        BySelector byMenu = By.res(QuestionDatabase.PACKAGE, menuId);
        mDevice.wait(Until.hasObject(byMenu), WAIT_TIME);
        UiObject2 click = mDevice.findObject(byMenu);
        if(click!=null){
            click.click();
            mDevice.waitForIdle();
        }
    }
    //有做题记录，点击做题概况→做题概况页面加载完成
    //有错题记录，点击错题本→错题界面加载完成
}
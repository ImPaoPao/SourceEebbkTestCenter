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
import com.eebbk.test.common.PackageConstants.Vtraining;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@RunWith(AndroidJUnit4.class)
public class VtrainingTestCase extends PerforTestCase {
    @Test
    public void launchVtraining() throws IOException, UiObjectNotFoundException, InterruptedException, JSONException {
        BySelector byVtrain = By.text("名师辅导班");
        Bitmap source_png = getHomeSourceScreen(byVtrain, Vtraining.PACKAGE, "my_plan_banner_scale_id", 20000);
        Rect refreshPngRect = new Rect(0, 0, source_png.getWidth(), source_png.getHeight() - 70);
        Rect loadPngRect = new Rect(0, source_png.getHeight() - 70, source_png.getWidth(), source_png.getHeight());
        for (int i = 0; i < mCount; i++) {
            swipeCurrentLauncher();
            mDevice.wait(Until.hasObject(byVtrain), WAIT_TIME);
            UiObject2 byVtraining = mDevice.findObject(byVtrain);
            startTestRecord();
            byVtraining.clickAndWait(Until.newWindow(), WAIT_TIME);
            Map<String, String> compareResult = doCompare(source_png, loadPngRect, refreshPngRect, new Date());
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

    //点击首页更多精彩→课程列表页面，加载完成
    @Test
    public void showVtMoreList() throws FileNotFoundException, JSONException {
        mHelper.openVtraining();
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "mainpage_introduce_more_layout")), WAIT_TIME * 4);
        UiObject2 more = mDevice.findObject(By.res(Vtraining.PACKAGE, "mainpage_introduce_more_layout"));
        more.clickAndWait(Until.newWindow(), WAIT_TIME);
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "homerecommended_more_recyclerview")), WAIT_TIME * 4);
        more = mDevice.findObject(By.res(Vtraining.PACKAGE, "homerecommended_more_title_bar_id"));
        Rect loadPngRect = more.getVisibleBounds();
        more = mDevice.findObject(By.res(Vtraining.PACKAGE, "homerecommended_more_recyclerview"));
        Rect refreshPngRect = more.getVisibleBounds();
        SystemClock.sleep(5000);
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        mDevice.pressBack();
        for (int i = 0; i < mCount; i++) {
            mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "mainpage_introduce_more_layout")), WAIT_TIME * 4);
            more = mDevice.findObject(By.res(Vtraining.PACKAGE, "mainpage_introduce_more_layout"));
            startTestRecord();
            more.click();
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

    //点击选课→科目→课程包封面，加载完成
    @Test
    public void showVtCourse() throws JSONException, FileNotFoundException {
        //home_tab_view 下面的menu child 0 1 2
        openVt("选课");
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "select_course_class_type_view")), WAIT_TIME * 4);
        UiObject2 course = mDevice.findObject(By.res(Vtraining.PACKAGE, "select_course_class_type_view"));
        course.clickAndWait(Until.newWindow(), WAIT_TIME);
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "PullToRefreshRecyclerView_recyclerview")), WAIT_TIME
                * 4);
        course = mDevice.findObject(By.res(Vtraining.PACKAGE, "course_package_title_bar_id"));
        Rect rt = course.getVisibleBounds();
        course = mDevice.findObject(By.res(Vtraining.PACKAGE, "PullToRefreshRecyclerView_recyclerview"));
        Rect rtd = course.getVisibleBounds();
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        Rect refreshPngRect = new Rect(rtd.left, rtd.top, rtd.right, rtd.bottom);
        Rect loadPngRect = new Rect(rt.left, rt.top, rt.right, rt.bottom);
        mDevice.pressBack();
        for (int i = 0; i < mCount; i++) {
            mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "select_course_class_type_view")), WAIT_TIME * 2);
            course = mDevice.findObject(By.res(Vtraining.PACKAGE, "select_course_class_type_view"));
            startTestRecord();
            course.click();
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

    //选课界面，点击banner图名师在这里图片→名师页面加载完成
    @Test
    public void showVtCourseTeacher() throws FileNotFoundException, JSONException {
        //select_course_teacher 选课 最上方的图 名师在这里->左上角。
        openVt("选课");
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "select_course_teacher")), WAIT_TIME);
        UiObject2 course = mDevice.findObject(By.res(Vtraining.PACKAGE, "select_course_teacher"));
        Rect crt = course.getVisibleBounds();
        mHelper.longClick(crt.right / 10, crt.top + 25);
        //course.clickAndWait(Until.newWindow(), WAIT_TIME);
        //名师头像刷新出来
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "item_famousteacher_img_head_1")), WAIT_TIME * 6);
        //切换时  上方 选课变为名师 表示页面切换，上方的横向标题栏有变化 famous_teacher_indicator
        //course = mDevice.findObject(By.res(Vtraining.PACKAGE, "famous_teacher_view1"));
        course = mDevice.findObject(By.res(Vtraining.PACKAGE, "famous_teacher_indicator"));
        Rect rt = course.getVisibleBounds();
        //下方名师信息view
        course = mDevice.findObject(By.res(Vtraining.PACKAGE,
                "famous_teacher_studyphase_pulltoRefreshRecyclerView"));
        Rect rtd = course.getVisibleBounds();
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        Rect refreshPngRect = new Rect(rtd.left, rtd.top, rtd.right, rtd.bottom);
        Rect loadPngRect = new Rect(0, 0, rt.right, rt.bottom);
        mDevice.pressBack();
        for (int i = 0; i < mCount; i++) {
            mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "select_course_teacher")), WAIT_TIME);
            startTestRecord();
            mDevice.click(crt.right / 10, crt.top + 25);
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


    //点击名师头像→名师详情加载完成
    @Test
    public void showVtTeacherInfo() throws FileNotFoundException, JSONException {
        //item_famousteacher_img_head_1 名师头像
        //select_course_teacher 选课 最上方的图 名师在这里->左上角。
        openVt("选课");
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "select_course_teacher")), WAIT_TIME);
        UiObject2 course = mDevice.findObject(By.res(Vtraining.PACKAGE, "select_course_teacher"));
        Rect crt = course.getVisibleBounds();
        mHelper.longClick(crt.right / 10, crt.top + 25); //名师在这里
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "item_famousteacher_img_head_1")), WAIT_TIME * 6);
        course = mDevice.findObject(By.res(Vtraining.PACKAGE, "item_famousteacher_img_head_1"));
        course.clickAndWait(Until.newWindow(), WAIT_TIME);
        //teacher_info_title_name  名师详情标题
        //上方带视频图片 teacher_area_id 下方描述介绍文字：teacher_introduce_txt 同时刷新出现
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "teacher_area_id")), WAIT_TIME);
        //course = mDevice.findObject(By.res(Vtraining.PACKAGE, "teacher_info_title"));
        //Rect rt = course.getVisibleBounds();
        course = mDevice.findObject(By.res(Vtraining.PACKAGE, "teacher_area_id"));
        Rect rtd = course.getVisibleBounds();
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        Rect loadPngRect = new Rect(rtd.left, rtd.top, rtd.right, rtd.bottom);
        Rect refreshPngRect = loadPngRect;
        mDevice.pressBack();
        for (int i = 0; i < mCount; i++) {
            mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "item_famousteacher_img_head_1")), WAIT_TIME);
            course = mDevice.findObject(By.res(Vtraining.PACKAGE, "item_famousteacher_img_head_1"));
            startTestRecord();
            course.click();
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

    //我的界面，点击排行榜→排行榜页面，加载完成
    @Test
    public void showVtRanking() throws JSONException, FileNotFoundException {
        openVt("我的");
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "mine_rank_layout")), WAIT_TIME * 6);
        UiObject2 rank = mDevice.findObject(By.res(Vtraining.PACKAGE, "mine_rank_layout"));
        rank.clickAndWait(Until.newWindow(), WAIT_TIME);
        //排行列表刷新出来
        mDevice.wait(Until.hasObject(By.res(PackageConstants.Android.PACKAGE, "list")), WAIT_TIME * 6);
        rank = mDevice.findObject(By.res(PackageConstants.Android.PACKAGE, "list"));
        Rect rtd = rank.getVisibleBounds();//list 包含了整个排行榜页面 可取下半部分刷新界面
        // 排行榜上方 一半是固定的，页面切换即显示，另一半要下面刷新完成重新刷新
        rank = mDevice.findObject(By.res(Vtraining.PACKAGE, "item_week_rank_transparent_view_id"));
        Rect rt = rank.getVisibleBounds();
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        Rect refreshPngRect = new Rect(rtd.left, rtd.bottom / 2, rtd.right, rtd.bottom);
        Rect loadPngRect = new Rect(rt.left, rt.bottom / 2, rt.right, rt.bottom);
        mDevice.pressBack();
        for (int i = 0; i < mCount; i++) {
            mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "mine_rank_layout")), WAIT_TIME);
            rank = mDevice.findObject(By.res(Vtraining.PACKAGE, "mine_rank_layout"));
            startTestRecord();
            rank.click();
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


    //已加入5个课程，进入我的界面，点击已加入课程→列表内容加载完成
    @Test
    public void showVtJoinCourse() throws JSONException, FileNotFoundException {
        //mine_setting_layout 列表菜单
        BySelector by = By.text("已加入课程");
        openVt("我的");
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "mine_setting_layout")), WAIT_TIME);
        UiObject2 join = mDevice.findObject(by);
        join.clickAndWait(Until.newWindow(), WAIT_TIME);
        if (mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "my_plan_no_plan_tip_id")), WAIT_TIME)) {
            join = mDevice.findObject(By.res(Vtraining.PACKAGE, "my_plan_no_plan_tip_id"));
        } else if (mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "title_bar_btn_edit")), WAIT_TIME * 4)) {
            join = mDevice.findObject(By.res(PackageConstants.Android.PACKAGE, "list"));
        }
        Rect refreshPngRect = join.getVisibleBounds();
        join = mDevice.findObject(By.res(Vtraining.PACKAGE, "title_bar_title_name"));
        Rect loadPngRect = join.getVisibleBounds();
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        mDevice.pressBack();
        for (int i = 0; i < mCount; i++) {
            mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "mine_setting_layout")), WAIT_TIME);
            join = mDevice.findObject(by);
            startTestRecord();
            join.click();
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


    //已下载5个课程，进入我的界面，点击已下载课程→列表内容加载完成
    @Test
    public void showVtDownloadCourse() throws FileNotFoundException, JSONException {
        BySelector by = By.text("我的下载");
        openVt("我的");
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "mine_setting_layout")), WAIT_TIME);
        UiObject2 download = mDevice.findObject(by);
        download.clickAndWait(Until.newWindow(), WAIT_TIME);
        if (mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "tips_no_download_layout")), WAIT_TIME)) {
            download = mDevice.findObject(By.res(Vtraining.PACKAGE, "tips_no_download_layout"));
        } else if (mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "title_bar_btn_edit")), WAIT_TIME * 2)) {
            download = mDevice.findObject(By.res(PackageConstants.Android.PACKAGE, "list"));
        }
        Rect refreshPngRect = download.getVisibleBounds();
        download = mDevice.findObject(By.res(Vtraining.PACKAGE, "title_bar_title_name"));
        Rect loadPngRect = download.getVisibleBounds();
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        mDevice.pressBack();
        for (int i = 0; i < mCount; i++) {
            mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "mine_setting_layout")), WAIT_TIME * 2);
            download = mDevice.findObject(by);
            startTestRecord();
            download.click();
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

    //点击课程包封面→视频播放界面加载完成
    @Test
    public void showVtCourseVideo() {
    }

    //点击首页视频缩略图→视频播放加载完成
    @Test
    public void showVtVideo() throws FileNotFoundException, JSONException {
        mHelper.openVtraining();
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "mainpage_introduce_item_image")), WAIT_TIME * 4);
        UiObject2 more = mDevice.findObject(By.res(Vtraining.PACKAGE, "mainpage_introduce_item_image"));
        more.clickAndWait(Until.newWindow(), WAIT_TIME);
        mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "videoview")), WAIT_TIME * 6);
        SystemClock.sleep(10000);
        more = mDevice.findObject(By.res(Vtraining.PACKAGE, "tab_view_root_id"));
        Rect loadPngRect = more.getVisibleBounds();
        more = mDevice.findObject(By.res(Vtraining.PACKAGE, "videoview"));
        Rect refreshPngRect = more.getVisibleBounds();
//        SystemClock.sleep(5000);
        Bitmap source_png = mHelper.takeScreenshot(mNumber);
        SystemClock.sleep(2000);
        mDevice.pressBack();
        for (int i = 0; i < mCount; i++) {
            mDevice.wait(Until.hasObject(By.res(Vtraining.PACKAGE, "mainpage_introduce_item_image")), WAIT_TIME * 4);
            more = mDevice.findObject(By.res(Vtraining.PACKAGE, "mainpage_introduce_item_image"));
            startTestRecord();
            more.click();
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

    private void openVt(String menu) {
        BySelector byMenu = By.text(menu);
        mHelper.openVtraining();
        mDevice.wait(Until.hasObject(byMenu), WAIT_TIME);
        UiObject2 click = mDevice.findObject(byMenu);
        click.click();
        mDevice.waitForIdle();
    }
}

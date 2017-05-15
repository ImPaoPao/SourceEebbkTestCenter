package com.eebbk.test.performance;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.util.Log;

import com.eebbk.test.common.PackageConstants.DeskClock;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class DeskClockTestCase extends PerforTestCase {
    //前置条件：新建10个闹钟，提前导入五首音乐
    private void openDesckClock() throws IOException {
        //am instrument -r -e class ${package}.${class}\#${method} -e count ${count} -e number ${number}
        // -w com.eebbk.uiauto2demo.test/android.support.test.runner.AndroidJUnitRunner
        // > ${workout}/${number}/runlog.txt
        //启动方式
        // 1.intent package
//        Context context = InstrumentationRegistry.getInstrumentation().getContext();
//        Intent intent = context.getPackageManager().getLaunchIntentForPackage(DeskClock.PACKAGE);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        context.startActivity(intent);
        //2.am start
        mDevice.executeShellCommand("pm clear " + DeskClock.PACKAGE);
        mDevice.executeShellCommand("am start -W com.eebbk.deskclock/.DeskClockJunior");
        mDevice.waitForIdle();
        //3.resourcce icon
        //............
    }

    //新建一个闹钟
    @Test
    public void createClock() throws IOException {
        Log.i(TAG, "create a clock");
        for (int i = 0; i < 4; i++) {
            openDesckClock();
            UiObject2 create_clcok = mDevice.findObject(By.res(DeskClock.PACKAGE, "desk_clock_btn_add_alarm"));
            assertThat("没有找到新建闹钟按钮", create_clcok, notNullValue());
            //记录开始时间
            startTestRecord();
            create_clcok.clickAndWait(Until.newWindow(), WAIT_TIME);
            //1.选择记录页面核心控件
            UiObject2 save_clock = mDevice.findObject(By.res(DeskClock.PACKAGE, "nav_bar_right_tv"));
            if (save_clock !=null) {
                stopTestRecord();
            }
            //完全等待程序加载完成：记录结束时间
            //mDevice.waitForIdle();
            //stopTestRecord();
            stopTestRecord();
        }
    }

    //点击铃声到铃声页面加载出来
    @Test
    public void selectRingtone() throws IOException {
        Log.i(TAG, "select inner clock ringtone list ");
    }

    //编辑已有闹钟
    @Test
    public void editClock() throws IOException {
        Log.i(TAG, "edit an existed clock");
    }
}

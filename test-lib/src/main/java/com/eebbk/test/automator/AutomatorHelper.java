package com.eebbk.test.automator;

import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

public class AutomatorHelper {

    private UiDevice mDevice;

    public AutomatorHelper(UiDevice device) {
        mDevice = device;
    }

    public boolean sleep() {
        try {
            mDevice.sleep();
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean wakeUp() {
        try {
            mDevice.wakeUp();
            return true;
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void lock() {
        sleep();
        wakeUp();
        SystemClock.sleep(1500);
    }

    public void unlock() {
        wakeUp();
        //解锁
    }

    public void openLauncher() {
        unlock();
        mDevice.pressHome();
        mDevice.waitForIdle();
        BySelector selector = By.res(Automator.PATTERN_BUTTON1);
        UiObject2 ok = mDevice.findObject(selector);
        if (ok != null) {
            ok.click();
            mDevice.wait(Until.gone(selector), Automator.WAIT_TIME);
        }

        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat("没有安装启动器", launcherPackage, notNullValue());
        assertThat("返回主界面失败",
                mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), Automator.WAIT_TIME), is(true));
    }
}

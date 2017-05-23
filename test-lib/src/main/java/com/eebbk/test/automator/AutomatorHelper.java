package com.eebbk.test.automator;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.text.TextUtils;
import android.widget.TextView;

import com.eebbk.test.common.PackageConstants.BbkMiddleMarket;
import com.eebbk.test.common.PackageConstants.EnglishTalk;
import com.eebbk.test.common.PackageConstants.Launcher;
import com.eebbk.test.common.PackageConstants.OneSearchDark;
import com.eebbk.test.common.PackageConstants.QuestionDatabase;
import com.eebbk.test.common.PackageConstants.SynChinese;
import com.eebbk.test.common.PackageConstants.SynMath;
import com.eebbk.test.common.PackageConstants.SyncEnglish;
import com.eebbk.test.common.PackageConstants.Vision;
import com.eebbk.test.common.PackageConstants.Vtraining;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static com.eebbk.test.automator.Automator.WAIT_TIME;
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
            mDevice.wait(Until.gone(selector), WAIT_TIME);
        }

        final String launcherPackage = mDevice.getLauncherPackageName();
        assertThat("没有安装启动器", launcherPackage, notNullValue());
        assertThat("返回主界面失败",
                mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), WAIT_TIME), is(true));
    }

    public void longClick(int x, int y) {
        mDevice.drag(x, y, x, y, 5);
    }

    public void longClick(UiObject obj) {
        Rect r = null;
        try {
            r = obj.getVisibleBounds();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        if (r != null) {
            longClick(r.centerX(), r.centerY());
        }
    }

    public void longClick(UiObject2 obj) {
        Point p = obj.getVisibleCenter();
        longClick(p.x, p.y);
    }

    public Bitmap getBitmap(Rect r) {
        Bitmap source = getInstrumentation().getUiAutomation().takeScreenshot();
        return Bitmap.createBitmap(source, r.left, r.top, r.width(), r.height());
    }

    public Bitmap getBitmap(UiObject obj) {
        Bitmap target = null;
        try {
            target = getBitmap(obj.getVisibleBounds());
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
        return target;
    }

    public Bitmap getBitmap(UiObject2 obj) {
        return getBitmap(obj.getVisibleBounds());
    }

    public boolean takeScreenshot(Rect r, File storePath, int quality) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(storePath);
        } catch (IOException e) {
            // Nothing to do
        }
        if (out != null) {
            Bitmap source = getInstrumentation().getUiAutomation().takeScreenshot();
            Bitmap bitmap = Bitmap.createBitmap(source, r.left, r.top, r.width(), r.height());
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
            return true;
        }
        return false;
    }

    public boolean takeScreenshot(UiObject obj, File storePath, int quality) {
        Rect r = null;
        try {
            r = obj.getVisibleBounds();
        } catch (UiObjectNotFoundException e) {
            // Nothing to do
        }
        return r != null && takeScreenshot(r, storePath, quality);
    }

    public boolean takeScreenshot(UiObject2 obj, File storePath, int quality) {
        return takeScreenshot(obj.getVisibleBounds(), storePath, quality);
    }

    public Object findIcon(String title) {
        openLauncher();
        mDevice.waitForIdle();
        UiObject2 label = mDevice.findObject(By.text(title));
        if (label == null) {
            UiObject2 indicator = mDevice.findObject(By.res(Launcher.PACKAGE, "paged_view_indicator"));
            if (indicator != null) {
                int flag = 0;
                List<UiObject2> children = indicator.getChildren();
                for (int i = children.size() - 1; i > 0; i--) {
                    if (children.get(i).isChecked()) {
                        flag = i;
                    }
                }
                if (flag <= children.size() / 2) {
                    for (int j = 0; j < flag; j++) {
                        SystemClock.sleep(1500);
                        label = mDevice.findObject(By.text(title));
                        if (label != null) {
                            return label;
                        }
                        mDevice.swipe(mDevice.getDisplayWidth() / 2, mDevice.getDisplayHeight() / 2, 0, mDevice
                                .getDisplayHeight() / 2, 20);

                    }
                } else {
                    for (int j = 0; j < flag; j++) {
                        SystemClock.sleep(1500);
                        label = mDevice.findObject(By.text(title));
                        if (label != null) {
                            return label;
                        }
                        mDevice.swipe(mDevice.getDisplayWidth() / 2, mDevice.getDisplayHeight() / 2, mDevice
                                .getDisplayWidth(), mDevice.getDisplayHeight() / 2, 20);

                    }

                }
            }
        }
        return label;
    }

    public Object findIcon(String folder, String title) {
        openFolder(folder);
        mDevice.waitForIdle();
        UiObject2 label = mDevice.findObject(By.text(title));
        if (label == null) {
            UiScrollable icons = new UiScrollable(
                    new UiSelector().resourceId(String.format("%s:id/scroll_view", Launcher.PACKAGE)));
            UiObject icon = null;
            try {
                icon = icons.getChildByText(new UiSelector().className(TextView.class), title);
            } catch (UiObjectNotFoundException e) {
                // Nothing to do
            }
            return icon;
        }
        return label;
    }

    public void openIcon(String folder, String title, String packageName, boolean forceStop) {
        if (forceStop) {
            try {
                mDevice.executeShellCommand(String.format("am force-stop %s", packageName));
            } catch (IOException e) {
                // Nothing to do
            }
        }
        Object icon = folder != null ? findIcon(folder, title) : findIcon(title);
        assertThat(String.format("没有找到图标%s", title), icon, notNullValue());
        if (icon instanceof UiObject2) {
            ((UiObject2) icon).clickAndWait(Until.newWindow(), WAIT_TIME);
        } else {
            try {
                ((UiObject) icon).clickAndWaitForNewWindow();
            } catch (UiObjectNotFoundException e) {
                // Nothing to do
            }
        }
        if (!TextUtils.isEmpty(packageName)) {
            UiObject2 validate = mDevice.wait(Until.findObject(By.pkg(packageName).depth(0)), WAIT_TIME);
            assertThat(String.format("启动%s失败", title), validate, notNullValue());
        }
    }

    public void openIcon(String title, String packageName, boolean forceStop) {
        openIcon(null, title, packageName, forceStop);
    }

    public void openIcon(String title, String packageName) {
        openIcon(title, packageName, false);
    }

    public void openFolder(String title) {
        openIcon(title, null);
    }

    public void openSyncEnglish() {
        openIcon("同步英语", SyncEnglish.PACKAGE);
    }

    public void openSynChinese() {
        openIcon("同步语文", SynChinese.PACKAGE);
    }

    public void openSynMath() {
        openIcon("同步数学", SynMath.PACKAGE);
    }

    public void openVtraining() {
        openIcon("名师辅导", Vtraining.PACKAGE);
    }

    public void openVision() {
        openIcon("视力保护", Vision.PACKAGE);
    }

    public void openQuestionDatabse() {
        openIcon("好题精练", QuestionDatabase.PACKAGE);
    }

    public void openOnesearch() {
        openIcon("一键搜", OneSearchDark.PACKAGE);
    }

    public void openEnglishTalk() {
        openIcon("英语听说", EnglishTalk.PACKAGE);
    }

    public void openBbkMiddleMarket() {
        openIcon("应用商店", BbkMiddleMarket.PACKAGE);
    }
}

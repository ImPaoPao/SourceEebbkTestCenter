package com.eebbk.test.performance;

import android.app.Instrumentation;
import android.os.Bundle;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.BySelector;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;

import com.eebbk.test.automator.Automator;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.support.test.InstrumentationRegistry.getArguments;

@RunWith(AndroidJUnit4.class)
public class PerforTestCase extends Automator {
    public static String TAG = "PerforTestCase";

    private FileWriter mWriter;
    private XmlSerializer mXml;
    private String mStartTime;

    protected int mCount = 20;
    protected int mType = 0;//0:冷启动 1:热启动

    @Before
    public void setUp() throws Exception {
        super.setUp();
        String count = getArguments().getString("count", "1");
        String type = getArguments().getString("type", "0");
        if (TextUtils.isDigitsOnly(count)) {
            mCount = Integer.parseInt(count);
        }
        if (TextUtils.isDigitsOnly(type)) {
            mType = Integer.parseInt(type);
        }

        String number = getArguments().getString("number", "unknown");
        File out = new File("/sdcard/performance-test/", number);
        if (out.exists()) {
            File[] files = out.listFiles();
            if (files != null) {
                for (File file : files) {
                    file.delete();
                }
            }
        } else {
            out.mkdirs();
        }

        mWriter = new FileWriter(new File(out, "result.xml"));
        mXml = Xml.newSerializer();
        mXml.setOutput(mWriter);
        mXml.startDocument("UTF-8", false);
        mXml.text("\n");
        mXml.startTag(null, "Record");
    }


    public void startTestRecord() {
        Log.i(TAG, "record start time ");
        mStartTime = getCurrentDate();
    }

    public void stopTestRecord() {
        Log.i(TAG, "record endtime and infos");
        if (mStartTime != null) {
            try {
                mXml.text("\n  ");
                mXml.startTag(null, "Segment");
                mXml.attribute(null, "starttime", mStartTime);
                mXml.attribute(null, "endtime", getCurrentDate());
                mXml.endTag(null, "Segment");
            } catch (IOException e) {
                // Nothing to do
            }
        }
        mStartTime = null;
    }


    public void stopTestRecord(String loadtime) {
        Log.i(TAG, "record endtime and infos");
        if (mStartTime != null) {
            try {
                mXml.text("\n  ");
                mXml.startTag(null, "Segment");
                mXml.attribute(null, "starttime", mStartTime);
                mXml.attribute(null, "loadtime", loadtime);
                mXml.attribute(null, "endtime", getCurrentDate());
                mXml.endTag(null, "Segment");
            } catch (IOException e) {
                // Nothing to do
            }
        }
        mStartTime = null;
    }
    public void stopTestRecord(String loadtime, String startScreen, String endScreen, String compareTime, String compareResult) {
        Log.i(TAG, "record endtime and infos");
        if (mStartTime != null) {
            try {
                mXml.text("\n  ");
                mXml.startTag(null, "Segment");
                mXml.attribute(null, "starttime", mStartTime);
                mXml.attribute(null, "loadtime", loadtime);
                mXml.attribute(null, "startScreen", startScreen);
                mXml.attribute(null, "endScreen", endScreen);
                mXml.attribute(null, "compareTime", compareTime);
                mXml.attribute(null, "compareResult", compareResult);
                mXml.attribute(null, "endtime", getCurrentDate());
                mXml.endTag(null, "Segment");
            } catch (IOException e) {
                // Nothing to do
            }
        }
        mStartTime = null;
    }

    public void clearRunprocess() throws IOException {
        mDevice.executeShellCommand("am start -W com.android.systemui/.recents.RecentsActivity");
        mDevice.waitForIdle();
        BySelector cleanAll = By.res("com.android.systemui", "clean_all");
        mDevice.wait(Until.hasObject(cleanAll), WAIT_TIME);
        UiObject2 cleanAllObj = mDevice.findObject(cleanAll);
        cleanAllObj.clickAndWait(Until.newWindow(), WAIT_TIME * 2);
        mDevice.pressHome();
        mDevice.waitForIdle();
    }

    public void swipeCurrentLauncher() {
        for (int j = 0; j < 3; j++)
            mDevice.swipe(mDevice.getDisplayWidth() / 2, mDevice.getDisplayHeight() / 2, 0, mDevice.getDisplayHeight() / 2, 20);
    }


    @After
    public void tearDown() throws IOException {
        mXml.text("\n");
        mXml.endTag(null, "Record");
        mXml.endDocument();
        mWriter.flush();
        mWriter.close();
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss.SSS", Locale.getDefault());
        return sdf.format(new Date());
    }

    //output instrumentation status info
    public void instrumentationStatusOut(JSONObject obj) {
        Bundle b = new Bundle();
        b.putString(Instrumentation.REPORT_KEY_STREAMRESULT, obj.toString());
        InstrumentationRegistry.getInstrumentation().sendStatus(0, b);
    }
}





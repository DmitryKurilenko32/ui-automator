package ru.netology.testing.uiautomator

import android.content.Context
import android.view.KeyEvent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


const val SETTINGS_PACKAGE = "com.android.settings"
const val MODEL_PACKAGE = "ru.netology.testing.uiautomator"

const val TIMEOUT = 5000L

@RunWith(AndroidJUnit4::class)
class ChangeTextTest {

    private lateinit var device: UiDevice
    private val textToSet = "hello!!!"
    private val textToSetToo = "hello World!!!"


    private fun waitForPackage(packageName: String) {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        context.startActivity(intent)
        device.wait(Until.hasObject(By.pkg(packageName)), TIMEOUT)
    }

    @Before
    fun beforeEachTest() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.pressHome()

        val launcherPackage = device.launcherPackageName
        device.wait(Until.hasObject(By.pkg(launcherPackage)), TIMEOUT)
    }


    @Test
    fun testChangeText() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        device.findObject(By.res(packageName, "userInput")).text = textToSet
        device.findObject(By.res(packageName, "buttonChange")).click()
        device.wait(Until.findObject(By.res(packageName, "userInput")), 5000)

        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
        assertEquals(result, textToSet)
    }

    @Test
    fun testEmptyString() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)
        device.findObject(By.res(packageName, "userInput")).text = textToSet
        device.findObject(By.res(packageName, "buttonChange")).click()
        device.findObject(By.res(packageName, "userInput")).text = ""
        device.findObject(By.res(packageName, "buttonChange")).click()
        Thread.sleep(3000)
        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
        assertEquals(result, textToSet)
    }

    @Test
    fun testSpaceOnEmptyString() {
        val actualText = "Hello UiAutomator!"
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)
        device.findObject(By.res(packageName, "userInput")).click()
        device.pressKeyCode(KeyEvent.KEYCODE_SPACE);
        device.findObject(By.res(packageName, "buttonChange")).click()
        Thread.sleep(3000)
        val result = device.findObject(By.res(packageName, "textToBeChanged")).text
        assertEquals(result, actualText)
    }


    @Test
    fun testChangeTextNextPage() {
        val packageName = MODEL_PACKAGE
        waitForPackage(packageName)

        device.findObject(By.res(packageName, "userInput")).text = textToSetToo
        device.findObject(By.res(packageName, "buttonActivity")).click()
        device.wait(Until.findObject(By.res(packageName, "text")), 5000)

        val result = device.findObject(By.res(packageName, "text")).text
        assertEquals(result, textToSetToo)
    }


}




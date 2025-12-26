package tests;

import io.qameta.allure.testng.AllureTestNg;
import org.testng.annotations.Listeners;

@Listeners({AllureTestNg.class})
public abstract class BaseAllureTest {
    // Inherit this class in all test classes to enable Allure reporting
}

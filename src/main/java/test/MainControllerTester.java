package test;

import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class MainControllerTester {
    public void testLogin() {
        open("/login");
        $(By.name("username")).setValue("jeka");
        $("#submitBtn").click();
    }
}

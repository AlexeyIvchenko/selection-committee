package test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

public class ExtranceTestControllerTester {
    private static final String PATH_TO_APP = "http://localhost:8010";

    @Before
    public void authorize() {
        String loginPagePath = PATH_TO_APP + "/login";
        Configuration.baseUrl = loginPagePath;

        Selenide.open(loginPagePath);
        $(By.name("username")).setValue("jeka");
        $(By.name("password")).setValue("1234");
        $("#submitBtn").click();
    }

    @Test
    public void testCorrectExtranceTestData() {
        String loginPagePath = PATH_TO_APP + "/user/extranceTestPage/1";
        Configuration.baseUrl = loginPagePath;

        Selenide.open(loginPagePath);
        $("#hbResult").setValue("15");
        $("#run100mResult").setValue("15.4");
        $("#run3kmResult").setValue("10.7");
        $("#prof_group").setValue("1");
        $("#submitNewName").click();
        sleep(2000);
        $("#extranceTestMessage").shouldHave(Condition.text("Результаты успешно изменены!"));
    }
}

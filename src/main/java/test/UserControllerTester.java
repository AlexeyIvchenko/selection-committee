package test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

/**
 * Класс предназначен для автоматизированного тестирования авторизации.
 */
public class UserControllerTester {

    private static final String PATH_TO_APP = "http://localhost:8010";

    /**
     * Тестирование процесса авторизации
     */
    @Test
    public void testCorrectAuthorizationData() {
        String loginPagePath = PATH_TO_APP + "/login";
        Configuration.baseUrl = loginPagePath;

        Selenide.open(loginPagePath);
        $(By.name("username")).setValue("jeka");
        $(By.name("password")).setValue("1234");
        $("#submitBtn").click();
        Assert.assertEquals(WebDriverRunner.url(), "http://localhost:8010/user/info");
    }
}

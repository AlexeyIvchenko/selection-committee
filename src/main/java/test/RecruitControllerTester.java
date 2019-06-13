package test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

/**
 * Класс предназначен для автоматизированного тестирования графического интерфейса
 * модуля программы, связанного с регистрацией личного дела абитуриента.
 */
public class RecruitControllerTester {

    private static final String PATH_TO_APP = "http://localhost:8010";

    /**
     * Предварительная авторизация пользователя в системе.
     */
    @Before
    public void authorize() {
        String loginPagePath = PATH_TO_APP + "/login";
        Configuration.baseUrl = loginPagePath;

        Selenide.open(loginPagePath);
        $(By.name("username")).setValue("jeka");
        $(By.name("password")).setValue("1234");
        $("#submitBtn").click();
    }

    /**
     * Тестирование возможности добавления/редактирования данных об абитуриенте при вводе корректных данных.
     */
    @Test
    public void testCorrectRecruitData() {
        String loginPagePath = PATH_TO_APP + "/user/recruitQuestionary";
        Configuration.baseUrl = loginPagePath;

        Selenide.open(loginPagePath);
        $("#surname").setValue("Ивченко");
        $("#name").setValue("Алексей");
        $("#secondName").setValue("Дмитриевич");
        $("#birthday").setValue("29.01.1996");
        $(By.name("sex")).selectRadio("true");
        $(By.name("familyStatus")).selectRadio("true");
        $(By.name("category")).selectOptionByValue("3");
        $(By.name("nationality")).selectOptionByValue("1");
        $(By.name("region")).selectOptionContainingText("Рязанская область");
        $(By.name("address.city")).selectOptionContainingText("Рязань");
        $(By.name("address.streetName")).setValue("Пушкина");
        $(By.name("address.houseNumber")).setValue("50");
        $(By.name("address.blockNumber")).setValue("1");
        $(By.name("address.apartmentNumber")).setValue("10");
        $(By.name("office")).selectOptionByValue("10");
        $(By.name("passport.passportNumber")).setValue("7109 600579");
        $(By.name("passport.passportIssuedBy")).setValue("Отделением №3 отдела УФМС России по Рязанской облсти в г. Рязани");
        $(By.name("passport.passportDate")).setValue("19.02.2016");
        $("#submitBtn").click();

        $("#message").shouldHave(Condition.text("Личное дело абитуриента зарегистрировано!"));
        sleep(5000);
    }

    /**
     * Тестирование возможности добавления/редактирования данных об абитуриенте при вводе некорректных данных.
     */
    @Test
    public void testIncorrectRecruitData() {
        String loginPagePath = PATH_TO_APP + "/user/recruitQuestionary";
        Configuration.baseUrl = loginPagePath;

        Selenide.open(loginPagePath);
        $("#surname").setValue("Ивченко1");
        $("#name").setValue("Алексей");
        $("#secondName").setValue("Дмитриевич");
        $("#birthday").setValue("29.01.1996");
        $(By.name("sex")).selectRadio("true");
        $(By.name("familyStatus")).selectRadio("true");
        $(By.name("category")).selectOptionByValue("3");
        $(By.name("nationality")).selectOptionByValue("1");
        $(By.name("region")).selectOptionContainingText("Рязанская область");
        $(By.name("address.city")).selectOptionContainingText("Рязань");
        $(By.name("address.streetName")).setValue("Пушкина");
        $(By.name("address.houseNumber")).setValue("50");
        $(By.name("address.blockNumber")).setValue("1");
        $(By.name("address.apartmentNumber")).setValue("10");
        $(By.name("office")).selectOptionByValue("10");
        $(By.name("passport.passportNumber")).setValue("7109 600580");
        $(By.name("passport.passportIssuedBy")).setValue("Отделением №3 отдела УФМС России по Рязанской облсти в г. Рязани");
        $(By.name("passport.passportDate")).setValue("19.02.2016");
        $("#submitBtn").click();
        sleep(2000);
        $("#submitBtn").should(Condition.disabled);
    }
}

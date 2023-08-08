import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;


public class SelenideTest {
    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    public String date(long addDays, String pattern) {
        return LocalDate.now().minusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    String planningDate = generateDate(3, "dd.MM.yyyy");
    String errorDate = date(4,"dd.MM.yyyy");

    @Test
    void shouldDelivery() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Ульяновск");
        $("[name='name']").setValue("Петрова Анастасия");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(planningDate);
        $("[name='phone']").setValue("+79370350050");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);

    }

    @Test
    void doubleNameCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Горно-Алтайск");
        $("[name='name']").setValue("Петрова Анастасия-Мария");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(planningDate);
        $("[name='phone']").setValue("+79370350050");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void whitespaceTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Нижний Новгород");
        $("[name='name']").setValue("Петрова Анастасия Мария");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(planningDate);
        $("[name='phone']").setValue("+79370350050");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
    }

    @Test
    void errorName() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Нижний Новгород");
        $("[name='name']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(planningDate);
        $("[name='phone']").setValue("+79370350050");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//span[contains(text(),'Поле обязательно для заполнения')]").shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    void errorName1() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Херсон");
        $("[name='name']").setValue("Petrova Anastasiya");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(planningDate);
        $("[name='phone']").setValue("+79370350050");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $(".input_invalid").shouldHave(Condition.text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void errorPhone() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Москва");
        $("[name='name']").setValue("Петрова Анастасия");
        $("[data-test-id='agreement']").click();
        $("[name='phone']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(planningDate);
        $(By.className("button__text")).click();
        $(".input_invalid").shouldHave(Condition.text("Поле обязательно для заполнения"));
    }

    @Test
    void errorPhonePlus() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Биробиджан");
        $("[name='name']").setValue("Петрова Анастасия");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(planningDate);
        $("[name='phone']").setValue("+");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $(".input_invalid").shouldHave(Condition.text("Телефон указан неверно"));
    }

    @Test
    void errorDate() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Нижний Новгород");
        $("[name='name']").setValue("Петрова Анастасия");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[data-test-id='agreement']").click();
        $("[name='phone']").setValue("+79370357057");
        $(By.className("button__text")).click();
        $(".input_invalid").shouldHave(Condition.text("Неверно введена дата"));

    }

    @Test
    void errorDatePrevious() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Салехард");
        $("[name='name']").setValue("Петрова Анастасия");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(errorDate);
        $("[name='phone']").setValue("+79370357057");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $(".input_invalid").shouldHave(Condition.text("Заказ на выбранную дату невозможен"));
    }

    @Test
    void errorCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Питер");
        $("[name='name']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(planningDate);
        $("[name='phone']").setValue("+79370357057");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $("[data-test-id='city']").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

    @Test
    void errorEmptyFieldCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("");
        $("[name='name']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(planningDate);
        $("[name='phone']").setValue("+79370357057");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $("[data-test-id='city']").shouldHave(Condition.text("Поле обязательно для заполнения"));

    }

    @Test
    void errorForeignCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Piter");
        $("[name='name']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(planningDate);
        $("[name='phone']").setValue("+79370357057");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $("[data-test-id='city']").shouldHave(Condition.text("Доставка в выбранный город недоступна"));
    }

}


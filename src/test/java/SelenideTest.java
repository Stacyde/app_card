import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;


public class SelenideTest {
    LocalDate date = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    LocalDate nextDate = date.plusDays(3);
    LocalDate previousDate = date.minusDays(3);

    @Test
    void shouldDelivery() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Ульяновск");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("Петрова Анастасия");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(formatter.format(nextDate));
        $("[name='phone']").setValue("+79370350050");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//div[contains(text(),'Встреча успешно забронирована на')]").should(appear, Duration.ofSeconds(15));
    }

    @Test
    void doubleNameCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Горно-Алтайск");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("Петрова Анастасия-Мария");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(formatter.format(nextDate));
        $("[name='phone']").setValue("+79370350050");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//div[contains(text(),'Встреча успешно забронирована на')]").should(appear, Duration.ofSeconds(15));
    }

    @Test
    void whitespaceTest() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Нижний Новгород");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("Петрова Анастасия Мария");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(formatter.format(nextDate));
        $("[name='phone']").setValue("+79370350050");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//div[contains(text(),'Встреча успешно забронирована на')]").should(appear, Duration.ofSeconds(15));
    }

    @Test
    void checkBoxError() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id=city] input")).setValue("Ульяновск");
        $("[placeholder='Дата встречи']").click();
        $("[data-day='1691438400000']").click();
        $("[name='name']").setValue("Петрова Анастасия");
        $("[name='phone']").setValue("+79370350050");
        $(By.className("button__text")).click();
        $(By.className("input_invalid")).shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    @Test
    void errorName() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Нижний Новгород");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(formatter.format(nextDate));
        $("[name='phone']").setValue("+79370350050");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//span[contains(text(),'Поле обязательно для заполнения')]");
    }

    @Test
    void errorName1() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Херсон");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("Petrova Anastasiya");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(formatter.format(nextDate));
        $("[name='phone']").setValue("+79370350050");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//span[contains(text(),'Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.')]");
    }

    @Test
    void errorPhone() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Москва");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("Петрова Анастасия");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(formatter.format(nextDate));
        $("[name='phone']").setValue("");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//span[contains(text(),'Поле обязательно для заполнения')]");
    }

    @Test
    void errorPhone1() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Биробиджан");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("Петрова Анастасия");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(formatter.format(nextDate));
        $("[name='phone']").setValue("+");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//span[contains(text(),'Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.')]");
    }

    @Test
    void errorDate() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Нижний Новгород");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("Петрова Анастасия");
        $("[name='phone']").setValue("+79370357057");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//span[contains(text(),'Неверно введена дата')]");
    }

    @Test
    void errorDatePrevious() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Салехард");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("Петрова Анастасия");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(formatter.format(previousDate));
        $("[name='phone']").setValue("+79370357057");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//span[contains(text(),'Неверно введена дата')]");
    }

    @Test
    void errorCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Питер");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(formatter.format(nextDate));
        $("[name='phone']").setValue("+79370357057");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//span[contains(text(),'Доставка в выбранный город недоступна')]");
    }

    @Test
    void errorEmptyFieldCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(formatter.format(nextDate));
        $("[name='phone']").setValue("+79370357057");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//span[contains(text(),'Поле обязательно для заполнения')]");
    }

    @Test
    void errorForeignCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        $(By.cssSelector("[data-test-id='city'] input")).setValue("Piter");
        $("[placeholder='Дата встречи']").click();
        Selenide.actions().keyDown(Keys.CONTROL).sendKeys("a").sendKeys(Keys.BACK_SPACE).perform();
        $("[name='name']").setValue("");
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(formatter.format(nextDate));
        $("[name='phone']").setValue("+79370357057");
        $("[data-test-id='agreement']").click();
        $(By.className("button__text")).click();
        $x("//span[contains(text(),'Доставка в выбранный город недоступна')]");
    }

}


package guru.qa;

import com.codeborne.selenide.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class ParametrizedTest {

    @ValueSource(strings = {"Русский" , "English", "Deutsch", "日本語"})
    @ParameterizedTest(name = "Wikipedia home page should have \"{0}\" language.")
    void wikipediaHomePageShouldHaveLanguage(String testData) {

        open("https://wikipedia.org/");
        $(".central-featured").shouldHave(text(testData));

    }


    @CsvSource(value = {
            "JUnit, JUnit — фреймворк для модульного тестирования программного обеспечения на языке Java.",
            "Java, Java[прим. 1] — строго типизированный объектно-ориентированный язык программирования общего назначения"
    })
    @ParameterizedTest(name = "Wikipedia page for \"{0}\" should have text \"{1}\" in its first paragraph.")
    void wikipediaShouldHaveTextInArticle(String testData, String expectedResult) {

        open("https://wikipedia.org/");
        $("#searchInput").setValue(testData).pressEnter();
        $("p:first-of-type").shouldHave(text(expectedResult));

    }

    static Stream<Arguments> wikiJavaTest() {
        return Stream.of(
                Arguments.of("Java", List.of("Класс языка", "Появился в", "Автор", "Разработчик","Расширение файлов","Выпуск", "Испытал влияние", "Лицензия", "Сайт")),
                Arguments.of("Ява", List.of("Площадь", "Наивысшая точка", "Население", "Плотность населения", "Омывающие акватории", "Страна"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Wikipedia page for {0} should have {1} characteristics of the subject")
    void wikiJavaTest(String java, List<String> characteristic) {
        open("https://wikipedia.org/");
        $("[name=search]").setValue(java).pressEnter();
        $$(".infobox th[scope=row]").shouldHave(CollectionCondition.texts(characteristic));
    }
}

package confForTests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;

import java.io.IOException;

public class Setup {
    @BeforeClass
    public void setup() {
        try {
            System.getProperties().load(ClassLoader.getSystemResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла config.properties", e);
        }

        String baseUri = System.getProperty("base.uri");

        if (baseUri == null || baseUri.isEmpty()) {
            throw new RuntimeException("В файле \"config.properties\" отсутствует значение \"base.uri\"");
        }

        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri) // Задаем базовый URI для всех запросов
                .setAccept(ContentType.JSON) // Задаем заголовок accept
                .setContentType(ContentType.JSON) // Задаем заголовок content-type
                .log(LogDetail.ALL) // Логгирование всех деталей запросов и ответов
                .build();
        RestAssured.filters(new ResponseLoggingFilter());
    }
}

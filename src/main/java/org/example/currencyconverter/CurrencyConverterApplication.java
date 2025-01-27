package org.example.currencyconverter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Основной класс приложения для конвертации валют.
 * <p>
 * Приложение предоставляет функциональность для получения актуальных курсов валют,
 * их конвертации и хранения данных в базе данных. Также поддерживается возможность
 * автоматического обновления данных по расписанию.
 * </p>
 *
 * @SpringBootApplication Аннотация, объединяющая @Configuration, @EnableAutoConfiguration и @ComponentScan.
 * @EnableJpaRepositories Включает поддержку репозиториев JPA и указывает пакет для их поиска.
 * @EnableScheduling Включает поддержку выполнения задач по расписанию.
 */
@SpringBootApplication
@EnableJpaRepositories("org.example.currencyconverter.persistence.repository")
@EnableScheduling
public class CurrencyConverterApplication {

    /**
     * Точка входа в приложение.
     *
     * @param args Аргументы командной строки, переданные при запуске приложения.
     */
    public static void main(String[] args) {
        SpringApplication.run(CurrencyConverterApplication.class, args);
    }
}
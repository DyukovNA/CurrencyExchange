package org.example.currencyconverter.api.controller;

import org.example.currencyconverter.api.dto.CurrencyDto;
import org.example.currencyconverter.service.CurrencyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Контроллер для обработки запросов, связанных с получением информации о курсах валют.
 * <p>
 * Предоставляет REST API для получения данных о валюте по её коду.
 * </p>
 */
@RestController
@RequestMapping("/api")
public class CurrencyController {

    /**
     * Сервис для работы с данными о валютах.
     */
    private final CurrencyServiceImpl currencyService;

    /**
     * Конструктор для внедрения зависимости сервиса {@link CurrencyServiceImpl}.
     *
     * @param currencyService Сервис для работы с данными о валютах.
     */
    @Autowired
    public CurrencyController(CurrencyServiceImpl currencyService) {
        this.currencyService = currencyService;
    }

    /**
     * Обрабатывает GET-запрос для получения информации о валюте по её коду.
     * <p>
     * Пример запроса: /api/getData?code=USD
     * </p>
     *
     * @param charCode Код валюты (например, USD, EUR и т.д.).
     * @return Объект {@link CurrencyDto}, содержащий информацию о запрошенной валюте.
     */
    @GetMapping("/getData")
    @CrossOrigin(origins = "http://localhost:63342")
    public CurrencyDto getCurrencyData(@RequestParam("code") String charCode) {
        return currencyService.getByCharCode(charCode);
    }

}

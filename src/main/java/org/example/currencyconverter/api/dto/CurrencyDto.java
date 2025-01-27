package org.example.currencyconverter.api.dto;

import lombok.Builder;

/**
 * Класс, представляющий основную информацию о валюте.
 * <p>
 * Используется для передачи данных о валюте между слоями приложения.
 * </p>
 */
@Builder
public class CurrencyDto {
    /**
     * Код валюты (например, USD, EUR и т.д.).
     */
    public String charCode;

    /**
     * Полное название валюты (например, "Доллар США", "Евро" и т.д.).
     */
    public String name;

    /**
     * Курс валюты по отношению к рублю.
     */
    public double value;


    /**
     * Конструктор для создания объекта.
     *
     * @param charCode Код валюты.
     * @param name     Полное название валюты.
     * @param value    Курс валюты по отношению к рублю.
     */
    public CurrencyDto(String charCode, String name, double value) {
        this.charCode = charCode;
        this.name = name;
        this.value = value;
    }
}

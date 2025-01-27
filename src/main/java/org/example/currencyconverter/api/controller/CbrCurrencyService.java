package org.example.currencyconverter.api.controller;

import com.google.gson.Gson;
import org.example.currencyconverter.api.dto.CurrencyDto;
import org.json.JSONObject;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Класс для работы с API ЦБ РФ
 */
@Component
public class CbrCurrencyService {
    /**
     * Поле объекта, содержащего данные, полученные после обращения к API
     */
    private JSONObject currenciesData = null;
    /**
     * URL API Центрального Банка России для получения актуальных курсов валют. Курс обновляется ежедневно
     */
    private static final String CBR_URL = "https://www.cbr-xml-daily.ru/daily_json.js";


    /**
     * Получает данные о валютах от API ЦБ РФ.
     *
     * @return JSONObject, содержащий данные о валютах, полученные от API.
     * @throws RuntimeException если происходит ошибка при чтении данных из API.
     */
    private JSONObject fetchCurrenciesData() {
        String json;

        try {
            json = new String(
                    new URL(CBR_URL)
                            .openStream()
                            .readAllBytes()
            );
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при получении данных от API ЦБ РФ", e);
        }

         return new JSONObject(json).getJSONObject("Valute");
    }

    /**
     * Получает и обрабатывает данные о курсах валют.
     *
     * @return Список объектов {@link CurrencyDto}, содержащих информацию о каждой валюте.
     */
    public List<CurrencyDto> getCurrenciesRates() {
        JSONObject currenciesData = fetchCurrenciesData();

        List<CurrencyData> currencies = parseCurrenciesData(currenciesData);
        logCurrencies(currencies);

        return convertDataToDto(currencies);
    }

    /**
     * Парсит данные о валютах из JSONObject в список объектов {@link CurrencyData}.
     *
     * @param currenciesData JSONObject, содержащий данные о валютах.
     * @return Список объектов {@link CurrencyData}, представляющих данные о валютах.
     */
    private List<CurrencyData> parseCurrenciesData(JSONObject currenciesData) {
        return currenciesData.keySet().stream()
                .map(currency -> new Gson().fromJson(
                        currenciesData.getJSONObject(currency).toString(),
                        CurrencyData.class
                ))
                .toList();
    }

    /**
     * Логирует информацию о валютах в консоль.
     *
     * @param currencies Список объектов {@link CurrencyData}, содержащих данные о валютах.
     */
    private void logCurrencies(List<CurrencyData> currencies) {
        currencies.forEach(currency ->
                System.out.println(currency.Name + " (" + currency.CharCode + ") - " + currency.Value)
        );
    }

    /**
     * Преобразует список объектов {@link CurrencyData} в список объектов {@link CurrencyDto}.
     *
     * @param dataList Список объектов {@link CurrencyData}, содержащих данные о валютах.
     * @return Список объектов {@link CurrencyDto}, готовых для использования в других частях приложения.
     */
    private List<CurrencyDto> convertDataToDto(List<CurrencyData> dataList) {
        List<CurrencyDto> dtoList = new ArrayList<>();
        dataList.forEach(data -> dtoList.add(new CurrencyDto(data.CharCode, data.Name, data.Value)));
        return dtoList;
    }

    /**
     * Внутренний класс для парсинга данных о валютах из JSON.
     */
    private static class CurrencyData {
        public String CharCode;
        public String Name;
        public double Value;
    }
}

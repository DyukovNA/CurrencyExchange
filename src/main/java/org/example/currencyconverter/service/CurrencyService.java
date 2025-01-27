package org.example.currencyconverter.service;

import org.example.currencyconverter.persistence.entity.Currency;

import java.util.List;

/**
 * Сервис для работы с данными о валютах.
 * <p>
 * Предоставляет методы для сохранения, получения и удаления данных о валютах.
 * </p>
 */
public interface CurrencyService {

    /**
     * Сохраняет информацию о валюте.
     *
     * @param currency Объект {@link Currency}, содержащий данные о валюте.
     * @return Сохранённый объект {@link Currency}.
     */
    Currency saveCurrency(Currency currency);

    /**
     * Возвращает список всех валют.
     *
     * @return Список объектов {@link Currency}, содержащих данные о валютах.
     */
    List<Currency> fetchCurrencyList();

    /**
     * Удаляет валюту по её идентификатору.
     *
     * @param currencyID Идентификатор валюты, которую необходимо удалить.
     */
    void deleteCurrencyByID(Long currencyID);
}
package org.example.currencyconverter.service;

import jakarta.annotation.PostConstruct;
import org.example.currencyconverter.api.controller.CbrCurrencyService;
import org.example.currencyconverter.api.dto.CurrencyDto;
import org.example.currencyconverter.persistence.entity.Currency;
import org.example.currencyconverter.persistence.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Реализация сервиса для работы с данными о валютах.
 * <p>
 * Предоставляет методы для сохранения, обновления, получения и удаления данных о валютах.
 * Также включает автоматическое заполнение и обновление базы данных курсами валют.
 * </p>
 */
@Service
@Component
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;
    private final CbrCurrencyService cbrCurrencyService;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param currencyRepository Репозиторий для работы с данными о валютах.
     * @param cbrCurrencyService        API для получения курсов валют.
     */
    @Autowired
    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CbrCurrencyService cbrCurrencyService) {
        this.currencyRepository = currencyRepository;
        this.cbrCurrencyService = cbrCurrencyService;
    }


    /**
     * Инициализация базы данных при старте приложения.
     * Заполняет базу данных актуальными курсами валют.
     */
    @PostConstruct
    public void createAndFillDB() {
        List<CurrencyDto> currencyDtoList = cbrCurrencyService.getCurrenciesRates();
        this.saveAll(currencyDtoList);
        System.out.println("База данных успешно заполнена актуальными курсами валют.");
    }

    /**
     * Обновление базы данных курсами валют по расписанию.
     * Выполняется ежедневно в полночь.
     */
    @Scheduled(cron="0 0 0 * * ?")
    public void updateDB() {
        List<CurrencyDto> currencyDtoList = cbrCurrencyService.getCurrenciesRates();
        this.updateAll(currencyDtoList);
    }

    /**
     * Сохраняет список валют в базу данных.
     *
     * @param currencyDtoList Список объектов {@link CurrencyDto} для сохранения.
     */

    public void saveAll(List<CurrencyDto> currencyDtoList) {
        for (int i = 0; i <= currencyDtoList.size() - 1; i++) {
            Currency currency = convertFromDto(currencyDtoList.get(i));
            saveCurrency(currency);
        }
    }

    /**
     * Обновляет список валют в базе данных.
     *
     * @param currencyDtoList Список объектов {@link CurrencyDto} для обновления.
     */
    public void updateAll(List<CurrencyDto> currencyDtoList) {
        for (int i = 0; i <= currencyDtoList.size() - 1; i++) {
            Currency currency = convertFromDto(currencyDtoList.get(i));
            updateCurrency((long) i+1, currency);
        }
    }

    /**
     * Возвращает данные о валюте по её коду.
     *
     * @param charCode Код валюты (например, USD, EUR и т.д.).
     * @return Объект {@link CurrencyDto}, содержащий информацию о валюте.
     */
    public CurrencyDto getByCharCode(String charCode) {
        return currencyRepository.findByCharCode(charCode)
                .stream()
                .findFirst()
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Валюта с кодом " + charCode + " не найдена."));
    }

    /**
     * Обновляет данные о валюте по её идентификатору.
     *
     * @param id              Идентификатор валюты.
     * @param updatedCurrency Обновлённые данные о валюте.
     * @return Обновлённый объект {@link Currency}.
     */
    public Currency updateCurrency(Long id, Currency updatedCurrency) {
        return currencyRepository.findById(id)
                .map(currency -> {
                    currency.setName(updatedCurrency.getName());
                    currency.setCharCode(updatedCurrency.getCharCode());
                    currency.setValue(updatedCurrency.getValue());
                    return currencyRepository.save(currency);
                })
                .orElseThrow(() -> new RuntimeException("Валюта с идентификатором " + id + " не найдена."));
    }

    @Override
    public Currency saveCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    @Override
    public List<Currency> fetchCurrencyList() {
        return (List<Currency>) currencyRepository.findAll();
    }

    @Override
    public void deleteCurrencyByID(Long currencyID) {
        currencyRepository.deleteById(currencyID);
    }

    /**
     * Преобразует объект {@link CurrencyDto} в объект {@link Currency}.
     *
     * @param currencyDto Объект {@link CurrencyDto} для преобразования.
     * @return Объект {@link Currency}.
     */
    public Currency convertFromDto(CurrencyDto currencyDto) {
        Currency currency = new Currency();
        currency.setCharCode(currencyDto.charCode);
        currency.setValue(currencyDto.value);
        currency.setName(currencyDto.name);
        return currency;
    }

    /**
     * Преобразует объект {@link Currency} в объект {@link CurrencyDto}.
     *
     * @param currency Объект {@link Currency} для преобразования.
     * @return Объект {@link CurrencyDto}.
     */
    public CurrencyDto convertToDto(Currency currency) {
        return new CurrencyDto(
                currency.getCharCode(),
                currency.getName(),
                currency.getValue()
        );
    }
}

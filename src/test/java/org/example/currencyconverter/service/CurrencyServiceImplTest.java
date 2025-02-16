package org.example.currencyconverter.service;

import org.example.currencyconverter.api.dto.CurrencyDto;
import org.example.currencyconverter.persistence.entity.Currency;
import org.example.currencyconverter.persistence.repository.CurrencyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CurrencyServiceImplTest {
    @Mock
    CbrRatesService mockedCbrRates = mock(CbrRatesService.class);
    @Mock
    private CurrencyRepository currencyRepository;

    @InjectMocks
    private CurrencyServiceImpl currencyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCurrency() {
        // Arrange
        Currency currency = new Currency();
        currency.setCharCode("USD");
        currency.setName("Доллар США");
        currency.setValue(97.132);

        when(currencyRepository.save(currency)).thenReturn(currency);

        Currency savedCurrency = currencyService.saveCurrency(currency);

        assertNotNull(savedCurrency);
        assertEquals("USD", savedCurrency.getCharCode());
        assertEquals("Доллар США", savedCurrency.getName());
        verify(currencyRepository, times(1)).save(currency);
    }

    @Test
    void testFetchCurrencyList() {
        // Arrange
        Currency currency1 = new Currency();
        currency1.setCharCode("USD");
        currency1.setName("Доллар США");
        currency1.setValue(97.132);

        Currency currency2 = new Currency();
        currency2.setCharCode("EUR");
        currency2.setName("Евро");
        currency2.setValue(102.746);

        List<Currency> currencies = Arrays.asList(currency1, currency2);

        when(currencyRepository.findAll()).thenReturn(currencies);

        List<Currency> fetchedCurrencies = currencyService.fetchCurrencyList();

        assertNotNull(fetchedCurrencies);
        assertEquals(2, fetchedCurrencies.size());
        verify(currencyRepository, times(1)).findAll();
    }

    @Test
    void testDeleteCurrencyByID() {
        Long currencyID = 1L;

        doNothing().when(currencyRepository).deleteById(currencyID);

        currencyService.deleteCurrencyByID(currencyID);

        verify(currencyRepository, times(1)).deleteById(currencyID);
    }

    @Test
    void testGetByCharCode() {
        Currency currency = new Currency();
        currency.setCharCode("USD");
        currency.setName("Доллар США");
        currency.setValue(97.132);

        when(currencyRepository.findByCharCode("USD")).thenReturn(Optional.of(currency));

        CurrencyDto currencyDto = currencyService.getByCharCode("USD");

        assertNotNull(currencyDto);
        assertEquals("USD", currencyDto.charCode);
        assertEquals("Доллар США", currencyDto.name);
        verify(currencyRepository, times(1)).findByCharCode("USD");
    }

    @Test
    void testGetByCharCode_ThrowsException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            currencyService.getByCharCode("INVALID");
        });

        assertEquals("Валюта с кодом INVALID не найдена.", exception.getMessage());
        verify(currencyRepository, times(1)).findByCharCode("INVALID");
    }
}
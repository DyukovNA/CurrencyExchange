package org.example.currencyconverter.persistence.repository;

import org.example.currencyconverter.persistence.entity.Currency;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link Currency}.
 * <p>
 * Предоставляет методы для выполнения CRUD-операций и поиска валюты по её коду.
 * </p>
 */
@Repository
public interface CurrencyRepository extends CrudRepository<Currency, Long> {

    /**
     * Находит валюту по её коду.
     *
     * @param charCode Код валюты (например, USD, EUR и т.д.).
     * @return {@link Optional}, содержащий валюту, если она найдена, или пустой {@link Optional}, если нет.
     */
    Optional<Currency> findByCharCode(String charCode);
}
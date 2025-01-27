package org.example.currencyconverter.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Класс, представляющий сущность валюты в базе данных.
 * <p>
 * Содержит информацию о валюте, такую как код, название, значение, а также временные метки создания и обновления.
 * </p>
 */
@Entity
@Table(name = "currency")
@Data
public class Currency {

    /**
     * Уникальный идентификатор валюты.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * Код валюты (например, USD, EUR и т.д.).
     */
    @Column(name = "code")
    private String charCode;

    /**
     * Полное название валюты (например, "Доллар США", "Евро" и т.д.).
     */
    @Column(name = "name")
    private String name;

    /**
     * Курс валюты по отношению к рублю.
     */
    @Column(name = "val")
    private double value;

    /**
     * Временная метка создания записи.
     */
    @Column(name = "created_time")
    private Date created;

    /**
     * Временная метка последнего обновления записи.
     */
    @Column(name = "updated_time")
    private Date updated;

    /**
     * Устанавливает временную метку создания записи перед её сохранением в базу данных.
     */
    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    /**
     * Устанавливает временную метку обновления записи перед её обновлением в базе данных.
     */
    @PreUpdate
    protected void onUpdate() {
        updated = new Date();
    }

    /**
     * Возвращает код валюты.
     *
     * @return Код валюты.
     */
    public String getCharCode() {
        return charCode;
    }

    /**
     * Устанавливает код валюты.
     *
     * @param charCode Код валюты.
     */
    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }

    /**
     * Возвращает название валюты.
     *
     * @return Название валюты.
     */
    public String getName() {
        return name;
    }

    /**
     * Устанавливает название валюты.
     *
     * @param name Название валюты.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Возвращает курс валюты по отношению к рублю.
     *
     * @return Курс валюты.
     */
    public double getValue() {
        return value;
    }

    /**
     * Устанавливает курс валюты по отношению к рублю.
     *
     * @param value Курс валюты.
     */
    public void setValue(double value) {
        this.value = value;
    }
}
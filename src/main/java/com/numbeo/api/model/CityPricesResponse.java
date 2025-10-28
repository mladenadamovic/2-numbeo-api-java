package com.numbeo.api.model;

import java.util.List;

/**
 * Response wrapper for the Numbeo API city_prices endpoint.
 */
public class CityPricesResponse {
    private String name;
    private String country;
    private List<Price> prices;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Price> getPrices() {
        return prices;
    }

    public void setPrices(List<Price> prices) {
        this.prices = prices;
    }

    @Override
    public String toString() {
        return "CityPricesResponse{" +
                "name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", prices=" + prices +
                '}';
    }
}

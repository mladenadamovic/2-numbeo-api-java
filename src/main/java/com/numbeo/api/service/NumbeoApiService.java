package com.numbeo.api.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.numbeo.api.model.CityPricesResponse;
import com.numbeo.api.model.ItemsResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Service class for interacting with the Numbeo API.
 * Provides methods to fetch items and city prices.
 */
public class NumbeoApiService {
    private static final String BASE_URL = "https://www.numbeo.com/api";
    private static final String ITEMS_ENDPOINT = "/items";
    private static final String CITY_PRICES_ENDPOINT = "/city_prices";
    private static final int TIMEOUT = 30000; // 30 seconds

    private final String apiKey;
    private final Gson gson;

    /**
     * Creates a new NumbeoApiService instance.
     *
     * @param apiKey The Numbeo API key for authentication
     */
    public NumbeoApiService(String apiKey) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            throw new IllegalArgumentException("API key cannot be null or empty");
        }
        this.apiKey = apiKey;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }

    /**
     * Fetches all available items from the Numbeo API.
     *
     * @return ItemsResponse containing the list of all items
     * @throws IOException if an error occurs during the API call
     */
    public ItemsResponse fetchItems() throws IOException {
        String urlString = BASE_URL + ITEMS_ENDPOINT + "?api_key=" + apiKey;
        String jsonResponse = makeHttpGetRequest(urlString);
        return gson.fromJson(jsonResponse, ItemsResponse.class);
    }

    /**
     * Fetches city prices for a specific city and country.
     *
     * @param city    The city name (e.g., "San Francisco, CA")
     * @param country The country name (e.g., "United States")
     * @return CityPricesResponse containing price data for the city
     * @throws IOException if an error occurs during the API call
     */
    public CityPricesResponse fetchCityPrices(String city, String country) throws IOException {
        if (city == null || city.trim().isEmpty()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }

        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
        String encodedCountry = URLEncoder.encode(country, StandardCharsets.UTF_8.toString());

        String urlString = BASE_URL + CITY_PRICES_ENDPOINT +
                "?city=" + encodedCity +
                "&country=" + encodedCountry +
                "&api_key=" + apiKey;

        String jsonResponse = makeHttpGetRequest(urlString);
        return gson.fromJson(jsonResponse, CityPricesResponse.class);
    }

    /**
     * Makes an HTTP GET request to the specified URL.
     *
     * @param urlString The URL to make the request to
     * @return The response body as a string
     * @throws IOException if an error occurs during the request
     */
    private String makeHttpGetRequest(String urlString) throws IOException {
        URL url = new URL(urlString);
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(TIMEOUT);
            connection.setReadTimeout(TIMEOUT);
            connection.setRequestProperty("Accept", "application/json");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                return readResponse(connection);
            } else {
                String errorMessage = readErrorResponse(connection);
                throw new IOException(
                        String.format("HTTP request failed with code %d: %s",
                                responseCode, errorMessage)
                );
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    /**
     * Reads the response body from a successful HTTP connection.
     *
     * @param connection The HTTP connection
     * @return The response body as a string
     * @throws IOException if an error occurs while reading the response
     */
    private String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }

    /**
     * Reads the error response body from a failed HTTP connection.
     *
     * @param connection The HTTP connection
     * @return The error response body as a string
     */
    private String readErrorResponse(HttpURLConnection connection) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getErrorStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (Exception e) {
            return "Unable to read error response";
        }
    }
}

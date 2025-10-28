package com.numbeo.api;

import com.numbeo.api.model.CityPricesResponse;
import com.numbeo.api.model.ItemsResponse;
import com.numbeo.api.model.Price;
import com.numbeo.api.service.NumbeoApiService;

import java.io.IOException;
import java.util.*;

/**
 * Main application class for the Numbeo API Client.
 * Fetches and displays price data for a given city and country,
 * organized by categories.
 */
public class NumbeoApiClient {
    // Category display order as specified
    private static final List<String> CATEGORY_ORDER = Arrays.asList(
            "Restaurants",
            "Markets",
            "Transportation",
            "Utilities (Monthly)",
            "Sports And Leisure",
            "Childcare",
            "Clothing And Shoes",
            "Rent Per Month",
            "Buy Apartment Price",
            "Salaries And Financing"
    );

    public static void main(String[] args) {
        // Parse command line arguments
        if (args.length < 3) {
            printUsage();
            System.exit(1);
        }

        String apiKey = args[0];
        String city = args[1];
        String country = args[2];

        // Create service instance
        NumbeoApiService service = new NumbeoApiService(apiKey);

        try {
            System.out.println("=".repeat(80));
            System.out.println("NUMBEO API CLIENT - City Price Information");
            System.out.println("=".repeat(80));
            System.out.println();

            // Fetch items (optional - can be used to understand available items)
            System.out.println("Fetching available items...");
            ItemsResponse itemsResponse = service.fetchItems();
            System.out.println("Total items available: " +
                    (itemsResponse.getItems() != null ? itemsResponse.getItems().size() : 0));
            System.out.println();

            // Fetch city prices
            System.out.println("Fetching prices for " + city + ", " + country + "...");
            CityPricesResponse pricesResponse = service.fetchCityPrices(city, country);
            System.out.println();

            // Display results
            displayCityPrices(pricesResponse);

        } catch (IOException e) {
            System.err.println("Error occurred while fetching data from Numbeo API:");
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Unexpected error occurred:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Displays city prices organized by categories in the specified order.
     *
     * @param response The city prices response from the API
     */
    private static void displayCityPrices(CityPricesResponse response) {
        if (response == null) {
            System.out.println("No data received from API");
            return;
        }

        System.out.println("=".repeat(80));
        System.out.println("PRICE DATA FOR: " + response.getName() + ", " + response.getCountry());
        System.out.println("=".repeat(80));
        System.out.println();

        List<Price> prices = response.getPrices();
        if (prices == null || prices.isEmpty()) {
            System.out.println("No price data available for this city.");
            return;
        }

        // Group prices by category
        Map<String, List<Price>> pricesByCategory = new LinkedHashMap<>();
        for (Price price : prices) {
            if (price == null) {
                continue;
            }
            String categoryName = price.getCategoryName();
            pricesByCategory.computeIfAbsent(categoryName, k -> new ArrayList<>()).add(price);
        }

        // Display prices in the specified category order
        for (String categoryName : CATEGORY_ORDER) {
            List<Price> categoryPrices = pricesByCategory.get(categoryName);
            if (categoryPrices != null && !categoryPrices.isEmpty()) {
                displayCategory(categoryName, categoryPrices);
            }
        }

        // Display any remaining categories not in the predefined order
        for (Map.Entry<String, List<Price>> entry : pricesByCategory.entrySet()) {
            String categoryName = entry.getKey();
            if (!CATEGORY_ORDER.contains(categoryName)) {
                displayCategory(categoryName, entry.getValue());
            }
        }
    }

    /**
     * Displays prices for a specific category.
     *
     * @param categoryName The name of the category
     * @param prices       The list of prices in this category
     */
    private static void displayCategory(String categoryName, List<Price> prices) {
        if (prices == null || prices.isEmpty()) {
            return;
        }

        System.out.println("-".repeat(80));
        System.out.println(categoryName != null ? categoryName.toUpperCase() : "UNKNOWN CATEGORY");
        System.out.println("-".repeat(80));

        for (Price price : prices) {
            displayPrice(price);
        }
        System.out.println();
    }

    /**
     * Displays a single price entry.
     *
     * @param price The price to display
     */
    private static void displayPrice(Price price) {
        if (price == null) {
            return;
        }

        String itemName = price.getItemName() != null ? price.getItemName() : "Unknown Item";
        System.out.printf("  %-50s", itemName);

        if (price.getAverageValue() != null) {
            System.out.printf(" Avg: $%-10.2f", price.getAverageValue());
        } else {
            System.out.printf(" Avg: %-12s", "N/A");
        }

        if (price.getMinValue() != null && price.getMaxValue() != null) {
            System.out.printf(" Range: $%.2f - $%.2f", price.getMinValue(), price.getMaxValue());
        }

        if (price.getDataPoints() != null && price.getDataPoints() > 0) {
            System.out.printf(" (%d data points)", price.getDataPoints());
        }

        System.out.println();
    }

    /**
     * Prints usage information for the application.
     */
    private static void printUsage() {
        System.out.println("Usage: java -jar numbeo-api-client.jar <API_KEY> <CITY> <COUNTRY>");
        System.out.println();
        System.out.println("Arguments:");
        System.out.println("  API_KEY  - Your Numbeo API key (obtain from https://www.numbeo.com/common/api.jsp)");
        System.out.println("  CITY     - City name (e.g., \"San Francisco, CA\")");
        System.out.println("  COUNTRY  - Country name (e.g., \"United States\")");
        System.out.println();
        System.out.println("Example:");
        System.out.println("  java -jar numbeo-api-client.jar your_api_key \"San Francisco, CA\" \"United States\"");
        System.out.println();
    }
}

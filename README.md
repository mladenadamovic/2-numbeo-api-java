# Numbeo API Java Client

A Java application that fetches and displays cost of living data for cities worldwide using the Numbeo API.

## Overview

This application retrieves price information for a specified city and country from the [Numbeo API](https://www.numbeo.com/api/doc.jsp) and displays it organized by categories such as Restaurants, Markets, Transportation, Utilities, and more.

## Features

- Fetches all available items from the Numbeo API
- Retrieves city-specific price data
- Displays prices organized by predefined categories:
  - Restaurants
  - Markets
  - Transportation
  - Utilities (Monthly)
  - Sports And Leisure
  - Childcare
  - Clothing And Shoes
  - Rent Per Month
  - Buy Apartment Price
  - Salaries And Financing
- Shows average, minimum, and maximum prices for each item
- Displays data point counts for transparency

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- A valid Numbeo API key

## Getting a Numbeo API Key

To use this application, you need a Numbeo API key:

1. Visit [https://www.numbeo.com/common/api.jsp](https://www.numbeo.com/common/api.jsp)
2. Follow the instructions to obtain your API key
3. Note: API access may require a subscription

## Project Structure

```
numbeo-api-java/
├── pom.xml
├── README.md
├── LICENSE
└── src/
    └── main/
        └── java/
            └── com/
                └── numbeo/
                    └── api/
                        ├── NumbeoApiClient.java        # Main application class
                        ├── model/
                        │   ├── Item.java               # Item model
                        │   ├── ItemsResponse.java      # Items API response
                        │   ├── Price.java              # Price model
                        │   └── CityPricesResponse.java # City prices API response
                        └── service/
                            └── NumbeoApiService.java   # API service layer
```

## Building the Application

### Using Maven

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd numbeo-api-java
   ```

2. Build the project:
   ```bash
   mvn clean package
   ```

   This will create an executable JAR file in the `target/` directory:
   - `numbeo-api-client-1.0.0-jar-with-dependencies.jar`

## Running the Application

### Command Line Usage

```bash
java -jar target/numbeo-api-client-1.0.0-jar-with-dependencies.jar <API_KEY> <CITY> <COUNTRY>
```

### Arguments

- `API_KEY` - Your Numbeo API key (required)
- `CITY` - City name, including state/province if applicable (required)
- `COUNTRY` - Country name (required)

### Examples

#### San Francisco, USA
```bash
java -jar target/numbeo-api-client-1.0.0-jar-with-dependencies.jar your_api_key "San Francisco, CA" "United States"
```

#### London, UK
```bash
java -jar target/numbeo-api-client-1.0.0-jar-with-dependencies.jar your_api_key "London" "United Kingdom"
```

#### Tokyo, Japan
```bash
java -jar target/numbeo-api-client-1.0.0-jar-with-dependencies.jar your_api_key "Tokyo" "Japan"
```

## Sample Output

```
================================================================================
NUMBEO API CLIENT - City Price Information
================================================================================

Fetching available items...
Total items available: 54

Fetching prices for San Francisco, CA, United States...

================================================================================
PRICE DATA FOR: San Francisco, CA, United States
================================================================================

--------------------------------------------------------------------------------
RESTAURANTS
--------------------------------------------------------------------------------
  Meal, Inexpensive Restaurant                      Avg: $25.00       Range: $15.00 - $40.00 (123 data points)
  Meal for 2 People, Mid-range Restaurant           Avg: $100.00      Range: $60.00 - $150.00 (98 data points)
  McMeal at McDonalds                               Avg: $12.00       Range: $10.00 - $15.00 (87 data points)

--------------------------------------------------------------------------------
MARKETS
--------------------------------------------------------------------------------
  Milk (regular), (1 liter)                         Avg: $1.25        Range: $0.90 - $2.00 (145 data points)
  Loaf of Fresh White Bread (500g)                  Avg: $4.50        Range: $3.00 - $6.50 (112 data points)
  ...

[Additional categories displayed in order]
```

## API Endpoints Used

### 1. Items Endpoint
```
GET https://www.numbeo.com/api/items?api_key={API_KEY}
```
Returns all available items with their categories.

### 2. City Prices Endpoint
```
GET https://www.numbeo.com/api/city_prices?city={CITY}&country={COUNTRY}&api_key={API_KEY}
```
Returns price data for a specific city.

## Dependencies

- **Gson 2.10.1** - JSON parsing library by Google
- **JUnit 4.13.2** - Testing framework (test scope)

## Error Handling

The application handles the following error scenarios:

- Missing or invalid API key
- Missing city or country parameters
- Network connectivity issues
- Invalid API responses
- HTTP error codes from the API
- Timeout after 30 seconds

## Development

### Running Tests

```bash
mvn test
```

### Compiling Without Tests

```bash
mvn clean package -DskipTests
```

### Running from IDE

You can import this Maven project into your favorite IDE (IntelliJ IDEA, Eclipse, VS Code) and run the `NumbeoApiClient` class directly. Make sure to configure the program arguments in your run configuration.

### Debug Mode

To see the raw JSON response from the Numbeo API (useful for troubleshooting):

```bash
java -Dnumbeo.debug=true -jar target/numbeo-api-client-1.0.0-jar-with-dependencies.jar your_api_key "San Francisco, CA" "United States"
```

This will print the raw JSON response before parsing, which helps identify any field name mismatches.

## API Documentation

For complete API documentation, visit:
- [Numbeo API Documentation](https://www.numbeo.com/api/doc.jsp)
- [Numbeo API Key Registration](https://www.numbeo.com/common/api.jsp)

## License

This project is licensed under the terms specified in the LICENSE file.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## Notes

- The Numbeo API may have rate limits depending on your subscription level
- Price data is crowdsourced and updated regularly by Numbeo
- Some cities may have limited data available
- Currency is typically in the local currency of the requested city

## Troubleshooting

### Issue: "API key cannot be null or empty"
**Solution**: Ensure you're passing your API key as the first argument.

### Issue: HTTP 401 or 403 errors
**Solution**: Verify your API key is valid and has not expired. Check your Numbeo API subscription status.

### Issue: No price data available
**Solution**: The city may not have sufficient data in Numbeo's database. Try a larger, more popular city.

### Issue: Connection timeout
**Solution**: Check your internet connection. The application times out after 30 seconds.

## Support

For issues related to:
- **This application**: Open an issue in this repository
- **Numbeo API**: Contact Numbeo support at https://www.numbeo.com/

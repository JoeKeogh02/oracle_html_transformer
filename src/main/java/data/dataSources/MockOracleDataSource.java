package data.dataSources;

import com.mashape.unirest.http.exceptions.UnirestException;

import model.network.NetworkConfig;

import java.util.HashMap;
import java.util.Map;

public class MockOracleDataSource extends OracleDataSource {
    private Map<Integer, String> mockData;

    public MockOracleDataSource(NetworkConfig config) {
        super(config);
        initialiseMockData();
    }

    // Initialize the mock data with 10 different IDs and HTML content
    private void initialiseMockData() {
        mockData = new HashMap<>();
        mockData.put(1, "<html><body><p>Content for Answer 1</p></body></html>");
        mockData.put(2, "<html><body><p>Content for Answer 2</p></body></html>");
        mockData.put(3, "<html><body><p>Content for Answer 3</p></body></html>");
        mockData.put(4, "<html><body><p>Content for Answer 4</p></body></html>");
        mockData.put(5, "<html><body><p>Content for Answer 5</p></body></html>");
        mockData.put(6, "<html><body><p>Content for Answer 6</p></body></html>");
        mockData.put(7, "<html><body><p>Content for Answer 7</p></body></html>");
        mockData.put(8, "<html><body><p>Content for Answer 8</p></body></html>");
        mockData.put(9, "<html><body><p>Content for Answer 9</p></body></html>");
        mockData.put(10, "<html><body><p>Content for Answer 10</p></body></html>");
    }

    @Override
    public String fetchAnswer(int answerId) throws UnirestException {
        // Simulate fetching from the mock data
        if (!mockData.containsKey(answerId)) {
            throw new UnirestException("Answer ID not found in mock data.");
        }
        
        // Check rate limit
        if (!getNetworkConfig().canMakeRequest()) {
            throw new UnirestException("API rate limit exceeded. Please try again later.");
        }
        
        return mockData.get(answerId);
    }

    @Override
    public void updateAnswer(int answerId, String html) throws UnirestException {
        // Simulate updating the mock data
        if (!mockData.containsKey(answerId)) {
            throw new UnirestException("Answer ID not found in mock data.");
        }
        
        // Check rate limit
        if (!getNetworkConfig().canMakeRequest()) {
            throw new UnirestException("API rate limit exceeded. Please try again later.");
        }
        mockData.put(answerId, html);
    }
}

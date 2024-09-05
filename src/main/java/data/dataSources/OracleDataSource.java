package data.dataSources;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import model.network.NetworkConfig;

public class OracleDataSource implements IOracleDataSource {
	private String ENDPOINT = "/services/rest/connect/v1.4/answers/";
    private NetworkConfig config;

    public OracleDataSource(NetworkConfig config) {
        setNetworkConfig(config);
    }

    @Override
    public String fetchAnswer(int answerId) throws UnirestException {
        if (!checkConnection()) throw new UnirestException("User details are not set"); // Checking if the user is signed in
        
        // Check rate limit
        if (!config.canMakeRequest()) {
            throw new UnirestException("API rate limit exceeded. Please try again later.");
        }

        // Fetching the answer by the id
        HttpResponse<JsonNode> jsonResponse = Unirest.get(config.getHost() + ENDPOINT + answerId)
                .basicAuth(config.getUser().username(), config.getUser().password())
                .header("OSvC-CREST-Application-Context", "This is a valid request for account")
                .asJson();
        JSONObject jsonBody = jsonResponse.getBody().getObject();
        
        return jsonBody.optString("solution");
    }

    @Override
    public void updateAnswer(int answerId, String html) throws UnirestException {
        if (!checkConnection()) throw new UnirestException("User details are not set"); // Checking if the user is signed in

        // Check rate limit
        if (!config.canMakeRequest()) {
            throw new UnirestException("API rate limit exceeded. Please try again later.");
        }

        // Preparing the JSON body for the update request
        JSONObject body = new JSONObject();
        body.put("solution", html); // Update the "solution" field with new HTML content

        // Sending the PUT request to update the answer
        HttpResponse<JsonNode> jsonResponse = Unirest.put(config.getHost() + ENDPOINT + answerId)
                .basicAuth(config.getUser().username(), config.getUser().password())
                .header("Content-Type", "application/json") // Set the content type as JSON
                .header("OSvC-CREST-Application-Context", "This is a valid request for account")
                .body(body) // Attach the JSON body to the request
                .asJson();

        // Check if the update was successful
        if (jsonResponse.getStatus() != 200) {
            throw new UnirestException("Failed to update answer: " + jsonResponse.getStatusText());
        }
    }

    @Override
    public void setNetworkConfig(NetworkConfig newConfig) {
        if (newConfig == null) {
            newConfig = new NetworkConfig(null, null, 0);
        }

        // Preserve rate limiting state
        if (this.config != null) {
            newConfig.setRequestsMade(this.config.getRequestsMade());
            newConfig.setLastRequestTime(this.config.getLastRequestTime());
        }

        this.config = newConfig;
    }

    private Boolean checkConnection() {    
        return config != null && config.getUser() != null;
    }

    @Override
    public NetworkConfig getNetworkConfig() {
        return config;
    }
}


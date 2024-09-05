package data.dataSources;

import com.mashape.unirest.http.exceptions.UnirestException;

import model.network.NetworkConfig;

public interface IOracleDataSource {
	public String fetchAnswer(int answerId) throws UnirestException;
	public void updateAnswer(int answerId, String html) throws UnirestException;
	
	public void setNetworkConfig(NetworkConfig config);
	public NetworkConfig getNetworkConfig();
}

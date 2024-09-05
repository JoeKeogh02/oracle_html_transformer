package data.repositories;

import com.mashape.unirest.http.exceptions.UnirestException;

import model.network.NetworkConfig;
import model.network.OracleResponse;
import model.network.User;

public interface IOracleRepository {
	public OracleResponse fetchAnswer(int answerId) throws UnirestException;
	public void updateAnswer(int answerId, String html) throws UnirestException;
	
	public User getUser();
	public void setUser(User user);
	
	public NetworkConfig getNetworkConfig();
	public void setNetworkConfig(NetworkConfig config);
}
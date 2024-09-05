package data.repositories;

import com.mashape.unirest.http.exceptions.UnirestException;

import data.dataSources.IOracleDataSource;
import model.network.NetworkConfig;
import model.network.OracleResponse;
import model.network.User;

public class OracleRepository implements IOracleRepository {
	final private IOracleDataSource dataSource;
	
	public OracleRepository(IOracleDataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public OracleResponse fetchAnswer(int answerId) throws UnirestException {
		String html = dataSource.fetchAnswer(answerId);
		
		return new OracleResponse(answerId, html);
	}

	@Override
	public void updateAnswer(int answerId, String html) throws UnirestException {
		dataSource.updateAnswer(answerId, html);
		
	}

	@Override
	public void setUser(User user) {
		NetworkConfig config = dataSource.getNetworkConfig();
		
		dataSource.setNetworkConfig(new NetworkConfig(user, config.getHost(), config.getMaxRequestsPerMinute()));
	}

	@Override
	public User getUser() {
		return dataSource.getNetworkConfig().getUser();
	}

	@Override
	public NetworkConfig getNetworkConfig() {
		return dataSource.getNetworkConfig();
	}

	@Override
	public void setNetworkConfig(NetworkConfig config) {
		dataSource.setNetworkConfig(config);
	}
}

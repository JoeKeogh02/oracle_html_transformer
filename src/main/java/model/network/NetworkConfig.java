package model.network;

public class NetworkConfig {
    private String host;
    private User user;
    private int maxRequestsPerMinute;
    private int requestsMade;
    private long lastRequestTime;

    public NetworkConfig(User user, String host, int maxRequestsPerMinute) {
    	if (maxRequestsPerMinute < 0) maxRequestsPerMinute = 0;
        this.host = host;
        this.user = user;
        this.maxRequestsPerMinute = maxRequestsPerMinute;
        this.requestsMade = 0;
        this.lastRequestTime = System.currentTimeMillis();
    }

    public String getHost() {
        return host;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getMaxRequestsPerMinute() {
        return maxRequestsPerMinute;
    }

    public void setMaxRequestsPerMinute(int maxRequestsPerMinute) {
        this.maxRequestsPerMinute = maxRequestsPerMinute;
    }

    public int getRequestsMade() {
        return requestsMade;
    }

    public void setRequestsMade(int requestsMade) {
        this.requestsMade = requestsMade;
    }

    public long getLastRequestTime() {
        return lastRequestTime;
    }

    public void setLastRequestTime(long lastRequestTime) {
        this.lastRequestTime = lastRequestTime;
    }

    public synchronized boolean canMakeRequest() {
        long currentTime = System.currentTimeMillis();
        if ((currentTime - lastRequestTime) / 1000 >= 60) { // If a minute has passed
            requestsMade = 0;
            lastRequestTime = currentTime;
        }

        if (requestsMade >= maxRequestsPerMinute) {
            return false;
        } 
        
        requestsMade++;
        
        return false;
    }
}




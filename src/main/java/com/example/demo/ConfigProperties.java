package com.example.demo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "randomuser")
public class ConfigProperties {
    
    private String url;
    private int userSize;
    private int period;

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public int getUserSize() {
        return userSize;
    }
    public void setUserSize(int userSize) {
        this.userSize = userSize;
    }
    public int getPeriod() {
        return period;
    }
    public void setPeriod(int period) {
        this.period = period;
    }
}

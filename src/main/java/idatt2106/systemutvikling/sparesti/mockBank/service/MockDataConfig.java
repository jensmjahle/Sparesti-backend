package idatt2106.systemutvikling.sparesti.mockBank.service;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Configuration class for the mock data. This class is used to configure the mock data and is
 * determined by the application.properties file.
 */
@Component
@ConfigurationProperties(prefix = "mock.data")
@EnableAutoConfiguration
public class MockDataConfig {

  private boolean enabled;

  /**
   * Method to check if the mock data is enabled.
   *
   * @return boolean true if the mock data is enabled, false otherwise
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * Method to set the mock data enabled.
   *
   * @param enabled the boolean value to set the mock data enabled
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }
}
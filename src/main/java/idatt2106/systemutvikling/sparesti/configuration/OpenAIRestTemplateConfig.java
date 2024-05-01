package idatt2106.systemutvikling.sparesti.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class for RestTemplate with OpenAI API key.
 */
@Configuration
public class OpenAIRestTemplateConfig {

  @Value("${openai.api.key}")
  private String openaiApiKey;

  /**
   * Configures a RestTemplate bean with the OpenAI API key as an Authorization header.
   *
   * @return a RestTemplate bean with the OpenAI API key as an Authorization header
   */
  @Bean
  @Qualifier("openaiRestTemplate")
  public RestTemplate openaiRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    restTemplate.getInterceptors().add((request, body, execution) -> {
      request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
      return execution.execute(request, body);
    });
    return restTemplate;
  }
}


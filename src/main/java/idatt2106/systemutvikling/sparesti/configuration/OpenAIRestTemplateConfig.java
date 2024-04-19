package idatt2106.systemutvikling.sparesti.configuration;

import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class OpenAIRestTemplateConfig {
  Logger logger = Logger.getLogger(OpenAIRestTemplateConfig.class.getName());

  @Value("${openai.api.key}")
  private String openaiApiKey;


  @Bean
  @Qualifier("openaiRestTemplate")
  public RestTemplate openaiRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    logger.info("OpenAI API Key: " + openaiApiKey);
    logger.info(restTemplate.toString());
    restTemplate.getInterceptors().add((request, body, execution) -> {
     // request.getHeaders().add("Authorization", "Bearer " + openaiApiKey);
      return execution.execute(request, body);
    });
    return restTemplate;
  }
}


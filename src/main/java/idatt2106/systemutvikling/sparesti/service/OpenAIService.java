package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.exceptions.OpenAIException;
import idatt2106.systemutvikling.sparesti.model.openAI.ChatResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service class for handling the OpenAI API.
 */
@Service
public class OpenAIService {

  @Qualifier("openaiRestTemplate")
  private RestTemplate restTemplate;

  @Value("${openai.model}")
  private String model;

  @Value("${openai.api.url}")
  private String apiUrl;

  Logger logger = Logger.getLogger(OpenAIService.class.getName());

  @Autowired
  public OpenAIService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  /**
   * Method to chat with OpenAI. This method sends a prompt to OpenAI and returns the response. The
   * prompt should be a string that the AI can respond to.
   *
   * @param prompt the prompt to chat with
   * @return the response from OpenAI
   */
  public String chat(String prompt) {
    logger.info("Received request to chat with OpenAI.");
    try {
      // Constructing a sample request
      Map<String, Object> request = new HashMap<>();
      request.put("model", model);

      List<Map<String, String>> messages = new ArrayList<>();
      Map<String, String> message = new HashMap<>();
      message.put("role", "user");
      message.put("content", prompt);
      messages.add(message);

      request.put("messages", messages);

      // Setting up headers
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);

      // Constructing the request entity
      HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(request, headers);

      // Calling the OpenAI service
      ChatResponse response = restTemplate.postForObject(apiUrl, requestEntity, ChatResponse.class);

      assert response != null;
      return response.getChoices().get(0).getMessage().getContent();

    } catch (Exception e) {
      logger.warning("Failed to call OpenAI service.");
      throw new OpenAIException("Failed to call OpenAI service.");
    }
  }
}
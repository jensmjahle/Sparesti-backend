package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.exceptions.OpenAIException;
import idatt2106.systemutvikling.sparesti.model.openAI.ChatRequest;
import idatt2106.systemutvikling.sparesti.model.openAI.ChatResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

  public String chat(String prompt) {
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

      // Calling the API
      ResponseEntity<Map> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST,
          requestEntity, Map.class);

      // Extracting response
      Map<String, Object> responseBody = responseEntity.getBody();

      if (responseBody == null || !responseBody.containsKey("choices")) {
        return "No response";
      }

      // Extracting the first response
      List<Map<String, Object>> choices = (List<Map<String, Object>>) responseBody.get("choices");
      if (choices.isEmpty()) {
        return "No response";
      }

      Map<String, Object> firstChoice = choices.get(0);
      if (!firstChoice.containsKey("message")) {
        return "No response";
      }

      Map<String, Object> messageContent = (Map<String, Object>) firstChoice.get("message");
      if (!messageContent.containsKey("content")) {
        return "No response";
      }

      return (String) messageContent.get("content");
    } catch (Exception e) {
      logger.warning("Failed to call OpenAI service.");
      throw new OpenAIException("Failed to call OpenAI service.");
    }
  }
}
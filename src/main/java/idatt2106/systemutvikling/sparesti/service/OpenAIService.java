package idatt2106.systemutvikling.sparesti.service;

import idatt2106.systemutvikling.sparesti.model.ChatRequest;
import idatt2106.systemutvikling.sparesti.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
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

  @Autowired
  public OpenAIService(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public String chat(String prompt) {
    // create a request
    ChatRequest request = new ChatRequest(model, prompt);

    // call the API
    ChatResponse response = restTemplate.postForObject(apiUrl, request, ChatResponse.class);

    if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
      return "No response";
    }

    // return the first response
    return response.getChoices().get(0).getMessage().getContent();
  }
}
package idatt2106.systemutvikling.sparesti.model.openAI;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatRequest {

  private String model;
  private List<Message> messages;
  private int n;
  private double temperature;

  public ChatRequest(String model, String prompt) {
    this.model = model;

    this.messages = new ArrayList<>();
    this.messages.add(new Message("user", prompt));
  }

}

package idatt2106.systemutvikling.sparesti.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatResponse {

  private List<Choice> choices;

  // constructors, getters and setters

  @Getter
  @Setter
  @AllArgsConstructor
  public static class Choice {

    private int index;
    private Message message;

    // constructors, getters and setters
  }
}

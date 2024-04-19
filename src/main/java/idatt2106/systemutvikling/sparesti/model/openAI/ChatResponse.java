package idatt2106.systemutvikling.sparesti.model.openAI;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

  private List<Choice> choices;

  // constructors, getters and setters

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Choice {

    private int index;
    private Message message;



    // constructors, getters and setters
  }
}

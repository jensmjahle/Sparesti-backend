package idatt2106.systemutvikling.sparesti.model.openAI;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class for the ChatResponse entity.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatResponse {

  private List<Choice> choices;

  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Choice {

    private int index;
    private Message message;

  }
}

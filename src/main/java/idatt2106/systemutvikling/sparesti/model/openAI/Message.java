package idatt2106.systemutvikling.sparesti.model.openAI;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {

  private String role;
  private String content;

  // constructor, getters and setters
}

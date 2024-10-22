package idatt2106.systemutvikling.sparesti.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object for ManualSaving
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ManualSavingDTO {

  private Long milestoneId;

  private Long amount;
}

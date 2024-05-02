package idatt2106.systemutvikling.sparesti.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data transfer object for PaginatedRequest
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedRequestDTO {

    int pageNum;

    int pageSize;
}

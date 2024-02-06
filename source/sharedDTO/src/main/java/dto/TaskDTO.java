package dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public record TaskDTO(Long id, String name, Date startDate, Date endDate, Date completionDate,
                      Boolean completed) implements Serializable {
}
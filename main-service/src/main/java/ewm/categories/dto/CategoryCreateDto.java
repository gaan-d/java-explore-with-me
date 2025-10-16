package ewm.categories.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryCreateDto {
    @NotBlank
    @Size(max = 50)
    private String name;
}

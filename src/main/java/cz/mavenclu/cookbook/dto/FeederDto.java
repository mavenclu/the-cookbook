package cz.mavenclu.cookbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Schema(name = "Feeder")
@Validated
@Data
public class FeederDto {

    @Schema(description = "first name of a feeder")
    @NotNull
    @Size(min = 2, max = 20)
    private String name;

}

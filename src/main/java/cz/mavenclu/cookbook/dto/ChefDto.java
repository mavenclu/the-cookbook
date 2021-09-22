package cz.mavenclu.cookbook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChefDto {
    @JsonProperty("sub")
    private String id;
    @JsonProperty("email")
    private String username;

}

package cz.mavenclu.cookbook.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Feeder Response DTO")
public class FeederResponseDto {

    @Schema(name = "feeder's ID")
    private long id;
    @Schema(name = "feeder's name")
    private String name;
    @Schema(name = "ID of a feeder's chef")
    private String chefId;
}

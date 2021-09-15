package cz.mavenclu.cookbook.rest.controller;

import cz.mavenclu.cookbook.dto.FeederDto;
import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.dto.IngredientResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "feeder", description = "Feeder API")
@RequestMapping("/cookbook")
public interface FeederApi {


    @Operation(
            summary = "Add new feeder", description = "Add new feeder",
            tags = {"feeder"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Feeder created",
                content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = IngredientResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Log in to be able to send a request")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/feeders")
    FeederResponseDto addNewFeeder(
            @Parameter(description = "Feeder to create", required = true)
            @Valid @RequestBody FeederDto feeder);


    @Operation(
            summary = "Update Feeder", description = "Update existent Feeder",
            tags = {"feeder"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feeder updated",
                content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = FeederResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Bad input"),
            @ApiResponse(responseCode = "401", description = "Unauthorized.")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/feeders/{id}")
    FeederResponseDto updateFeedersName(
            @Parameter(description = "ID of a feeder to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Feeder to update", required = true)
            @Valid @RequestBody FeederDto feeder);


    @Operation(
            summary = "Find all chef's feeders", description = "Find all feeders of a logged in user",
            tags = {"feeder"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all Feeders",
                content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FeederResponseDto.class)))}),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/feeders")
    List<FeederResponseDto> getAllFeeders();

    @Operation(
            summary = "Find feeder by ID", description = "Find Feeder by ID",
            tags = {"feeder"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Feeder by ID",
                content = {@Content(mediaType = "application/json",
                schema = @Schema(implementation = FeederResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Feeder not found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    @GetMapping("/feeder/{id}")
    FeederResponseDto getFeeder(@Parameter(description = "ID of a feeder to find", required = true) @PathVariable Long id);


    @Operation(
            summary = "Delete feeder",
            tags = {"feeder"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feeder was deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request."),
            @ApiResponse(responseCode = "404", description = "Feeder with requested ID was not found")

    })
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/feeder/{id}")
    void delete(@Parameter (description = "ID of a feeder to delete", required = true) @PathVariable Long id);




}

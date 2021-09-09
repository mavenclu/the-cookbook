package cz.mavenclu.cookbook.rest.controller;

import cz.mavenclu.cookbook.dto.IngredientDto;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "ingredient", description = "Ingredient API")
@RequestMapping("/cookbook")
public interface IngredientRestApi {

    @Operation(
            summary = "Find All Ingredients", description = "Find All Ingredients",
            tags = {"ingredient"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredients found",
                content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = IngredientResponseDto.class))))
    })
    @GetMapping("/ingredients")
    @ResponseStatus(HttpStatus.OK)
    List<IngredientResponseDto> getAllIngredients();

    @Operation(
            summary = "Find Ingredient by ID", description = "Find Ingredient by ID",
            tags = {"ingredient"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredient found",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientResponseDto.class))),
            @ApiResponse(responseCode = "404", description = "Ingredient not found")
    })
    @GetMapping("/ingredients/{id}")
    IngredientResponseDto getIngredient(@Parameter(description = "ID of ingredient to find", required = true) @PathVariable Long id);


    @Operation(
            summary = "Add New Ingredient", description = "Add New Ingredient",
            tags = {"ingredient"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added new ingredient",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad input")

    })
    @PostMapping("/ingredients")
    @ResponseStatus(HttpStatus.CREATED)
    IngredientResponseDto addIngredient(@Valid @RequestBody IngredientDto ingredientDto);

    @Operation(
            summary = "Update Ingredient", description = "Update Ingredient",
            tags = {"ingredient"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredient updated",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = IngredientResponseDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad input")
    })
    @PutMapping("/ingredients/update/{id}")
    IngredientResponseDto updateIngredient(
            @Parameter(description = "Ingredient updates", required = true) @Valid @RequestBody IngredientDto ingredientDto,
            @Parameter(description = "ID of the ingredient to update", required = true) @PathVariable Long id );

}

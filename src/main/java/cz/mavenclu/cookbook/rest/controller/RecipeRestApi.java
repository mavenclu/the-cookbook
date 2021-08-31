package cz.mavenclu.cookbook.rest.controller;


import cz.mavenclu.cookbook.dto.RecipeDto;
import cz.mavenclu.cookbook.dto.RecipeResponseDto;
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

@Tag(name = "recipe", description = "Recipe API")
@RequestMapping("/cookbook")
public interface RecipeRestApi {


    @Operation(
            summary = "Add new recipe", description = "Add new recipe",
            tags = {"recipe"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Recipe created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Something went wrong."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Most likely request sent without authorization")
    })
    @PostMapping("/recipes")
    @ResponseStatus(HttpStatus.CREATED)
    RecipeResponseDto addNewRecipe(
            @Parameter(description = "Add new recipe", required = true)
            @Valid @RequestBody RecipeDto recipeDto);


    @Operation(
            summary = "Update recipe", description = "Update recipe",
            tags = {"recipe"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Something went wrong."),
            @ApiResponse(responseCode = "401", description = "Unauthorized. Most likely request sent without authorization")
    })
    @PutMapping("/recipes/update/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    RecipeResponseDto updateRecipe(
            @Parameter(description = "ID of a recipe to update", required = true)@PathVariable Long id,
            @Parameter(description = "Recipe updates", required = true) @Valid @RequestBody RecipeDto recipeDto);


    @Operation(
            summary = "Find recipe by ID", description = "Find recipe by ID",
            tags = {"recipe"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe found",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RecipeResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Recipe not found")

    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/recipes/{id}")
    RecipeResponseDto getRecipe(
            @Parameter(description = "ID of the recipe to get", required = true)
            @PathVariable Long id);

    @Operation(
            summary = "Find all recipes", description = "Find all recipes",
            tags = {"recipe"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipes found",
                    content = {@Content(array = @ArraySchema(schema = @Schema(implementation = RecipeResponseDto.class)))})
    })
    @GetMapping("/recipes")
    @ResponseStatus(HttpStatus.OK)
    List<RecipeResponseDto> getAllRecipes();
}


package cz.mavenclu.cookbook.rest.controller;

import cz.mavenclu.cookbook.dto.ChefDto;
import cz.mavenclu.cookbook.dto.FeederDto;
import cz.mavenclu.cookbook.dto.FeederResponseDto;
import cz.mavenclu.cookbook.dto.IngredientResponseDto;
import cz.mavenclu.cookbook.entity.Allergen;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "feeder", description = "Feeder API")
@RequestMapping(value = "/cookbook/feeders", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping
    FeederResponseDto addNewFeeder(
            @Parameter(description = "Feeder to create", required = true)
            @Valid @RequestBody FeederDto feeder,
            @RequestAttribute("chef") ChefDto chef);


    @Operation(
            summary = "Update Feeder's name", description = "Update Feeder's name",
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
    @PutMapping("/{id}/update-name")
    FeederResponseDto updateFeedersName(
            @Parameter(description = "ID of a feeder to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Feeder to update", required = true)
            @Valid @RequestBody FeederDto feeder,
            @RequestAttribute("chef") ChefDto chef);


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
    @GetMapping
    List<FeederResponseDto> getAllFeeders(@RequestAttribute("chef") ChefDto chef);

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
    @GetMapping("/{id}")
    FeederResponseDto getFeeder(@Parameter(description = "ID of a feeder to find", required = true) @PathVariable Long id, @RequestAttribute("chef") ChefDto chef);


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
    @DeleteMapping("/{id}")
    void delete(@Parameter (description = "ID of a feeder to delete", required = true) @PathVariable Long id, @RequestAttribute("chef") ChefDto chef);

    @Operation(
            summary = "Add allergen to feeder",
            tags = {"feeder"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Allergen was added to feeder"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request."),
            @ApiResponse(responseCode = "404", description = "Feeder with requested ID was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/add-allergen")
    void addAllergen(@PathVariable Long id, Allergen allergen, @RequestAttribute("chef") ChefDto chef);

    @Operation(
            summary = "Remove feeder's allergen",
            tags = {"feeder"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Feeder's allergen was removed"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request."),
            @ApiResponse(responseCode = "404", description = "Feeder with requested ID was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/remove-allergen")
    void removeAllergen(@PathVariable Long id, Allergen allergen, @RequestAttribute("chef") ChefDto chef);

    @Operation(
            summary = "Mark recipe as liked by feeder with provided id",
            tags = {"feeder"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe with provided ID was marked as liked by feeder with provided id"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request."),
            @ApiResponse(responseCode = "404", description = "Feeder with requested ID was not found"),
            @ApiResponse(responseCode = "404", description = "Recipe with requested ID was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/like-recipe/{recipeId}")
    void likeRecipe(@PathVariable("id") Long feedersId, @PathVariable("recipeId") Long recipesId, @RequestAttribute("chef") ChefDto chef);

    @Operation(
            summary = "Unmark liked recipe",
            tags = {"feeder"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe with provided ID was removed from liked recipes by feeder with provided id"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request."),
            @ApiResponse(responseCode = "404", description = "Feeder with requested ID was not found"),
            @ApiResponse(responseCode = "404", description = "Recipe with requested ID was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/dislike-recipe/{recipeId}")
    void dislikeRecipe(@PathVariable("id") Long feedersId, @PathVariable("recipeId") Long recipesId, @RequestAttribute("chef") ChefDto chef);

    @Operation(
            summary = "Add Ingrediont to list of food intolerances",
            tags = {"feeder"}
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredient was added to food intolerances."),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request."),
            @ApiResponse(responseCode = "404", description = "Feeder with requested ID was not found"),
            @ApiResponse(responseCode = "404", description = "Ingredient with requested ID was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/add-intolerance/{ingredientId}")
    void addFoodIntolerance(@PathVariable("id") Long feedersId, @PathVariable("ingredientId") Long ingredientId, @RequestAttribute("chef") ChefDto chef);

    @Operation(
            summary = "Remove Ingredient from food intolerances"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ingredient was removed from food intolerances."),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "401", description = "Unauthorized request."),
            @ApiResponse(responseCode = "404", description = "Feeder with requested ID was not found"),
            @ApiResponse(responseCode = "404", description = "Ingredient with requested ID was not found")
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/remove-intolerance/{ingredientId}")
    void removeFoodIntolerance(@PathVariable("id") Long feedersId, @PathVariable("ingredientId") Long ingredientId, @RequestAttribute("chef") ChefDto chef);

}

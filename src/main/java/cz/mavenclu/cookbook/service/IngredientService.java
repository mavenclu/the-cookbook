package cz.mavenclu.cookbook.service;

import cz.mavenclu.cookbook.dao.IngredientRepository;
import cz.mavenclu.cookbook.dto.IngredientDto;
import cz.mavenclu.cookbook.dto.IngredientResponseDto;
import cz.mavenclu.cookbook.entity.Ingredient;
import cz.mavenclu.cookbook.exception.IngredientNotFoundException;
import cz.mavenclu.cookbook.mapper.IngredientMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class IngredientService {

    private final IngredientRepository ingredientRepo;
    private final IngredientMapper ingredientMapper;

    public IngredientService(IngredientRepository ingredientRepo, IngredientMapper ingredientMapper) {
        this.ingredientRepo = ingredientRepo;
        this.ingredientMapper = ingredientMapper;
    }

    public List<IngredientResponseDto> getAll() {
        log.info("getAll() - find all ingredients");
        List<Ingredient> ingredients = ingredientRepo.findAll();
        log.info("getAll() - found: {}", ingredients);
        log.info("getAll() - mapping found ingredients to IngredientResponseDto");
        List<IngredientResponseDto> ingrDto = ingredientMapper.mapToIngredientResponseDtoList(ingredients);
        log.info("getAll() - mapped to: {}", ingrDto);
        return ingrDto;
    }

    public IngredientResponseDto getIngredient(Long id) {
        log.info("getIngredient - get IngredientResponseDto for ingredient with ID: {}. Calling getById()", id);
        Ingredient ingredient = getById(id);
        log.info("getIngredient() - found: {}", ingredient);
        log.info("getIngredient() - calling ingredient mapper - mapToIngredientResponseDto()");
        IngredientResponseDto responseDto = ingredientMapper.mapToIngredientResponseDto(ingredient);
        log.info("getIngredient() - mapped to: {}", responseDto);
        return responseDto;
    }

    public IngredientResponseDto addIngredient(IngredientDto ingredientDto) {
        log.info("addIngredient() - adding ingredient with param: {}", ingredientDto);
        log.info("addIngredient() - checking if ingredient with given name {} and description{} already exists", ingredientDto.getName(), ingredientDto.getDescription());
        Ingredient ingredient;
        if (! isDuplicate(ingredientDto.getName(), ingredientDto.getDescription())){
            log.info("addIngredient() - no duplicates. will add new ingredient");
            log.info("addIngredient() - calling mapper - mapToIngredient()");
            ingredient = ingredientMapper.mapToIngredient(ingredientDto);
            log.info("addIngredient() - mapped to: {}. Calling save()", ingredient);
            ingredientRepo.save(ingredient);
            log.info("addIngredient() - saved ingredient with ID: {}", ingredient.getId());
        }else {
            ingredient = ingredientRepo.findByNameAndDescription(ingredientDto.getName(), ingredientDto.getDescription()).orElseThrow();
            log.info("addIngredient() - duplicate ingredient. will return existent one from db");
        }
        log.info("addIngredient() - calling mapper to map to IngredientResponseDto");
        IngredientResponseDto responseDto = ingredientMapper.mapToIngredientResponseDto(ingredient);
        log.info("addIngredient() - mapped to: {}", responseDto);
        return responseDto;
    }
    public IngredientResponseDto updateIngredient(IngredientDto ingredientDto, Long id) {
        log.info("updateIngredient() - input params: {} and ID: {}", ingredientDto, id);
        ingredientMapper.updateFromIngredientDto(ingredientDto, getById(id));
        ingredientRepo.save(getById(id));
        log.info("updateIngredient() - updated ingredient");
        log.info("updateIngredient() - mapping to IngredientResponseDto");
        IngredientResponseDto responseDto = ingredientMapper.mapToIngredientResponseDto(getById(id));
        log.info("updateIngredient() - updated to: {}", responseDto);
        return responseDto;
    }


    public Ingredient getById(Long id){
        return ingredientRepo.findById(id).orElseThrow(() -> new IngredientNotFoundException(id));
    }

    private boolean isDuplicate(String name, String description){
        Optional<Ingredient> existing = ingredientRepo.findByNameAndDescription(name, description);
        return existing.isPresent();

    }
}

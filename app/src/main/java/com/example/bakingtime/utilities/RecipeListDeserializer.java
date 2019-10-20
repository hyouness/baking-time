package com.example.bakingtime.utilities;

import com.example.bakingtime.model.Ingredient;
import com.example.bakingtime.model.Recipe;
import com.example.bakingtime.model.ResponseList;
import com.example.bakingtime.model.Step;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.example.bakingtime.utilities.JsonUtils.optDouble;
import static com.example.bakingtime.utilities.JsonUtils.optInt;
import static com.example.bakingtime.utilities.JsonUtils.optLong;
import static com.example.bakingtime.utilities.JsonUtils.optString;

public class RecipeListDeserializer implements JsonDeserializer<ResponseList<Recipe>> {

    private static final String UNIT = "UNIT";

    RecipeListDeserializer() { }

    @Override
    public ResponseList<Recipe> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        List<Recipe> recipes = new ArrayList<>();

        final JsonArray recipesJsonArray = json.getAsJsonArray();
        int size = recipesJsonArray.size();

        for (int i = 0; i < size; i++) {
            JsonObject result = recipesJsonArray.get(i).getAsJsonObject();
            Recipe recipe = getRecipe(result);
            recipes.add(recipe);
        }

        return new ResponseList<>(recipes);
    }

    private static Recipe getRecipe(JsonObject result) {
        long id = optLong(result.get("id"));
        String name = optString(result.get("name"));
        String imageUrl = optString(result.get("image"));
        int servings = optInt(result.get("servings"));

        JsonArray ingredientsJson = result.getAsJsonArray("ingredients");
        List<Ingredient> ingredients = getIngredients(ingredientsJson);

        JsonArray stepsJson = result.getAsJsonArray("steps");
        List<Step> steps = getSteps(stepsJson);

        return Recipe.newRecipe(id)
                .name(name)
                .imageUrl(imageUrl)
                .servings(servings)
                .ingredients(ingredients)
                .steps(steps)
                .build();
    }

    private static List<Ingredient> getIngredients(JsonArray ingredientsJson) {
        int size = ingredientsJson.size();
        List<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            JsonObject ingredientObject = ingredientsJson.get(i).getAsJsonObject();
            double quantity = optDouble(ingredientObject.get("quantity"));
            String measure = optString(ingredientObject.get("measure"));
            String ingredient = optString(ingredientObject.get("ingredient"));
            ingredients.add(new Ingredient(ingredient, getQuantityStr(quantity, measure)));
        }

        return ingredients;
    }

    private static String getQuantityStr(double quantity, String measure) {
        return String.format("%s %s", quantity, measure.equals(UNIT) ? "" : measure);
    }

    private static List<Step> getSteps(JsonArray stepsJson) {
        int size = stepsJson.size();
        List<Step> steps = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            JsonObject stepObject = stepsJson.get(i).getAsJsonObject();
            long id = optLong(stepObject.get("id"));
            String shortDescription = optString(stepObject.get("shortDescription"));
            String description = optString(stepObject.get("description"));
            String videoUrl = optString(stepObject.get("videoURL"));
            steps.add(new Step(id, shortDescription, description, videoUrl));
        }

        return steps;
    }

}

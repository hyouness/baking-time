package com.example.bakingtime.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class Recipe implements Parcelable {
    private long id;
    private String name;
    private int servings;
    private String imageUrl;
    private List<Ingredient> ingredients;
    private List<Step> steps;


    private Recipe() {

    }

    private Recipe(Parcel in) {
        id = in.readLong();
        name = in.readString();
        servings = in.readInt();
        imageUrl = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Step.CREATOR);
    }


    public static Builder newRecipe(long id) {
        return new Builder(id);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeInt(servings);
        dest.writeString(imageUrl);
        dest.writeTypedList(steps);
        dest.writeTypedList(ingredients);
    }

    public static class Builder {
        private final long id;
        private String name;
        private int servings;
        private String imageUrl;
        private List<Ingredient> ingredients;
        private List<Step> steps;

        Builder (long id) {
            this.id = id;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder servings(int servings) {
            this.servings = servings;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder ingredients(List<Ingredient> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public Builder steps(List<Step> steps) {
            this.steps = steps;
            return this;
        }

        public Recipe build() {
            Recipe recipe = new Recipe();
            recipe.id = id;
            recipe.name = name;
            recipe.servings = servings;
            recipe.imageUrl = imageUrl;
            recipe.steps = steps;
            recipe.ingredients = ingredients;
            return recipe;
        }
    }

}

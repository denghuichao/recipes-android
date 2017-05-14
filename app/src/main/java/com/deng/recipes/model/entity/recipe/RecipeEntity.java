package com.deng.recipes.model.entity.recipe;


import java.io.Serializable;
import java.util.List;

/**
 * Created by hcdeng on 2017/4/21.
 */
public class RecipeEntity implements Serializable{

    private  String id;

    private Recipe recipe;

    private  List<CookStep> cookSteps;

    public RecipeEntity() {
    }

    public RecipeEntity(String ID, Recipe recipe, List<CookStep> cookSteps) {
        this.id = ID;
        this.recipe = recipe;
        this.cookSteps = cookSteps;
    }


    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<CookStep> getCookSteps() {
        return cookSteps;
    }

    public void setCookSteps(List<CookStep> cookSteps) {
        this.cookSteps = cookSteps;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

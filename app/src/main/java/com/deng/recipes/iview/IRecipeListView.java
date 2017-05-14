package com.deng.recipes.iview;


import com.deng.recipes.model.entity.recipe.RecipeEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface IRecipeListView {

    public void onRecipesUpdateRefreshSuccess(ArrayList<RecipeEntity> list);
    public void onRecipesUpdateRefreshFail(String msg);
    public void onRecipesLoadMoreSuccess(ArrayList<RecipeEntity> list);
    public void onRecipesLoadMoreFail(String msg);
    public void onRecipeUpdateRefreshSuccess(RecipeEntity entity);
    public void onRecipeUpdateRefreshFail(String msg);
}

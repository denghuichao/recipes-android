package com.deng.recipes.iview;


import com.deng.recipes.model.entity.recipe.RecipeEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/22.
 */

public interface IRecipeSearchView {

    public void onCookSearchSuccess(ArrayList<RecipeEntity> list, int totalPages);
    public void onCookSearchFaile(String msg);
    public void onCookSearchEmpty();

}

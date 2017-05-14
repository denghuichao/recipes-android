package com.deng.recipes.iview;


import com.deng.recipes.model.entity.recipe.RecipeEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/22.
 */

public interface IRecipeSearchView {

    public void onCookSearchSuccess(ArrayList<RecipeEntity> list);
    public void onCookSearchFail(String msg);
    public void onCookSearchMoreSuccess(ArrayList<RecipeEntity> list);
    public void onCookSearchMoreFail(String msg);

}

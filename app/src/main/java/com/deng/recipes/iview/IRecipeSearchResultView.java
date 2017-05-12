package com.deng.recipes.iview;


import com.deng.recipes.model.entity.recipe.RecipeEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/22.
 */

public interface IRecipeSearchResultView {

    public void onCookSearchLoadMoreSuccess(ArrayList<RecipeEntity> list);
    public void onCookSearchLoadMoreFaile(String msg);
    public void onCookSearchLoadMoreNoData();

}

package com.deng.recipes.iview;


import com.deng.recipes.model.entity.recipe.RecipeEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface IRecipeListView {

    public void onCookListUpdateRefreshSuccess(ArrayList<RecipeEntity> list);
    public void onCookListUpdateRefreshFail(String msg);
    public void onCookListLoadMoreSuccess(ArrayList<RecipeEntity> list);
    public void onCookListLoadMoreFail(String msg);
}

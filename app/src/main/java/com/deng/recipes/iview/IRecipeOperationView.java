package com.deng.recipes.iview;

import com.deng.recipes.presenter.RecipeOperationPresenter;

/**
 * Created by huichaodeng on 2017/5/14.
 */
public interface IRecipeOperationView {
    public void onRecipeOperationSuccess(RecipeOperationPresenter.Operation operation);
    public void onRecipeOperationFail(RecipeOperationPresenter.Operation operation, String msg);
}

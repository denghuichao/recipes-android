package com.deng.recipes.model.interfaces;

import com.deng.recipes.model.entity.recipe.subscriber.NumberSubscriberResultInfo;
import com.deng.recipes.model.entity.recipe.subscriber.RecipeSubscriberResultInfo;

import rx.Observable;

/**
 * Created by Administrator on 2017/2/20.
 */

public interface IRecipesRespository {

    Observable<RecipeSubscriberResultInfo> getRecommendationRecipes(int pageSize, int pageIndex);

    Observable<RecipeSubscriberResultInfo> searchRecipes(int pageSize, int pageIndex, String queryString);

    Observable<NumberSubscriberResultInfo> getCollectionCountOfRecipe(String id);

    Observable<NumberSubscriberResultInfo> addCollectionCountOfRecipe(String id);

    Observable<NumberSubscriberResultInfo> reduceCollectionCountOfRecipe(String id);

    Observable<NumberSubscriberResultInfo> getLikeCountOfRecipe(String id);

    Observable<NumberSubscriberResultInfo> addLikeCountOfRecipe(String id);

    Observable<NumberSubscriberResultInfo> reduceLikeCountOfRecipe(String id);

    Observable<NumberSubscriberResultInfo> getCookCountOfRecipe(String id);

    Observable<NumberSubscriberResultInfo> addCookCountOfRecipe(String id);

    Observable<NumberSubscriberResultInfo> reduceCookCountOfRecipe(String id);
}

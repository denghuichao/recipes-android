package com.deng.recipes.model.respository;

import com.deng.recipes.model.entity.recipe.subscriber.NumberSubscriberResultInfo;
import com.deng.recipes.model.entity.recipe.subscriber.RecipeSubscriberResultInfo;
import com.deng.recipes.model.interfaces.IRecipesRespository;
import com.deng.recipes.model.interfaces.IRecipesService;
import com.deng.recipes.model.net.RetrofitService;

import rx.Observable;

/**
 * Created by Administrator on 2017/2/17.
 */

public class RecipesRespository implements IRecipesRespository {

    private static RecipesRespository Instance = null;

    public static RecipesRespository getInstance(){
        if(Instance == null)
            Instance = new RecipesRespository();

        return Instance;
    }

    @Override
    public Observable<RecipeSubscriberResultInfo> getRecipeById(String id) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.getRecipeById(id);
    }

    @Override
    public Observable<RecipeSubscriberResultInfo> getRecommendationRecipes(int pageSize, int pageIndex) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.getRecommendationRecipes(pageSize, pageIndex);
    }

    @Override
    public Observable<RecipeSubscriberResultInfo> searchRecipes(int pageSize, int pageIndex, String queryString) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.searchRecipes(pageSize, pageIndex, queryString);
    }

    @Override
    public Observable<NumberSubscriberResultInfo> getCollectionCountOfRecipe(String id) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.getCollectionCountOfRecipe(id);
    }

    @Override
    public Observable<NumberSubscriberResultInfo> addCollectionCountOfRecipe(String id) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.addCollectionCountOfRecipe(id);
    }

    @Override
    public Observable<NumberSubscriberResultInfo> reduceCollectionCountOfRecipe(String id) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.reduceCollectionCountOfRecipe(id);
    }

    //aaaa
    @Override
    public Observable<NumberSubscriberResultInfo> getLikeCountOfRecipe(String id) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.reduceLikeCountOfRecipe(id);
    }

    @Override
    public Observable<NumberSubscriberResultInfo> addLikeCountOfRecipe(String id) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.reduceLikeCountOfRecipe(id);
    }

    @Override
    public Observable<NumberSubscriberResultInfo> reduceLikeCountOfRecipe(String id) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.reduceLikeCountOfRecipe(id);
    }

    @Override
    public Observable<NumberSubscriberResultInfo> getCookCountOfRecipe(String id) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.reduceCookCountOfRecipe(id);
    }

    @Override
    public Observable<NumberSubscriberResultInfo> addCookCountOfRecipe(String id) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.reduceCookCountOfRecipe(id);
    }

    @Override
    public Observable<NumberSubscriberResultInfo> reduceCookCountOfRecipe(String id) {
        IRecipesService iRecipesService = RetrofitService.getInstance().createApi(IRecipesService.class);
        return iRecipesService.reduceCookCountOfRecipe(id);
    }
}

package com.deng.recipes.model.interfaces;

import com.deng.recipes.constants.Constants;
import com.deng.recipes.model.entity.recipe.subscriber.NumberSubscriberResultInfo;
import com.deng.recipes.model.entity.recipe.subscriber.RecipeSubscriberResultInfo;

import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/2/17.
 */

public interface IRecipesService {

    @GET(Constants.Recipe_Service_GET)
    Observable<RecipeSubscriberResultInfo> getRecipeById(@Path(Constants.Recipe_Parameter_ID) String id);

    @GET(Constants.Recipe_Service_Recommendation)
    Observable<RecipeSubscriberResultInfo> getRecommendationRecipes(
            @Query(Constants.Recipe_Parameter_Size) int pageSize,
            @Query(Constants.Recipe_Parameter_Page) int pageIndex);

    @GET(Constants.Recipe_Service_Search)
    Observable<RecipeSubscriberResultInfo> searchRecipes(
            @Query(Constants.Recipe_Parameter_Size) int pageSize,
            @Query(Constants.Recipe_Parameter_Page) int pageIndex,
            @Query(Constants.Recipe_Parameter_QueryString) String queryString);

    @GET(Constants.Recipe_Servic_CollectionQuery)
    Observable<NumberSubscriberResultInfo> getCollectionCountOfRecipe(
            @Query(Constants.Recipe_Parameter_ID) String id);


    @PUT(Constants.Recipe_Servic_Add_Collection)
    Observable<NumberSubscriberResultInfo> addCollectionCountOfRecipe(
            @Query(Constants.Recipe_Parameter_ID) String id);


    @PUT(Constants.Recipe_Servic_Reduce_Collection)
    Observable<NumberSubscriberResultInfo> reduceCollectionCountOfRecipe(
            @Query(Constants.Recipe_Parameter_ID) String id);


    @GET(Constants.Recipe_Servic_LikenessQuery)
    Observable<NumberSubscriberResultInfo> getLikeCountOfRecipe(
            @Query(Constants.Recipe_Parameter_ID) String id);


    @PUT(Constants.Recipe_Servic_Add_Likeness)
    Observable<NumberSubscriberResultInfo> addLikeCountOfRecipe(
            @Query(Constants.Recipe_Parameter_ID) String id);


    @PUT(Constants.Recipe_Servic_Reduce_Likeness)
    Observable<NumberSubscriberResultInfo> reduceLikeCountOfRecipe(
            @Query(Constants.Recipe_Parameter_ID) String id);


    @GET(Constants.Recipe_Servic_CooknessQuery)
    Observable<NumberSubscriberResultInfo> getCookCountOfRecipe(
            @Query(Constants.Recipe_Parameter_ID) String id);


    @PUT(Constants.Recipe_Servic_Add_Cookness)
    Observable<NumberSubscriberResultInfo> addCookCountOfRecipe(
            @Query(Constants.Recipe_Parameter_ID) String id);


    @PUT(Constants.Recipe_Servic_Reduce_Cookness)
    Observable<NumberSubscriberResultInfo> reduceCookCountOfRecipe(
            @Query(Constants.Recipe_Parameter_ID) String id);

}

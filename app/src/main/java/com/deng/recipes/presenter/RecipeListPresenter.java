package com.deng.recipes.presenter;

import android.content.Context;


import com.deng.recipes.iview.IRecipeListView;
import com.deng.recipes.R;
import com.deng.recipes.constants.Constants;
import com.deng.recipes.model.entity.recipe.subscriber.RecipeSubscriberResultInfo;
import com.deng.recipes.model.interfaces.IRecipesRespository;
import com.deng.recipes.model.respository.RecipesRespository;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/20.
 */

public class RecipeListPresenter extends Presenter{

    private IRecipeListView iRecipeListView;
    private IRecipesRespository iRecipesRespository;
    private int curPage = 0;
    private int totalPages = 0;

    public RecipeListPresenter(Context context, IRecipeListView iRecipeListView){
        super(context);

        this.iRecipeListView = iRecipeListView;
        this.iRecipesRespository = RecipesRespository.getInstance();
    }

    public void destroy(){
        if(updateRefreshCookMenuByIDSubscriber != null){
            updateRefreshCookMenuByIDSubscriber.unsubscribe();
        }

        if(loadMoreRecipesSubscriber != null){
            loadMoreRecipesSubscriber.unsubscribe();
        }
    }

    public void updateRefreshRecipes(){
        curPage = 0;
        rxJavaExecuter.execute(
                iRecipesRespository.getRecommendationRecipes(Constants.Per_Page_Size,curPage)
                , updateRefreshCookMenuByIDSubscriber = new UpdateRefreshCookMenuByIDSubscriber()
        );
    }

    public void loadMoreRecipes(){
        curPage++;
        if(curPage > totalPages){
            curPage--;
            if(iRecipeListView != null)
                iRecipeListView.onCookListLoadMoreFail(context.getString(R.string.toast_msg_no_more_data));
            return ;
        }

        rxJavaExecuter.execute(
                iRecipesRespository.getRecommendationRecipes(Constants.Per_Page_Size, curPage)
                , loadMoreRecipesSubscriber = new LoadMoreRecipesSubscriber()
        );
    }

    private LoadMoreRecipesSubscriber loadMoreRecipesSubscriber;
    private class LoadMoreRecipesSubscriber extends Subscriber<RecipeSubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(loadMoreRecipesSubscriber != null){
                loadMoreRecipesSubscriber.unsubscribe();
            }

            if(iRecipeListView != null)
                iRecipeListView.onCookListLoadMoreFail(e.getMessage());

        }

        @Override
        public void onNext(RecipeSubscriberResultInfo data){

            if(iRecipeListView != null)
                iRecipeListView.onCookListLoadMoreSuccess(data.getResult().getList());

            this.onCompleted();
        }
    }

    private UpdateRefreshCookMenuByIDSubscriber updateRefreshCookMenuByIDSubscriber;
    private class UpdateRefreshCookMenuByIDSubscriber extends Subscriber<RecipeSubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(updateRefreshCookMenuByIDSubscriber != null){
                updateRefreshCookMenuByIDSubscriber.unsubscribe();
            }

            if(iRecipeListView != null)
                iRecipeListView.onCookListUpdateRefreshFail(e.getMessage());

        }

        @Override
        public void onNext(RecipeSubscriberResultInfo data){
            totalPages = (data.getResult().getTotal() + Constants.Per_Page_Size - 1) / Constants.Per_Page_Size;

            if(iRecipeListView != null)
                iRecipeListView.onCookListUpdateRefreshSuccess(data.getResult().getList());

            this.onCompleted();
        }
    }
}

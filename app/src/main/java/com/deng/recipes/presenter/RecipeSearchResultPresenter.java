package com.deng.recipes.presenter;

import android.content.Context;

import com.deng.recipes.iview.IRecipeSearchResultView;
import com.deng.recipes.constants.Constants;
import com.deng.recipes.model.entity.recipe.subscriber.RecipeSubscriberResultInfo;
import com.deng.recipes.model.interfaces.IRecipesRespository;
import com.deng.recipes.model.respository.RecipesRespository;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/22.
 */

public class RecipeSearchResultPresenter extends Presenter{

    private IRecipeSearchResultView iRecipeSearchResultView;
    private IRecipesRespository iRecipesRespository;

    private int curPage = 0;
    private int totalPages = 0;
    private String searchKey;

    public RecipeSearchResultPresenter(Context context, String searchKey, int totalPages, IRecipeSearchResultView iRecipeSearchResultView){
        super(context);

        this.searchKey = searchKey;
        this.totalPages = totalPages;

        this.iRecipeSearchResultView = iRecipeSearchResultView;
        this.iRecipesRespository = RecipesRespository.getInstance();
    }

    public void destroy(){
        if(searchRecipesSubscriber != null){
            searchRecipesSubscriber.unsubscribe();
        }
    }

    public void setData(String searchKey, int totalPages){
        this.searchKey = searchKey;
        this.totalPages = totalPages;
    }

    public void loadMore(){
        curPage++;

        if(curPage > totalPages){
            curPage--;
            if(iRecipeSearchResultView != null)
                iRecipeSearchResultView.onCookSearchLoadMoreNoData();

            return ;
        }

        rxJavaExecuter.execute(
                iRecipesRespository.searchRecipes(Constants.Per_Page_Size, curPage, searchKey)
                , searchRecipesSubscriber = new SearchRecipesSubscriber()
        );
    }

    private SearchRecipesSubscriber searchRecipesSubscriber;
    private class SearchRecipesSubscriber extends Subscriber<RecipeSubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(searchRecipesSubscriber != null){
                searchRecipesSubscriber.unsubscribe();
            }

            if(iRecipeSearchResultView != null)
                iRecipeSearchResultView.onCookSearchLoadMoreFaile(e.getMessage());

        }

        @Override
        public void onNext(RecipeSubscriberResultInfo data){

            if(data == null || data.getResult() == null){
                if(iRecipeSearchResultView != null)
                    iRecipeSearchResultView.onCookSearchLoadMoreFaile("找不到相关菜谱");

                this.onCompleted();
                return ;
            }

            totalPages = data.getResult().getTotal();

            if(iRecipeSearchResultView != null) {
                iRecipeSearchResultView.onCookSearchLoadMoreSuccess(data.getResult().getList());
            }

            this.onCompleted();
        }
    }
}

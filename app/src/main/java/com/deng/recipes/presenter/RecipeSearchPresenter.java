package com.deng.recipes.presenter;

import android.content.Context;

import com.deng.recipes.R;
import com.deng.recipes.iview.IRecipeSearchView;
import com.deng.recipes.constants.Constants;
import com.deng.recipes.model.entity.recipe.subscriber.RecipeSubscriberResultInfo;
import com.deng.recipes.model.interfaces.IRecipesRespository;
import com.deng.recipes.model.respository.RecipesRespository;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/22.
 */

public class RecipeSearchPresenter extends Presenter {

    private IRecipeSearchView iRecipeSearchView;
    private IRecipesRespository iRecipesRespository;

    private int curPage = 0;
    private int totalPages = 0;

    public RecipeSearchPresenter(Context context, IRecipeSearchView iRecipeSearchView) {
        super(context);

        this.iRecipeSearchView = iRecipeSearchView;
        this.iRecipesRespository = RecipesRespository.getInstance();
    }

    @Override
    public void destroy() {
        if (searchRecipeSubscriber != null) {
            searchRecipeSubscriber.unsubscribe();
        }
    }

    public void search(String queryString) {
        curPage = 0;
        rxJavaExecuter.execute(
                iRecipesRespository.searchRecipes(Constants.Per_Page_Size, curPage, queryString)
                , searchRecipeSubscriber = new SearchRecipeSubscriber()
        );
    }

    public void searchMore(String queryString) {
        curPage++;
        if(curPage > totalPages){
            curPage--;
            if(iRecipeSearchView != null)
                iRecipeSearchView.onCookSearchMoreFail(context.getString(R.string.toast_msg_no_more_data));
            return ;
        }

        rxJavaExecuter.execute(
                iRecipesRespository.searchRecipes(Constants.Per_Page_Size, curPage, queryString)
                , searchRecipeSubscriber = new SearchRecipeSubscriber()
        );
    }


    private SearchMoreRecipesSubscriber searchMoreRecipesSubscriber;
    private class SearchMoreRecipesSubscriber extends Subscriber<RecipeSubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(searchMoreRecipesSubscriber != null){
                searchMoreRecipesSubscriber.unsubscribe();
            }

            if(iRecipeSearchView != null)
                iRecipeSearchView.onCookSearchMoreFail(e.getMessage());

        }

        @Override
        public void onNext(RecipeSubscriberResultInfo data){

            if(iRecipeSearchView != null)
                iRecipeSearchView.onCookSearchMoreSuccess(data.getResult().getList());

            this.onCompleted();
        }
    }

    private SearchRecipeSubscriber searchRecipeSubscriber;
    private class SearchRecipeSubscriber extends Subscriber<RecipeSubscriberResultInfo> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (searchRecipeSubscriber != null) {
                searchRecipeSubscriber.unsubscribe();
            }


            if (iRecipeSearchView != null)
                iRecipeSearchView.onCookSearchFail(e.getMessage());

        }

        @Override
        public void onNext(RecipeSubscriberResultInfo data) {

            if (data == null || data.getResult() == null) {
                if (iRecipeSearchView != null)
                    iRecipeSearchView.onCookSearchFail("找不到相关菜谱");

                this.onCompleted();
                return;
            }

            int totalPages = (data.getResult().getTotal() + Constants.Per_Page_Size - 1) / Constants.Per_Page_Size;

            if (iRecipeSearchView != null) {

                iRecipeSearchView.onCookSearchSuccess(data.getResult().getList());

            }

            this.onCompleted();
        }
    }
}

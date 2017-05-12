package com.deng.recipes.presenter;

import android.content.Context;

import com.deng.recipes.iview.IRecipeSearchView;
import com.deng.recipes.constants.Constants;
import com.deng.recipes.model.entity.recipe.subscriber.RecipeSubscriberResultInfo;
import com.deng.recipes.model.interfaces.IRecipesRespository;
import com.deng.recipes.model.respository.RecipesRespository;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/2/22.
 */

public class RecipeSearchPresenter extends Presenter{

    private IRecipeSearchView iRecipeSearchView;
    private IRecipesRespository iRecipesRespository;

    public RecipeSearchPresenter(Context context, IRecipeSearchView iRecipeSearchView){
        super(context);

        this.iRecipeSearchView = iRecipeSearchView;
        this.iRecipesRespository = RecipesRespository.getInstance();
    }

    @Override
    public void destroy(){
        if(searchRecipeSubscriber != null){
            searchRecipeSubscriber.unsubscribe();
        }
    }

    public void search(String queryString){
        rxJavaExecuter.execute(
                iRecipesRespository.searchRecipes(Constants.Per_Page_Size, 1, queryString)
                , searchRecipeSubscriber = new SearchRecipeSubscriber()
        );
    }


    private SearchRecipeSubscriber searchRecipeSubscriber;
    private class SearchRecipeSubscriber extends Subscriber<RecipeSubscriberResultInfo> {
        @Override
        public void onCompleted(){

        }

        @Override
        public void onError(Throwable e){
            if(searchRecipeSubscriber != null){
                searchRecipeSubscriber.unsubscribe();
            }


            if(iRecipeSearchView != null)
                iRecipeSearchView.onCookSearchFaile(e.getMessage());

        }

        @Override
        public void onNext(RecipeSubscriberResultInfo data){

            if(data == null || data.getResult() == null){
                if(iRecipeSearchView != null)
                    iRecipeSearchView.onCookSearchFaile("找不到相关菜谱");

                this.onCompleted();
                return ;
            }

            int totalPages = data.getResult().getTotal();

            if(iRecipeSearchView != null) {
                if(data.getResult().getList().size() < 1){
                    iRecipeSearchView.onCookSearchEmpty();
                }
                else {
                    iRecipeSearchView.onCookSearchSuccess(data.getResult().getList(), totalPages);
                }
            }

            this.onCompleted();
        }
    }
}

package com.deng.recipes.presenter;

import android.content.Context;

import com.deng.recipes.constants.Constants;
import com.deng.recipes.iview.IRecipeOperationView;
import com.deng.recipes.model.entity.recipe.subscriber.NumberSubscriberResultInfo;
import com.deng.recipes.model.interfaces.IRecipesRespository;
import com.deng.recipes.model.respository.RecipesRespository;

import rx.Subscriber;

/**
 * Created by huichaodeng on 2017/5/14.
 */
public class RecipeOperationPresenter extends Presenter {
    private IRecipesRespository iRecipesRespository;
    private IRecipeOperationView iRecipeOperationView;
    private Operation curOp;

    public RecipeOperationPresenter(Context context, IRecipeOperationView iRecipeOperationView) {
        super(context);

        this.iRecipeOperationView = iRecipeOperationView;
        this.iRecipesRespository = RecipesRespository.getInstance();
    }

    public void operate(Operation operation, String recipeId){
        this.curOp = operation;

        switch (operation){

            case LIKE:rxJavaExecuter.execute(
                    iRecipesRespository.addLikeCountOfRecipe(recipeId)
                    , recipeOperationSubscriber = new RecipeOperationSubscriber()
            );break;

            case UNLIKE:rxJavaExecuter.execute(
                    iRecipesRespository.reduceLikeCountOfRecipe(recipeId)
                    , recipeOperationSubscriber = new RecipeOperationSubscriber()
            );break;

            case COOK:rxJavaExecuter.execute(
                    iRecipesRespository.addCookCountOfRecipe(recipeId)
                    , recipeOperationSubscriber = new RecipeOperationSubscriber()
            );break;

            case UNCOOK:rxJavaExecuter.execute(
                    iRecipesRespository.reduceCookCountOfRecipe(recipeId)
                    , recipeOperationSubscriber = new RecipeOperationSubscriber()
            );break;

            case COLLECT:rxJavaExecuter.execute(
                    iRecipesRespository.addCollectionCountOfRecipe(recipeId)
                    , recipeOperationSubscriber = new RecipeOperationSubscriber()
            );break;

            case UNCOLLECT:rxJavaExecuter.execute(
                    iRecipesRespository.reduceCollectionCountOfRecipe(recipeId)
                    , recipeOperationSubscriber = new RecipeOperationSubscriber()
            );break;
            default:break;
        }
    }

    private RecipeOperationSubscriber recipeOperationSubscriber;

    private class RecipeOperationSubscriber extends Subscriber<NumberSubscriberResultInfo> {
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
            if (recipeOperationSubscriber != null) {
                recipeOperationSubscriber.unsubscribe();
            }

            if (iRecipeOperationView != null)
                iRecipeOperationView.onRecipeOperationFail(curOp,e.getMessage());

        }

        @Override
        public void onNext(NumberSubscriberResultInfo data) {

            if (iRecipeOperationView != null)
                iRecipeOperationView.onRecipeOperationSuccess(curOp);

            this.onCompleted();
        }
    }

    @Override
    public void destroy() {
        if (recipeOperationSubscriber != null) {
            recipeOperationSubscriber.unsubscribe();
        }
    }

    public static enum Operation{
        LIKE, UNLIKE, COLLECT, UNCOLLECT, COOK, UNCOOK;
    }
}

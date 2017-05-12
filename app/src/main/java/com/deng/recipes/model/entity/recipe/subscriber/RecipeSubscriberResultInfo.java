package com.deng.recipes.model.entity.recipe.subscriber;


import com.deng.recipes.model.entity.recipe.SearchRecipeResultInfo;

/**
 * Created by Administrator on 2017/2/20.
 */

public class RecipeSubscriberResultInfo {

    private String msg;
    private String retCode;
    private SearchRecipeResultInfo result;

    public RecipeSubscriberResultInfo(){

    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public SearchRecipeResultInfo getResult() {
        return result;
    }

    public void setResult(SearchRecipeResultInfo result) {
        this.result = result;
    }
}

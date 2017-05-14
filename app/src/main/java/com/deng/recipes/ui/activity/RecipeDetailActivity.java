package com.deng.recipes.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.OnClick;

import com.deng.recipes.RecipesApplication;
import com.deng.recipes.R;
import com.deng.recipes.Utils;
import com.deng.recipes.iview.IRecipeListView;
import com.deng.recipes.iview.IRecipeOperationView;
import com.deng.recipes.model.entity.recipe.RecipeEntity;
import com.deng.recipes.presenter.RecipeListPresenter;
import com.deng.recipes.presenter.RecipeOperationPresenter;
import com.deng.recipes.ui.adapter.CookStepAdapter;
import com.deng.recipes.ui.adapter.IngredientItemAdapter;
import com.deng.recipes.ui.view.ExpandingListView;
import com.google.common.base.Strings;

import java.util.ArrayList;

/**
 * Created by froger_mcs on 11.11.14.
 */
public class RecipeDetailActivity extends BaseDrawerActivity
        implements IRecipeOperationView, IRecipeListView {
    public static final String ARG_DRAWING_START_LOCATION = "arg_drawing_start_location";

    @BindView(R.id.svRoot)
    ScrollView svRoot;

    @BindView(R.id.contentRoot)
    LinearLayout contentRoot;

    @BindView(R.id.ivImage)
    ImageView ivImage;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvTag)
    TextView tvTag;

    @BindView(R.id.tvCookMethod)
    TextView tvCookMethod;

    @BindView(R.id.tvTimeCost)
    TextView tvTimeCost;

    @BindView(R.id.tvCooked)
    TextView tvCooked;

    @BindView(R.id.tvCollected)
    TextView tvCollected;

    @BindView(R.id.tvLiked)
    TextView tvLiked;

    @BindView(R.id.tvDesc)
    TextView tvDesc;

    @BindView(R.id.rvIngredients)
    ExpandingListView rvIngredients;

    @BindView(R.id.rvSteps)
    ExpandingListView rvSteps;

    @BindView(R.id.tipsRoot)
    LinearLayout tipsRoot;

    @BindView(R.id.tvTips)
    TextView tvTips;

    @BindView(R.id.ibMenu)
    FloatingActionButton actionMenu;

    @BindView(R.id.cvMenuRoot)
    public View viewSheet;

    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private CookStepAdapter stepsAdapter;
    private IngredientItemAdapter ingredientItemAdapter;

    private RecipeEntity recipeEntity;

    private RecipeOperationPresenter recipeOperationPresenter;

    private RecipeListPresenter recipeListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Bundle bundle = getIntent().getExtras();
        recipeEntity = (RecipeEntity) bundle.getSerializable("recipe");
        setUpSwipeRefreshLayout();
        setUpRecipe();

        svRoot.smoothScrollTo(0, 0);
        svRoot.setFocusable(true);

        recipeOperationPresenter = new RecipeOperationPresenter(this, this);
        recipeListPresenter = new RecipeListPresenter(this, this);
    }

    private void setUpSwipeRefreshLayout(){
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        mSwipeRefreshLayout.setProgressViewOffset(true, 50, 200);

        //设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        mSwipeRefreshLayout.setSize(SwipeRefreshLayout.LARGE);


        mSwipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                recipeListPresenter.updateRefreshRecipe(recipeEntity.getId());
            }
        });
    }

    private void setUpRecipe() {
        setupRecipeInfo();
        setupSteps();
        setupIngredients();
    }

    private void setupRecipeInfo() {
        if (recipeEntity.getRecipe().getImages().size() > 0) {
            ((RecipesApplication) this.getApplicationContext()).getImageLoader()
                    .displayImage(recipeEntity.getRecipe().getImages().get(0), ivImage,
                            RecipesApplication.imageOptions()); //
        } else {
            ivImage.setVisibility(View.GONE);
        }

        tvTitle.setText(recipeEntity.getRecipe().getTitle());

        if (recipeEntity.getRecipe().getTags().size() > 0) {
            tvTag.setText(recipeEntity.getRecipe().getTags().iterator().next());
        }

        tvCookMethod.setText(recipeEntity.getRecipe().getCookMethod());
        tvTimeCost.setText(recipeEntity.getRecipe().getCookingTime());

        tvCooked.setText(recipeEntity.getRecipe().getCookedNum() + "人做过");
        tvCollected.setText(recipeEntity.getRecipe().getCookedNum() + "人收藏");
        tvLiked.setText(recipeEntity.getRecipe().getLikedNum() + "人喜欢");

        tvDesc.setText(recipeEntity.getRecipe().getDesc());

        String tips = recipeEntity.getRecipe().getTips();
        if (!Strings.isNullOrEmpty(tips)) {
            tvTips.setText(tips);
            tipsRoot.setVisibility(View.VISIBLE);
        }
    }

    private void setupSteps() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvSteps.setLayoutManager(linearLayoutManager);
        rvSteps.setHasFixedSize(true);

        stepsAdapter = new CookStepAdapter(this, recipeEntity.getCookSteps());
        rvSteps.setAdapter(stepsAdapter);
        rvSteps.setOverScrollMode(View.OVER_SCROLL_NEVER);

    }

    private void setupIngredients() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvIngredients.setLayoutManager(linearLayoutManager);
        rvIngredients.setHasFixedSize(true);
        ingredientItemAdapter = new IngredientItemAdapter(this, recipeEntity.getRecipe().getIngredients());
        rvIngredients.setAdapter(ingredientItemAdapter);
        rvIngredients.setOverScrollMode(View.OVER_SCROLL_NEVER);

    }

    @Override
    public void onBackPressed() {
        svRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        RecipeDetailActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }


    public boolean closeFabMenu(){
        viewSheet.setAnimation(mHiddenAction);
        viewSheet.setVisibility(View.GONE);
        actionMenu.startAnimation(mShowAction);
        actionMenu.setVisibility(View.VISIBLE);

        return false;
    }

    private void openFabMenu(){
        viewSheet.startAnimation(mShowAction);
        viewSheet.setVisibility(View.VISIBLE);
        actionMenu.setAnimation(mHiddenAction);
        actionMenu.setVisibility(View.GONE);
    }

    @OnClick(R.id.ibMenu)
    public void onClickFabMenu(){
       openFabMenu();
    }



    @OnClick(R.id.rvCookedRoot)
    public void onClickCook(){
        closeFabMenu();
        recipeOperationPresenter.operate(RecipeOperationPresenter.Operation.COOK, recipeEntity.getId());
    }

    @OnClick(R.id.rvCollectionRoot)
    public void onClickCollection(){
        closeFabMenu();
        recipeOperationPresenter.operate(RecipeOperationPresenter.Operation.COLLECT, recipeEntity.getId());
    }

    @OnClick(R.id.rvLikeRoot)
    public void onClickLike(){
        closeFabMenu();
        recipeOperationPresenter.operate(RecipeOperationPresenter.Operation.LIKE, recipeEntity.getId());
    }

    @OnClick(R.id.rvCancelRoot)
    public void onClickCancel(){
        closeFabMenu();
    }

    @Override
    public void onRecipeOperationSuccess(RecipeOperationPresenter.Operation operation) {
        Toast.makeText(this, "操作成功", Toast.LENGTH_SHORT).show();
        switch (operation){
            case LIKE:tvLiked.setText(recipeEntity.getRecipe().getLikedNum() + 1 + "人喜欢");
                recipeEntity.getRecipe().setLikedNum(recipeEntity.getRecipe().getLikedNum() + 1);
                break;
            case UNLIKE:tvLiked.setText(recipeEntity.getRecipe().getLikedNum() - 1 + "人喜欢");
                recipeEntity.getRecipe().setLikedNum(recipeEntity.getRecipe().getLikedNum() - 1);
                break;
            case COOK:tvCooked.setText(recipeEntity.getRecipe().getCookedNum() + 1 + "人做过");
                recipeEntity.getRecipe().setCookedNum(recipeEntity.getRecipe().getCookedNum() + 1);
                break;
            case UNCOOK:tvCooked.setText(recipeEntity.getRecipe().getCookedNum() - 1 + "人做过");
                recipeEntity.getRecipe().setCookedNum(recipeEntity.getRecipe().getCookedNum() - 1);
                break;
            case COLLECT:tvCollected.setText(recipeEntity.getRecipe().getCollectedNum() + 1 + "人收藏");
                recipeEntity.getRecipe().setCollectedNum(recipeEntity.getRecipe().getCollectedNum() + 1);
                break;
            case UNCOLLECT:tvCollected.setText(recipeEntity.getRecipe().getCollectedNum() + 1 + "人收藏");
                recipeEntity.getRecipe().setCollectedNum(recipeEntity.getRecipe().getCollectedNum() - 1);
                break;
            default:break;
        }
    }

    @Override
    public void onRecipeOperationFail(RecipeOperationPresenter.Operation operation, String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRecipesUpdateRefreshSuccess(ArrayList<RecipeEntity> list) {

    }

    @Override
    public void onRecipesUpdateRefreshFail(String msg) {

    }

    @Override
    public void onRecipesLoadMoreSuccess(ArrayList<RecipeEntity> list) {

    }

    @Override
    public void onRecipesLoadMoreFail(String msg) {

    }

    @Override
    public void onRecipeUpdateRefreshSuccess(RecipeEntity entity) {
        this.setUpRecipe();
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRecipeUpdateRefreshFail(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}

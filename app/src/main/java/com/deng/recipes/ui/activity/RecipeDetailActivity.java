package com.deng.recipes.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import butterknife.BindView;

import com.deng.recipes.InstaMaterialApplication;
import com.deng.recipes.R;
import com.deng.recipes.Utils;
import com.deng.recipes.entity.RecipeEntity;
import com.deng.recipes.ui.adapter.CookStepAdapter;
import com.deng.recipes.ui.adapter.IngredientItemAdapter;
import com.deng.recipes.ui.view.MyListView;
import com.google.common.base.Strings;

/**
 * Created by froger_mcs on 11.11.14.
 */
public class RecipeDetailActivity extends BaseDrawerActivity {
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
    MyListView rvIngredients;

    @BindView(R.id.rvSteps)
    MyListView rvSteps;

    @BindView(R.id.tipsRoot)
    LinearLayout tipsRoot;

    @BindView(R.id.tvTips)
    TextView tvTips;

    private CookStepAdapter stepsAdapter;
    private IngredientItemAdapter ingredientItemAdapter;
    private int drawingStartLocation;

    private RecipeEntity recipeEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Bundle bundle = getIntent().getExtras();
        recipeEntity = (RecipeEntity) bundle.getSerializable("recipe");
        setupRecipeInfo();
        setupSteps();
        setupIngredients();
        svRoot.smoothScrollTo(0, 0);
        svRoot.setFocusable(true);
    }

    private void setupRecipeInfo() {
        if (recipeEntity.getRecipe().getImages().size() > 0) {
            ((InstaMaterialApplication) this.getApplicationContext()).getImageLoader()
                    .displayImage(recipeEntity.getRecipe().getImages().get(0), ivImage,
                            InstaMaterialApplication.imageOptions()); //
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
        contentRoot.animate()
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

}

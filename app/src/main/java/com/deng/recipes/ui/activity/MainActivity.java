package com.deng.recipes.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import com.deng.recipes.R;
import com.deng.recipes.iview.IRecipeListView;
import com.deng.recipes.model.entity.recipe.RecipeEntity;
import com.deng.recipes.presenter.RecipeListPresenter;
import com.deng.recipes.ui.adapter.RecipeItemAdapter;
import com.deng.recipes.ui.adapter.RecipeItemAnimator;
import com.deng.recipes.ui.view.RecipeContextMenuManager;
import com.google.common.collect.Lists;


public class MainActivity extends BaseDrawerActivity
        implements SwipeRefreshLayout.OnRefreshListener, IRecipeListView
{
    public static final String ACTION_SHOW_LOADING_ITEM = "action_show_loading_item";

    private static final int ANIM_DURATION_TOOLBAR = 300;
    private static final int ANIM_DURATION_FAB = 400;

    @BindView(R.id.rvFeed)
    RecyclerView mRecyclerView;
    @BindView(R.id.content)
    CoordinatorLayout clContent;

    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshWidget;

    private LinearLayoutManager mLayoutManager;

    private RecipeItemAdapter feedAdapter;
    private List<RecipeEntity> recipeEntities = new ArrayList<>();

    private RecipeListPresenter recipeListPresenter;

    private Handler handler = new Handler();

    private int lastVisibleItem = 0;

    private boolean loadMore;

    private boolean isRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRefreshView();
    }

    private void  setUpRefreshView(){

        mSwipeRefreshWidget.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        mSwipeRefreshWidget.setProgressViewOffset(true, 50, 200);

        //设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        mSwipeRefreshWidget.setSize(SwipeRefreshLayout.LARGE);

        mSwipeRefreshWidget.setOnRefreshListener(this);

        mSwipeRefreshWidget.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        mRecyclerView.setLayoutManager(mLayoutManager);
        feedAdapter = new RecipeItemAdapter(this, recipeEntities);
        mRecyclerView.setAdapter(feedAdapter);
        mRecyclerView.setItemAnimator(new RecipeItemAnimator());
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == feedAdapter.getItemCount()) {
                    loadMore = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

        });

        recipeListPresenter = new RecipeListPresenter(this, this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (ACTION_SHOW_LOADING_ITEM.equals(intent.getAction())) {
            showFeedLoadingItemDelayed();
        }
    }

    private void showFeedLoadingItemDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRecyclerView.smoothScrollToPosition(0);
                feedAdapter.showLoadingView();
            }
        }, 500);
    }

    @OnClick(R.id.ivSearch)
    public void onSearchClick(){
        Toast.makeText(this, "search", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRefresh() {
        if (!isRefresh) {
            isRefresh = true;
            mSwipeRefreshWidget.setRefreshing(true);
            if(loadMore){
                recipeListPresenter.loadMoreRecipes();
            }
            else {
                recipeListPresenter.updateRefreshRecipes();
            }
        }
    }

    @Override
    public void onCookListUpdateRefreshSuccess(ArrayList<RecipeEntity> list) {
        resetReflashStatus();
        feedAdapter.setDataList(list);
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCookListUpdateRefreshFail(String msg) {
        resetReflashStatus();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCookListLoadMoreSuccess(ArrayList<RecipeEntity> list) {
        resetReflashStatus();
        feedAdapter.addItems(list);
    }

    @Override
    public void onCookListLoadMoreFail(String msg) {
        resetReflashStatus();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void resetReflashStatus(){
        loadMore = false;
        isRefresh = false;
        mSwipeRefreshWidget.setRefreshing(false);
    }
}
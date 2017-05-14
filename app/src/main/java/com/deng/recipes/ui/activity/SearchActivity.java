package com.deng.recipes.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.deng.recipes.R;
import com.deng.recipes.iview.IRecipeListView;
import com.deng.recipes.iview.IRecipeSearchView;
import com.deng.recipes.model.entity.recipe.RecipeEntity;
import com.deng.recipes.presenter.RecipeListPresenter;
import com.deng.recipes.presenter.RecipeSearchPresenter;
import com.deng.recipes.ui.adapter.RecipeItemAdapter;
import com.deng.recipes.ui.view.SwipeRefreshView;
import com.google.common.base.Strings;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

import com.deng.recipes.ui.utils.CircleTransformation;
import com.deng.recipes.ui.view.RevealBackgroundView;

import org.apache.commons.codec.binary.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Miroslaw Stanek on 14.01.15.
 */
public class SearchActivity extends BaseDrawerActivity implements IRecipeSearchView {

    @BindView(R.id.search)
    ImageButton search;

    @BindView(R.id.et_input)
    EditText input;

    private String queryString;

    @BindView(R.id.rvFeed)
    ListView rvFeed;

    @BindView(R.id.swipe)
    SwipeRefreshView mSwipeRefreshView;

    private LinearLayoutManager mLayoutManager;

    private RecipeItemAdapter feedAdapter;
    private List<RecipeEntity> recipeEntities = new ArrayList<>();

    private RecipeSearchPresenter recipeSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        setUpRefreshView();
    }

    private void setUpRefreshView(){

        mSwipeRefreshView.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        //设置下拉出现小圆圈是否是缩放出现，出现的位置，最大的下拉位置
        mSwipeRefreshView.setProgressViewOffset(true, 50, 200);

        //设置下拉圆圈的大小，两个值 LARGE， DEFAULT
        mSwipeRefreshView.setSize(SwipeRefreshLayout.LARGE);


        mSwipeRefreshView.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));

        mLayoutManager = new LinearLayoutManager(this) {
            @Override
            protected int getExtraLayoutSpace(RecyclerView.State state) {
                return 300;
            }
        };
        //mRecyclerView.setLayoutManager(mLayoutManager);
        feedAdapter = new RecipeItemAdapter(this, recipeEntities);
        rvFeed.setAdapter(feedAdapter);
        //mRecyclerView.setItemAnimator(new RecipeItemAnimator());

        recipeSearchPresenter = new RecipeSearchPresenter(this,SearchActivity.this);

        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                if(!Strings.isNullOrEmpty(queryString)) {
                    queryString = input.getText().toString();
                    Toast.makeText(SearchActivity.this, "refresh", Toast.LENGTH_SHORT).show();
                    recipeSearchPresenter.search(queryString);
                }
                else mSwipeRefreshView.setRefreshing(false);
            }
        });

        // 加载监听器
        mSwipeRefreshView.setOnLoadListener(new SwipeRefreshView.OnLoadListener() {

            @Override
            public void onLoad() {
                if(!Strings.isNullOrEmpty(queryString)) {
                    Toast.makeText(SearchActivity.this, "load", Toast.LENGTH_SHORT).show();
                    recipeSearchPresenter.searchMore(queryString);
                }
                else mSwipeRefreshView.setLoading(false);

            }
        });
    }


    @OnClick(R.id.search)
    public void onSearchClick(){
        if(!Strings.isNullOrEmpty(input.getText().toString())){
            queryString = input.getText().toString();
            mSwipeRefreshView.setRefreshing(true);
            recipeSearchPresenter.search(queryString);
        }
    }

    @Override
    public void onCookSearchSuccess(ArrayList<RecipeEntity> list) {
        resetReflashStatus();
        feedAdapter.setDataList(list);
        feedAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCookSearchFail(String msg) {
        resetReflashStatus();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCookSearchMoreSuccess(ArrayList<RecipeEntity> list) {
        resetReflashStatus();
        feedAdapter.addItems(list);
    }

    @Override
    public void onCookSearchMoreFail(String msg) {
        resetReflashStatus();
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void resetReflashStatus(){
        mSwipeRefreshView.setRefreshing(false);
        mSwipeRefreshView.setLoading(false);
    }
}

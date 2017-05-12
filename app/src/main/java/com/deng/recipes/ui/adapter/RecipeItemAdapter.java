package com.deng.recipes.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.deng.recipes.RecipesApplication;
import com.deng.recipes.R;
import com.deng.recipes.model.entity.recipe.RecipeEntity;
import com.deng.recipes.ui.activity.RecipeDetailActivity;
import com.deng.recipes.ui.view.LoadingRecipeItemView;
import com.google.common.base.Strings;

/**
 * Created by froger_mcs on 05.11.14.
 */
public class RecipeItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";

    public static final int VIEW_TYPE_DEFAULT = 1;
    public static final int VIEW_TYPE_LOADER = 2;

    private List<RecipeEntity> feedItems = new ArrayList<>();

    private Context context;
    //private OnFeedItemClickListener onFeedItemClickListener;

    private boolean showLoadingView = false;

    public RecipeItemAdapter(Context context, List<RecipeEntity> recipeEntities) {
        this.context = context;
        this.feedItems = recipeEntities;
    }

    public void setDataList(List<RecipeEntity> datas) {
        feedItems.clear();
        if (null != datas) {
            feedItems.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void addItems(List<RecipeEntity> datas) {
        if (null == datas) return;
        feedItems.addAll(datas);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_DEFAULT) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
            CellFeedViewHolder cellFeedViewHolder = new CellFeedViewHolder(view, context);
            setupClickableViews(view, cellFeedViewHolder);
            return cellFeedViewHolder;
        } else if (viewType == VIEW_TYPE_LOADER) {
            LoadingRecipeItemView view = new LoadingRecipeItemView(context);
            view.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT)
            );
            return new LoadingCellFeedViewHolder(view, context);
        }

        return null;
    }

    private void setupClickableViews(final View view, final CellFeedViewHolder cellFeedViewHolder) {
        cellFeedViewHolder.ivFeedCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, RecipeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("recipe", cellFeedViewHolder.getFeedItem());
                it.putExtras(bundle);       // it.putExtra(“test”, "shuju”);
                context.startActivity(it);            // startActivityForResult(it,REQUEST_CODE);
            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((CellFeedViewHolder) viewHolder).bindView(feedItems.get(position));

        if (getItemViewType(position) == VIEW_TYPE_LOADER) {
            bindLoadingFeedItem((LoadingCellFeedViewHolder) viewHolder);
        }
    }

    private void bindLoadingFeedItem(final LoadingCellFeedViewHolder holder) {
        holder.loadingRecipeItemView.setOnLoadingFinishedListener(new LoadingRecipeItemView.OnLoadingFinishedListener() {
            @Override
            public void onLoadingFinished() {
                showLoadingView = false;
                notifyItemChanged(0);
            }
        });
        holder.loadingRecipeItemView.startLoading();
    }

    @Override
    public int getItemViewType(int position) {
        if (showLoadingView && position == 0) {
            return VIEW_TYPE_LOADER;
        } else {
            return VIEW_TYPE_DEFAULT;
        }
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public void updateItems(List<RecipeEntity> recipeEntities, boolean animated) {
        feedItems.addAll(recipeEntities);
        if (animated) {
            notifyItemRangeInserted(0, feedItems.size());
        } else {
            notifyDataSetChanged();
        }
    }

//    public void setOnFeedItemClickListener(OnFeedItemClickListener onFeedItemClickListener) {
//        this.onFeedItemClickListener = onFeedItemClickListener;
//    }

    public void showLoadingView() {
        showLoadingView = true;
        notifyItemChanged(0);
    }

    public static class CellFeedViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivImage)
        ImageView ivFeedCenter;
        @BindView(R.id.vBgLike)
        View vBgLike;
        @BindView(R.id.ivLike)
        ImageView ivLike;
        @BindView(R.id.vImageRoot)
        FrameLayout vImageRoot;

        @BindView(R.id.rating)
        RatingBar rbRating;

        @BindView(R.id.tvTitle)
        TextView tvTitle;

        @BindView(R.id.tvDesc)
        TextView tvDesc;

        private RecipeEntity feedItem;

        private Context context;

        public CellFeedViewHolder(View view, Context context) {
            super(view);
            this.context = context;
            ButterKnife.bind(this, view);

        }

        public void bindView(RecipeEntity feedItem) {
            this.feedItem = feedItem;
            if (feedItem.getRecipe().getImages().size() > 0) {
                ((RecipesApplication) context.getApplicationContext()).getImageLoader()
                        .displayImage(feedItem.getRecipe().getImages().get(0), ivFeedCenter,
                                RecipesApplication.imageOptions()); //
            }

            tvTitle.setText(feedItem.getRecipe().getTitle());
            if (Strings.isNullOrEmpty(feedItem.getRecipe().getDesc()))
                tvDesc.setVisibility(View.GONE);
            else
                tvDesc.setText(feedItem.getRecipe().getDesc());

            rbRating.setRating(feedItem.getRecipe().getScore());
        }

        public RecipeEntity getFeedItem() {
            return feedItem;
        }
    }

    public static class LoadingCellFeedViewHolder extends CellFeedViewHolder {

        LoadingRecipeItemView loadingRecipeItemView;

        public LoadingCellFeedViewHolder(LoadingRecipeItemView view, Context context) {
            super(view, context);
            this.loadingRecipeItemView = view;
        }

        @Override
        public void bindView(RecipeEntity feedItem) {
            super.bindView(feedItem);
        }
    }

//    public interface OnFeedItemClickListener {
//
//        void onMoreClick(View v, int position);
//    }
}

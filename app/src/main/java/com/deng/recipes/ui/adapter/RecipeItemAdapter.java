package com.deng.recipes.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
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
public class RecipeItemAdapter extends BaseAdapter {
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


    public void updateItems(List<RecipeEntity> recipeEntities, boolean animated) {
        feedItems.addAll(recipeEntities);
        notifyDataSetChanged();

    }


    public static class CellFeedViewHolder {
        ImageView ivFeedCenter;
        View vBgLike;
        ImageView ivLike;
        FrameLayout vImageRoot;
        RatingBar rbRating;
        TextView tvTitle;
        TextView tvDesc;

        RecipeEntity recipeEntity;

        public CellFeedViewHolder(RecipeEntity feedItem) {
            this.recipeEntity = feedItem;
        }


        public RecipeEntity getFeedItem() {
            return recipeEntity;
        }
    }


    @Override
    public int getCount() {
        return feedItems.size();
    }

    @Override
    public Object getItem(int i) {
        return feedItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final RecipeEntity recipeEntity = feedItems.get(position);
        final CellFeedViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_recipe, null);
            holder = new CellFeedViewHolder(recipeEntity);

            holder.ivFeedCenter = (ImageView) convertView.findViewById(R.id.ivImage);

            holder.vBgLike = convertView.findViewById(R.id.vBgLike);

            holder.ivLike = (ImageView) convertView.findViewById(R.id.ivLike);

            holder.vImageRoot = (FrameLayout) convertView.findViewById(R.id.vImageRoot);

            holder.rbRating = (RatingBar) convertView.findViewById(R.id.rating);

            holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

            holder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);

            convertView.setTag(holder);
        } else {
            holder = (CellFeedViewHolder) convertView.getTag();
        }

        if (holder.recipeEntity.getRecipe().getImages().size() > 0) {
            ((RecipesApplication) context.getApplicationContext()).getImageLoader()
                    .displayImage(holder.recipeEntity.getRecipe().getImages().get(0), holder.ivFeedCenter,
                            RecipesApplication.imageOptions()); //
        }

        holder.tvTitle.setText(holder.recipeEntity.getRecipe().getTitle());
        if (Strings.isNullOrEmpty(holder.recipeEntity.getRecipe().getDesc()))
            holder.tvDesc.setVisibility(View.GONE);
        else
            holder.tvDesc.setText(holder.recipeEntity.getRecipe().getDesc());

        holder.rbRating.setRating(holder.recipeEntity.getRecipe().getScore());

        setupClickableViews(convertView, holder);

        return convertView;
    }

}

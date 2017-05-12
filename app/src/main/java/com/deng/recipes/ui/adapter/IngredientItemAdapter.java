package com.deng.recipes.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.deng.recipes.R;
import com.deng.recipes.model.entity.recipe.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huichaodeng on 2017/5/1.
 */
public class IngredientItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    List<Ingredient> ingredients;

    public IngredientItemAdapter(Context context, List<Ingredient> ingredients) {
        this.context = context;
        this.ingredients = ingredients;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        //runEnterAnimation(viewHolder.itemView, position);
        IngredientViewHolder holder = (IngredientViewHolder) viewHolder;
        Ingredient ingredient = ingredients.get(position);
        holder.tvKey.setText(ingredient.getIngredientName());
        holder.tvValue.setText(ingredient.getQuantityDesc());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvKey)
        TextView tvKey;
        @BindView(R.id.tvValue)
        TextView tvValue;

        public IngredientViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

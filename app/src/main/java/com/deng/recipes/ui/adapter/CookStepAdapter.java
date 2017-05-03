package com.deng.recipes.ui.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
import com.deng.recipes.InstaMaterialApplication;
import com.deng.recipes.R;
import com.deng.recipes.entity.CookStep;
import com.google.common.base.Strings;

/**
 * Created by froger_mcs on 11.11.14.
 */
public class CookStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private int lastAnimatedPosition = -1;

    private boolean animationsLocked = false;
    private boolean delayEnterAnimation = true;

    private List<CookStep> cookSteps;

    public CookStepAdapter(Context context, List<CookStep> cookSteps) {
        this.context = context;
        this.cookSteps = cookSteps;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_step, parent, false);
        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        runEnterAnimation(viewHolder.itemView, position);
        StepViewHolder holder = (StepViewHolder) viewHolder;
        CookStep cookStep = cookSteps.get(position);
        holder.tvDesc.setText(cookStep.getDescription());
        holder.tvStepOrder.setText("第"+String.valueOf(cookStep.getStepOrder())+"步");
        if(!Strings.isNullOrEmpty(cookStep.getImage())) {
            ((InstaMaterialApplication) context.getApplicationContext()).getImageLoader()
                    .displayImage(cookStep.getImage(), holder.ivImage,
                            InstaMaterialApplication.imageOptions()); //
        }
        else{
            holder.ivImage.setVisibility(View.GONE);
        }
    }

    private void runEnterAnimation(View view, int position) {
        if (animationsLocked) return;

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(delayEnterAnimation ? 20 * (position) : 0)
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return cookSteps.size();
    }

    public void updateItems(){
        this.notifyDataSetChanged();
    }


    public void addItem(CookStep cookStep) {
        cookSteps.add(cookStep);
        notifyItemInserted(getItemCount() - 1);
    }

    public void setAnimationsLocked(boolean animationsLocked) {
        this.animationsLocked = animationsLocked;
    }

    public void setDelayEnterAnimation(boolean delayEnterAnimation) {
        this.delayEnterAnimation = delayEnterAnimation;
    }

    public static class StepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvStepOrder)
        TextView tvStepOrder;
        @BindView(R.id.ivImage)
        ImageView ivImage;
        @BindView(R.id.tvDesc)
        TextView tvDesc;

        public StepViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

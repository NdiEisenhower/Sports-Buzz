package com.mmbadi.sportsbuzz.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.mmbadi.sportsbuzz.R;
import com.mmbadi.sportsbuzz.models.Sport;
import com.mmbadi.sportsbuzz.utills.DateHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ArticleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Sport> sportList = new ArrayList<>();
    private OnArticleClickedListener articleClickedListener;
    private Context context;


    public ArticleViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_featured_sport, parent, false);
            viewHolder = new ViewHolderHeadline(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sport, parent, false);
            viewHolder = new ViewHolder(view);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        final Sport sport = sportList.get(position);

        if (sport != null) {

            String headline = sport.getHeadline();
            String blurb = sport.getBlurb();
            String category = TextUtils.isEmpty(sport.getCategory())? sport.getCategoryDisplayName():sport.getCategory();
            String updatedDate = sport.getUpdatedDate();
            String image = sport.getImageUrlLocal() + sport.getSmallImageName();

            long date = DateHelper.extractDate(updatedDate);
            updatedDate = DateHelper.getFormattedDate(date);

            if (holder.getItemViewType() == 0) {
                final ViewHolderHeadline viewHolderHeadline = (ViewHolderHeadline) holder;

                viewHolderHeadline.textViewHeadline.setText(headline);
                viewHolderHeadline.textViewCategory.setText(category);
                viewHolderHeadline.textViewUpdatedDate.setText(updatedDate);
                image = sport.getImageUrlLocal() + sport.getLargeImageName();


                viewHolderHeadline.textViewHeadline.setVisibility(TextUtils.isEmpty(headline) ? View.GONE : View.VISIBLE);
                viewHolderHeadline.textViewCategory.setVisibility(TextUtils.isEmpty(category) ? View.GONE : View.VISIBLE);
                viewHolderHeadline.textViewUpdatedDate.setVisibility(date > 0 ? View.GONE : View.VISIBLE);

                Glide.with(context)
                        .asDrawable()
                        .load(image)
                        .into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                viewHolderHeadline.constraintLayoutImage.setBackground(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });

                viewHolderHeadline.articleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleClickedListener.SelectedArticle(sport);
                    }
                });

            } else {
                ViewHolder viewHolder = (ViewHolder) holder;

                viewHolder.textViewHeadline.setVisibility(TextUtils.isEmpty(headline) ? View.GONE : View.VISIBLE);
                viewHolder.textViewBlurb.setVisibility(TextUtils.isEmpty(blurb) ? View.GONE : View.VISIBLE);
                viewHolder.textViewCategory.setVisibility(TextUtils.isEmpty(category) ? View.GONE : View.VISIBLE);
                viewHolder.textViewUpdatedDate.setVisibility(date > 0 ? View.GONE : View.VISIBLE);
                viewHolder.imageViewThumbnail.setVisibility(TextUtils.isEmpty(image) ? View.GONE : View.VISIBLE);

                viewHolder.textViewHeadline.setText(headline);
                viewHolder.textViewBlurb.setText(blurb);
                viewHolder.textViewCategory.setText(category);
                viewHolder.textViewUpdatedDate.setText(updatedDate);

                Glide.with(context)
                        .load(image)
                        .centerCrop()
                        .into(viewHolder.imageViewThumbnail);


                viewHolder.articleLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        articleClickedListener.SelectedArticle(sport);
                    }
                });
            }

        }
    }

    @Override
    public int getItemViewType(int position) {
        int viewType = 1;
        if (position == 0) viewType = 0;
        return viewType;
    }

    @Override
    public int getItemCount() {
        return sportList.size();
    }

    public void addArticles(List<Sport> sports) {
        this.sportList = sports;

        notifyDataSetChanged();
    }

    public void setOnArticleClickedListener(OnArticleClickedListener mCallback) {
        this.articleClickedListener = mCallback;
    }

    public interface OnArticleClickedListener {
        public void SelectedArticle(Sport sport);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewHeadline, textViewBlurb, textViewUpdatedDate, textViewCategory;
        public AppCompatImageView imageViewThumbnail;
        public CardView articleLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewHeadline = itemView.findViewById(R.id.article_headline);
            this.textViewBlurb = itemView.findViewById(R.id.article_blurb);
            this.textViewCategory = itemView.findViewById(R.id.article_category);
            this.textViewUpdatedDate = itemView.findViewById(R.id.article_date);
            this.imageViewThumbnail = itemView.findViewById(R.id.article_image);
            this.articleLayout = itemView.findViewById(R.id.articleLayout);
        }
    }

    public class ViewHolderHeadline extends RecyclerView.ViewHolder {

        public TextView textViewHeadline, textViewUpdatedDate, textViewCategory;
        public ConstraintLayout constraintLayoutImage;
        public CardView articleLayout;

        public ViewHolderHeadline(@NonNull View itemView) {
            super(itemView);
            this.textViewHeadline = itemView.findViewById(R.id.featured_headline);
            this.textViewCategory = itemView.findViewById(R.id.featured_category);
            this.textViewUpdatedDate = itemView.findViewById(R.id.featured_date);
            this.constraintLayoutImage = itemView.findViewById(R.id.featured_image);
            this.articleLayout = itemView.findViewById(R.id.articleLayout);
        }
    }
}

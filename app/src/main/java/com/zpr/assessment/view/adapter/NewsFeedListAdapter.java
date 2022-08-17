package com.zpr.assessment.view.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.zpr.assessment.R;
import com.zpr.assessment.service.model.News;
import com.zpr.assessment.view.ui.NewsDetailsFragment;

import java.util.ArrayList;
import java.util.List;

public class NewsFeedListAdapter extends RecyclerView.Adapter<NewsFeedListAdapter.ViewHolder> implements Filterable {

    private List<News> newsList = new ArrayList<>();
    private List<News> newsListForFilter;

    public void setNewsFeedList(List<News> newsList) {
        this.newsList = newsList;
        this.newsListForFilter = new ArrayList<>(newsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsFeedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsFeedListAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        News model = newsList.get(position);
        holder.title.setText(model.getTitle());
        holder.subTitle.setText(model.getDescription());
        holder.author.setText(model.getAuthor());
        holder.publishedDate.setText(model.getPublishedAt());
        Picasso.with(holder.mediaImage.getContext()).load(model.getImage()).placeholder(R.drawable.ic_loading_icon)
                .error(R.drawable.ic_news_no_image)
                .into(holder.mediaImage);
        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("NEWS", model);
            NewsDetailsFragment detailsFragment = new NewsDetailsFragment();
            detailsFragment.setArguments(bundle);
            AppCompatActivity activity = (AppCompatActivity) view.getContext();
            activity.getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.container, detailsFragment)
                    .addToBackStack("detailsFragment")
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView subTitle;
        private TextView author;
        private TextView publishedDate;
        private ImageView mediaImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            subTitle = itemView.findViewById(R.id.subtitle_text);
            author = itemView.findViewById(R.id.txt_publisher);
            publishedDate = itemView.findViewById(R.id.txt_date);
            mediaImage = itemView.findViewById(R.id.media_image);
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<News> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(newsListForFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (News item : newsListForFilter) {
                    if (item.getTitle().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values != null) {
                newsList.clear();
                newsList.addAll((List) results.values);
                notifyDataSetChanged();
            }
        }
    };
}

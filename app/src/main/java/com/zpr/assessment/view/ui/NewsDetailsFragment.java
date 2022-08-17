package com.zpr.assessment.view.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zpr.assessment.R;
import com.zpr.assessment.service.model.News;

import org.jetbrains.annotations.NotNull;

public class NewsDetailsFragment extends Fragment {


    private static final String ARG_PARAM1 = "NEWS";
    private TextView title;
    private TextView subTitle;
    private TextView author;
    private TextView publishedDate;
    private ImageView mediaImage;
    private News model;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (getArguments() != null) {
            model = (News) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.news_details_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title = view.findViewById(R.id.title_text);
        subTitle = view.findViewById(R.id.subtitle_text);
        author = view.findViewById(R.id.txt_publisher);
        publishedDate = view.findViewById(R.id.txt_date);
        mediaImage = view.findViewById(R.id.media_image);
        if (model != null) {
            title.setText(model.getTitle());
            subTitle.setText(model.getDescription());
            author.setText(model.getAuthor());
            publishedDate.setText(model.getPublishedAt());
            Picasso.with(getActivity()).load(model.getImage()).placeholder(R.drawable.ic_loading_icon)
                    .error(R.drawable.ic_news_no_image)
                    .into(mediaImage);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
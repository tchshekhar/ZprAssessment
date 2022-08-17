package com.zpr.assessment.view.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.Group;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zpr.assessment.R;
import com.zpr.assessment.view.adapter.NewsFeedListAdapter;
import com.zpr.assessment.viewmodel.MainViewModel;

import org.jetbrains.annotations.NotNull;

public class NewsListFragment extends Fragment {

    private MainViewModel mViewModel;
    private NewsFeedListAdapter adapter;
    private RecyclerView rcvNewsfeeds;
    private FloatingActionButton btnRefresh;
    private ShimmerFrameLayout mShimmerViewContainer;
    private Group viewGroup;


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.news_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        rcvNewsfeeds = view.findViewById(R.id.rcvNewsFeeds);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        viewGroup = view.findViewById(R.id.viewGroup);
        adapter = new NewsFeedListAdapter();
        rcvNewsfeeds.setAdapter(adapter);
        SearchView searchView = view.findViewById(R.id.search_bar);
        btnRefresh = view.findViewById(R.id.refresh);
        loadNewsFeeds();
        btnRefresh.setOnClickListener(view1 -> loadNewsFeeds());
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        mViewModel.mutableLiveData.observe(getActivity(), newsResponse -> {
            switch (newsResponse.status) {
                case SUCCESS:
                    hideLoading();
                    adapter.setNewsFeedList(newsResponse.data.getNewsList());
                    viewGroup.setVisibility(View.VISIBLE);
                    break;
                case LOADING:
                    showLoading();
                    break;
                case ERROR:
                    Toast.makeText(getActivity(),"oops something went wrong", Toast.LENGTH_SHORT).show();
                    break;

            }
        });
    }

    public void loadNewsFeeds() {
        mViewModel.loadNewsFeeds();
    }

    public void showLoading() {
        mShimmerViewContainer.startShimmer();
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        viewGroup.setVisibility(View.GONE);
    }

    public void hideLoading() {
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
    }
}
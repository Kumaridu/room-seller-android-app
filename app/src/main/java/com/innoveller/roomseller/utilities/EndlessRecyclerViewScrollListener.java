package com.innoveller.roomseller.utilities;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private static final String TAG = "EndlessRecyclerViewScrollListener";
    private boolean loading = true;
    private int previousItemTotalCount = 0;
    private final LinearLayoutManager layoutManager;
    private final LoadMoreListener loadMoreListener;
    public RecyclerView recyclerView;

    public interface LoadMoreListener {
        // Defines the process for actually loading more data based on page
        void onLoadMore();
    }

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager, LoadMoreListener loadMoreListener) {
        this.layoutManager = layoutManager;
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if(dy > 0) {
            int visibleItemCount = recyclerView.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int pastVisibleItems = layoutManager.findFirstCompletelyVisibleItemPosition();

            if(loading) {
                if(totalItemCount > previousItemTotalCount) {
                    Log.d(TAG, "onScrolled: Successfully loaded booking list");
                    loading = false;
                    previousItemTotalCount = totalItemCount;
                }
            }
            if (!loading) {
                if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                    // fetch new data
                    Log.d(TAG, "onScrolled: Going to load more booking list");
                    loadMoreListener.onLoadMore();
                    Log.d(TAG, "onScrolled: Setting the loading true");
                    loading = true;
                }
            }
        }
    }

}

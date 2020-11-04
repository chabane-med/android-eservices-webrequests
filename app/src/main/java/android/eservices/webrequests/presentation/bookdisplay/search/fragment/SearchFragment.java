package android.eservices.webrequests.presentation.bookdisplay.search.fragment;

import android.eservices.webrequests.R;
import android.eservices.webrequests.data.di.FakeDependencyInjection;
import android.eservices.webrequests.presentation.bookdisplay.search.adapter.BookActionInterface;
import android.eservices.webrequests.presentation.bookdisplay.search.adapter.BookAdapter;
import android.eservices.webrequests.presentation.bookdisplay.search.adapter.BookViewItem;
import android.eservices.webrequests.presentation.viewmodel.BookFavoriteViewModel;
import android.eservices.webrequests.presentation.viewmodel.BookSearchViewModel;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.lifecycle.Observer;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/*
 * TODO : uncheck favorite selection in search results when favorite unchecked from Favorite fragment
 */
public class SearchFragment extends Fragment implements BookActionInterface {

    public static final String TAB_NAME = "Search";
    private View rootView;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ProgressBar progressBar;
    private BookSearchViewModel bookSearchViewModel;
    private BookFavoriteViewModel bookFavoriteViewModel;

    private SearchFragment() {
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_search, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupSearchView();
        setupRecyclerView();
        progressBar = rootView.findViewById(R.id.progress_bar);

        registerViewModels();
    }

    private void registerViewModels() {
        bookSearchViewModel = new ViewModelProvider(requireActivity(), FakeDependencyInjection.getViewModelFactory()).get(BookSearchViewModel.class);
        System.out.println("FVVM is " + bookFavoriteViewModel);
        bookSearchViewModel.getBooks().observe(getViewLifecycleOwner(), new Observer<List<BookViewItem>>() {
            @Override
            public void onChanged(List<BookViewItem> bookItemViewModelList) {
                bookAdapter.bindViewModels(bookItemViewModelList);
            }
        });

        bookSearchViewModel.getIsDataLoading().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isDataLoading) {
                progressBar.setVisibility(isDataLoading ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void setupSearchView() {
        searchView = rootView.findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private Timer timer = new Timer();

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(final String s) {
                if (s.length() == 0) {
                    bookSearchViewModel.cancelSubscription();
                } else {
                    timer.cancel();
                    timer = new Timer();
                    int sleep = 350;
                    if (s.length() == 1)
                        sleep = 5000;
                    else if (s.length() <= 3)
                        sleep = 300;
                    else if (s.length() <= 5)
                        sleep = 200;
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            bookSearchViewModel.searchBooks(s);
                        }
                    }, sleep);
                }
                return true;
            }
        });
    }

    private void setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        bookAdapter = new BookAdapter(this);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onFavoriteToggle(String bookId, boolean isFavorite) {
        //Handle add and deletion to favorites
    }

}

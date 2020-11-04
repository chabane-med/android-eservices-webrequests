package android.eservices.webrequests.presentation.bookdisplay.favorite.fragment;

import android.eservices.webrequests.R;
import android.eservices.webrequests.data.di.FakeDependencyInjection;
import android.eservices.webrequests.presentation.bookdisplay.favorite.adapter.BookDetailActionInterface;
import android.eservices.webrequests.presentation.bookdisplay.favorite.adapter.BookDetailAdapter;
import android.eservices.webrequests.presentation.bookdisplay.favorite.adapter.BookDetailViewItem;
import android.eservices.webrequests.presentation.viewmodel.BookFavoriteViewModel;
import android.eservices.webrequests.presentation.viewmodel.Event;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoriteFragment extends Fragment implements BookDetailActionInterface {

    public static final String TAB_NAME = "Favorites";
    private View rootView;
    private RecyclerView recyclerView;
    private BookDetailAdapter bookAdapter;
    private BookFavoriteViewModel bookFavoriteViewModel;

    private FavoriteFragment() {
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupRecyclerView();
       // registerViewModels();
    }
/*
    private void registerViewModels() {
        bookFavoriteViewModel = new ViewModelProvider(requireActivity(), FakeDependencyInjection.getViewModelFactory()).get(BookFavoriteViewModel.class);
        System.out.println("FVVM is " + bookFavoriteViewModel);

        bookFavoriteViewModel.getFavorites().observe(getViewLifecycleOwner(), new Observer<List<BookDetailViewItem>>() {
            @Override
            public void onChanged(List<BookDetailViewItem> bookDetailViewItemList) {
                bookAdapter.bindViewModels(bookDetailViewItemList);
            }
        });

        bookFavoriteViewModel.getBookAddedEvent().observe(getViewLifecycleOwner(), new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                //Do nothing
            }
        });

        bookFavoriteViewModel.getBookDeletedEvent().observe(getViewLifecycleOwner(), new Observer<Event<String>>() {
            @Override
            public void onChanged(Event<String> stringEvent) {
                //Do nothing
            }
        });
    }*/

    private void setupRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recycler_view);
        bookAdapter = new BookDetailAdapter(this);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onRemoveFavorite(String bookId) {
        //bookFavoriteViewModel.removeBookFromFavorites(bookId);
        System.out.println("Remove book " + bookId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //bookFavoritePresenter.detachView();
    }
}

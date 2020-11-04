package android.eservices.webrequests.data.db;


import android.eservices.webrequests.data.entity.BookEntity;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
@Dao
public interface BookDao {
    @Query("SELECT * from bookentity")
    Flowable<List<BookEntity>> loadFavorites();

    @Insert
    public Completable addBookToFavorites(BookEntity bookEntity);

    @Query("DELETE FROM bookentity WHERE id = :id")
    public Completable deleteBookFromFavorites(String id);

    @Query("SELECT id from bookentity")
    Single<List<String>> getFavoriteIdList();
}

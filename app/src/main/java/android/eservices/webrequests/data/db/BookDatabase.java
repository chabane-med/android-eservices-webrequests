package android.eservices.webrequests.data.db;

import android.eservices.webrequests.data.entity.BookEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {BookEntity.class}, version = 1)
public abstract class BookDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
}
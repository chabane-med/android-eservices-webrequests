package android.eservices.webrequests.data.di;

import android.content.Context;
import android.eservices.webrequests.data.api.BookDisplayService;
import android.eservices.webrequests.data.db.BookDatabase;
import android.eservices.webrequests.data.repository.bookdisplay.BookDisplayDataRepository;
import android.eservices.webrequests.data.repository.bookdisplay.BookDisplayRepository;
import android.eservices.webrequests.data.repository.bookdisplay.local.BookDisplayLocalDataSource;
import android.eservices.webrequests.data.repository.bookdisplay.mapper.BookToBookEntityMapper;
import android.eservices.webrequests.data.repository.bookdisplay.remote.BookDisplayRemoteDataSource;
import android.eservices.webrequests.presentation.viewmodel.ViewModelFactory;

import androidx.room.Room;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Please never do that in a production app. Ever.
 * For the purpose of our course, this is the best option to cover interesting topics as
 * we don't have time to dig into Dependency Injection frameworks such as the famous Dagger.
 * Singleton are compulsory for some classes, such as the one here. If you don't know why, then ask me.
 * Note that this god object doesn't handle Scopes nor component lifecycles so this really shouldn't be
 * the way to go when you master the craft of your software.
 */
public class FakeDependencyInjection {

    private static BookDisplayService bookDisplayService;
    private static Retrofit retrofit;
    private static Gson gson;
    private static BookDisplayRepository bookDisplayRepository;
    private static BookDatabase bookDatabase;
    private static Context applicationContext;
    private static ViewModelFactory viewModelFactory;

    public static ViewModelFactory getViewModelFactory() {
        if (viewModelFactory == null) {
            viewModelFactory = new ViewModelFactory(getBookDisplayRepository());
        }
        return viewModelFactory;
    }


    public static BookDisplayRepository getBookDisplayRepository() {
        if (bookDisplayRepository == null) {
            bookDisplayRepository = new BookDisplayDataRepository(
                    new BookDisplayLocalDataSource(getBookDatabase()),
                    new BookDisplayRemoteDataSource(getBookDisplayService()),
                    new BookToBookEntityMapper()
            );
        }
        return bookDisplayRepository;
    }

    public static BookDisplayService getBookDisplayService() {
        if (bookDisplayService == null) {
            bookDisplayService = getRetrofit().create(BookDisplayService.class);
        }
        return bookDisplayService;
    }

    public static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://www.googleapis.com/books/v1/")
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();
            BookDisplayService retro = retrofit.create(BookDisplayService.class);
        }
        return retrofit;
    }

    public static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public static void setContext(Context context) {
        applicationContext = context;
    }

    public static BookDatabase getBookDatabase() {
        if (bookDatabase == null) {
            bookDatabase = Room.databaseBuilder(applicationContext,
                    BookDatabase.class, "book-database").build();
        }
        return bookDatabase;
    }
}

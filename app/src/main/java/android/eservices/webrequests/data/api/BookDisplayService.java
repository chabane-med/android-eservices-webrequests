package android.eservices.webrequests.data.api;

import android.eservices.webrequests.data.api.model.Book;
import android.eservices.webrequests.data.api.model.BookSearchResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BookDisplayService {
    @GET("volumes")
    Single<BookSearchResponse> searchBooks(@Query("q") String keywords, @Query("key") String apiKey);

    @GET("volumes/{bookId}")
    Single<Book> getBook(@Path("bookId") String bookId, @Query("key") String apiKey);
}

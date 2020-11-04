package android.eservices.webrequests.data.repository.bookdisplay;

import android.eservices.webrequests.data.api.model.BookSearchResponse;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface BookDisplayRepository {

    Single<BookSearchResponse> getBookSearchResponse(String keywords);
}

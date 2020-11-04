package android.eservices.webrequests.data.repository.bookdisplay.remote;

import android.eservices.webrequests.BookApplication;
import android.eservices.webrequests.data.api.BookDisplayService;
import android.eservices.webrequests.data.api.model.Book;
import android.eservices.webrequests.data.api.model.BookSearchResponse;
import android.eservices.webrequests.data.db.BookDatabase;

import io.reactivex.Single;

public class BookDisplayRemoteDataSource {
    private BookDisplayService bookDisplayService;

    public BookDisplayRemoteDataSource(BookDisplayService bookDisplayService) {
        this.bookDisplayService = bookDisplayService;
    }

    public Single<BookSearchResponse> getBookSearchResponse(String keywords) {
        return bookDisplayService.searchBooks(keywords, BookApplication.API_KEY);
    }

    public Single<Book> getBookDetails(String bookId) {
        return bookDisplayService.getBook(bookId, BookApplication.API_KEY);
    }
}

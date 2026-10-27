package com.accenture.externalapis.demo.client;

import com.accenture.externalapis.demo.config.ExternalServiceProperties;
import com.accenture.externalapis.demo.dto.BookDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

// TODO: Make this class implement BookRestClient.
@Component
public class BookRestClientImpl implements BookRestClient {

    private RestClient restClient;


    @Override
    public BookDto getBook(Long id) {
        return null;
    }

    @Override
    public List<BookDto> getAllBooks() {
        return List.of();
    }



    // Build the RestClient using builder.baseUrl(properties.baseUrl()).build()
    // and assign it to this.restClient
    //
    // Optional/bonus: this service doesn't require auth, but in a real API you would
    // often also add builder.defaultHeader("Authorization", "Bearer " + token) here.
    public BookRestClientImpl(RestClient.Builder builder, ExternalServiceProperties properties) {
        this.restClient = builder.baseUrl(properties.baseUrl()).build();
        // Build the RestClient using builder.baseUrl(properties.baseUrl()).build()
        // and assign it to this.restClient
        //
        // Optional/bonus: this service doesn't require auth, but in a real API you would
        // often also add builder.defaultHeader("Authorization", "Bearer " + token) here.
    }


    // TODO: Implement getBook(Long id) - fetch one book from GET /books/{id} as a
    // BookApiResponse, then map it onto a BookDto (only keep the fields BookDto needs).
    //
    // TODO: Handle the main RestClient error cases and rethrow them as ClientException:
    //  - HttpClientErrorException (4xx, e.g. book not found)
    //  - HttpServerErrorException (5xx, e.g. the faulty/teapot book)
    //  - ResourceAccessException (connection refused / timeout - the external service is unreachable)

    // TODO: Implement getAllBooks() - fetch all books from GET /books as
    // BookApiResponse[], then map each one onto a BookDto. Handle the same error
    // cases as getBook() above.
}

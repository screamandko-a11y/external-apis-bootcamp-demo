package com.accenture.externalapis.demo.client;

import com.accenture.externalapis.demo.config.ExternalServiceProperties;
import com.accenture.externalapis.demo.dto.BookApiResponse;
import com.accenture.externalapis.demo.dto.BookDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;

import java.util.ArrayList;
import java.util.List;


@Component
public class BookRestClientImpl implements BookRestClient {

    private RestClient restClient;

    public BookRestClientImpl(RestClient.Builder builder, ExternalServiceProperties properties) {
        this.restClient = builder.baseUrl(properties.baseUrl()).build();
    }


    @Override
    public BookDto getBook(Long id) {

        try {
            BookApiResponse response = restClient.get()
                    .uri("/books/{id}", id)
                    .retrieve()
                    .body(BookApiResponse.class);
            if (response == null) {
                throw new ClientException(
                        "External service returned an empty body for book with id=" + id);
            }

            return new BookDto(
                    response.title(),
                    response.author(),
                    response.genre(),
                    response.price());

        } catch (HttpClientErrorException e) {
            throw new ClientException("Failed to fetch book with id= " + id, e);
        } catch (HttpServerErrorException e) {
            throw new ClientException("External service failed for book with id=" + id, e);
        } catch (ResourceAccessException e) {
            throw new ClientException(
                    "Failed to connect to external service for book with id=" + id, e);
        } catch (RestClientException e) {
            throw new ClientException(
                    "Failed to read external response for book with id=" + id, e);

        }
    }

    @Override
    public List<BookDto> getAllBooks() {
        try {
            BookApiResponse[] responses = restClient.get()
                    .uri("/books")
                    .retrieve()
                    .body(BookApiResponse[].class);
            if (responses == null) {
                throw new ClientException("External service returned an empty body for books");

            }
            List<BookDto> books = new ArrayList<>();

            for (BookApiResponse response : responses) {
                books.add(new BookDto(
                        response.title(),
                        response.author(),
                        response.genre(),
                        response.price()));
            }
            return books;
        } catch (HttpClientErrorException e) {
            throw new ClientException("Failed to fetch books", e);

        } catch (HttpServerErrorException e) {
            throw new ClientException(
                    "External service failed while fetching books", e);

        } catch (ResourceAccessException e) {
            throw new ClientException(
                    "Failed to connect to external service while fetching books", e);
        } catch (RestClientException e) {
            throw new ClientException(
                    "Failed to read external response while fetching books", e);
        }
    }

}

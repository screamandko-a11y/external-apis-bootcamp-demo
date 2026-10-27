package com.accenture.externalapis.demo.client;

import com.accenture.externalapis.demo.config.ExternalServiceProperties;
import com.accenture.externalapis.demo.dto.BookApiResponse;
import com.accenture.externalapis.demo.dto.BookDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;


@Component
public class BookWebClientImpl implements BookWebClient {

    private WebClient webClient;

    public BookWebClientImpl(WebClient.Builder builder, ExternalServiceProperties properties) {
        this.webClient = builder.baseUrl(properties.baseUrl()).build();
    }

    @Override
    public Mono<BookDto> getBookAsync(Long id) {
        Mono<BookApiResponse> responseMono = webClient.get()
                .uri("/books/{id}", id)
                .retrieve()
                .bodyToMono(BookApiResponse.class).switchIfEmpty(Mono.error(
                        new ClientException(
                                "External service returned an empty body for book with id=" + id)))
                .onErrorMap(
                error -> !(error instanceof ClientException),
                error -> new ClientException(
                        "Failed to fetch book with id=" + id, error));

        return responseMono.map(response -> new BookDto(
                response.title(),
                response.author(),
                response.genre(),
                response.price()));
    }

    @Override
    public Flux<BookDto> getAllBooksAsync() {
        return webClient.get()
                .uri("/books")
                .retrieve()
                .bodyToFlux(BookApiResponse.class)
                .map(response -> new BookDto(
                        response.title(),
                        response.author(),
                        response.genre(),
                        response.price()))
                .onErrorMap(
                        error -> !(error instanceof ClientException),
                        error -> new ClientException(
                                "Failed to fetch books", error));
    }

    @Override
    public Mono<List<BookDto>> getBooksInParallel(Long id1, Long id2) {
        Mono<BookDto> firstBook = getBookAsync(id1);
        Mono<BookDto> secondBook = getBookAsync(id2);
        return Mono.zip(firstBook, secondBook)
                .map(books -> List.of(
                        books.getT1(),
                        books.getT2()));

    }

}

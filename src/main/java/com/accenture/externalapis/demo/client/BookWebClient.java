package com.accenture.externalapis.demo.client;

import com.accenture.externalapis.demo.dto.BookDto;
import java.util.List;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface BookWebClient {

    Mono<BookDto> getBookAsync(Long id);

    Flux<BookDto> getAllBooksAsync();

    Mono<List<BookDto>> getBooksInParallel(Long id1, Long id2);
}

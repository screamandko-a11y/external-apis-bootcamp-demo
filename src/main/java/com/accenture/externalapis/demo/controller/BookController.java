package com.accenture.externalapis.demo.controller;

import com.accenture.externalapis.demo.client.BookRestClient;
import com.accenture.externalapis.demo.client.BookWebClient;
import com.accenture.externalapis.demo.dto.BookDto;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookRestClient bookRestClient;
    private final BookWebClient bookWebClient;

    public BookController(BookRestClient bookRestClient, BookWebClient bookWebClient) {
        this.bookRestClient = bookRestClient;
        this.bookWebClient = bookWebClient;
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Long id) {
        return bookRestClient.getBook(id);
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookRestClient.getAllBooks();
    }

    @GetMapping("/async/{id}")
    public Mono<BookDto> getBookAsync(@PathVariable Long id) {
        return bookWebClient.getBookAsync(id);
    }

    @GetMapping("/async")
    public Flux<BookDto> getAllBooksAsync() {
        return bookWebClient.getAllBooksAsync();
    }

    @GetMapping("/async/parallel")
    public Mono<List<BookDto>> getBooksInParallel(@RequestParam Long id1,
                                                  @RequestParam Long id2) {
        return bookWebClient.getBooksInParallel(id1, id2);
    }
}
package io.github.dan7arievlis.libraryapi.repository;

import io.github.dan7arievlis.libraryapi.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class TransactionTest {

    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    TransactionService transactionService;

    @Test
    void simpleTransactionTest() {
        try {
        transactionService.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void ManagedEstateTransactionTest() {
        transactionService.updateWithoutUpdating();
    }
}

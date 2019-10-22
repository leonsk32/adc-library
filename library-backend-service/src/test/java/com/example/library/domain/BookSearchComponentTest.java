package com.example.library.domain;

import com.example.library.domain.book.Isbn;
import com.example.library.domain.lending.LendingRecord;
import com.example.library.domain.lending.LendingRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookSearchComponentTest {
    private BookSearchComponent target;
    @Mock
    LendingRecordRepository lendingRecordRepository;


    @BeforeEach
    void setup() {
        target = new BookSearchComponent(lendingRecordRepository);
    }

    @Test
    void search() {

        List<LendingRecord> lendingRecords = Arrays.asList(
                new LendingRecord(new Isbn("1234567890123"), "1234567"),
                new LendingRecord(new Isbn("1234567890124"), "1234567")
                );

        when(lendingRecordRepository.find()).thenReturn(lendingRecords);
        List<BookStatus> actual = target.search();

        List<BookStatus> expected =
                Arrays.asList(
                        new BookStatus(null, lendingRecords.get(0)),
        new BookStatus(null, lendingRecords.get(1))
                );
        assertThat(actual).isEqualTo(expected);
    }
}
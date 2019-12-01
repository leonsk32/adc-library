package com.example.library.infra;

import com.example.library.domain.book.Book;
import com.example.library.domain.book.BookRepository;
import com.example.library.domain.lending.LendingRecord;
import com.example.library.domain.ranking.Ranking;
import com.example.library.domain.user.User;
import com.example.library.domain.user.UserRepository;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryImplTest {

    @Autowired
    UserRepository target;

    @Autowired
    BookRepository bookRepository;
    @Autowired
    LendingRecordRepositoryImpl lendingRecordRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("Delete from LENDING_EVENT");
        jdbcTemplate.execute("Delete from BOOK");

        jdbcTemplate.execute("Delete from USERR");
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("Delete from LENDING_EVENT");
        jdbcTemplate.execute("Delete from BOOK");

        jdbcTemplate.execute("Delete from USERR");
    }

    @DisplayName("取得できるケース")
    @Test
    void test03() {
        String user_id = "1234567";
        User user1 = new User(user_id, "aabb", "a", "a");
        target.register(user1);

        User user = target.findById(user_id);

        assertThat(user).isEqualTo(user1);

        target.delete("1234567");
        User user2 = target.findById(user_id);
        assertThat(user2).isNull();
    }

    @DisplayName("取得できるケース")
    @Test
    void test01() {
        String user_id = "1234567";
        target.register(new User("1234567", "aa@bb", "a", "b"));
        User user = target.findById(user_id);

        assertThat(user).isEqualTo(new User(user_id, "aa@bb"));
    }

    @DisplayName("取得できないケース")
    @Test
    void test02() {
        String user_id = "1234567";
        User user = target.findById(user_id);
        assertThat(user).isNull();
    }

    @Nested
    class ranking {
        @Test
        void test_01() {
            // GIVEN
            bookRepository.register(new Book("9784567890978"));
            bookRepository.register(new Book("9784567890124"));
            bookRepository.register(new Book("9784567890125"));
            jdbcTemplate.execute("insert into USERR(user_id, email) values(9784567, 'aa@bb')");
            jdbcTemplate.execute("insert into USERR(user_id, email) values(9784568, 'ab@bb')");
            jdbcTemplate.execute("insert into USERR(user_id, email) values(9784569, 'ac@bb')");

            LendingRecord entity1 = new LendingRecord(new Book("9784567890978"), new User("9784567", "aa@BB"));
            LendingRecord entity2 = new LendingRecord(new Book("9784567890124"), new User("9784568", "ab@BB"));
            LendingRecord entity3 = new LendingRecord(new Book("9784567890125"), new User("9784569", "ac@BB"));

            lendingRecordRepository.registerForLendingEvent(entity1);
            lendingRecordRepository.registerForLendingEvent(entity2);
            lendingRecordRepository.registerForLendingEvent(entity3);

            SoftAssertions softly = new SoftAssertions();
            // THEN
            List<Ranking> actual = target.findLentRanking();
            softly.assertThat(actual).hasSize(3);
            softly.assertAll();
        }

    }

}

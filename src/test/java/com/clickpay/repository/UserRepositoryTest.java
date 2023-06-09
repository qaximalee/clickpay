package com.clickpay.repository;

import com.clickpay.model.user.User;
import com.clickpay.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByUsername() {
        // Create a test user
        User user = new User();
        user.setUsername("qasim");
        user.setFirstName("Qasim");
        user.setLastName("Ali");
        user.setActive(true);
        user.setEmail("qasim@engro.com");
        user.setPassword("abc212332#@1@");

        entityManager.persist(user);
        entityManager.flush();

        // Perform the repository operation
        User foundUser = userRepository.findByUsername("qasim");

        // Assert the result
        System.out.println("USERNAME: "+ foundUser.getUsername());
    }

}



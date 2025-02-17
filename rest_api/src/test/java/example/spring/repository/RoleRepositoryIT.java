package example.spring.repository;

import example.spring.config.TestContainerConfig;
import example.spring.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.Assert.assertNotNull;

@DataJpaTest
@Testcontainers
@ContextConfiguration(classes = TestContainerConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoleRepositoryIT {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void findByNameTest() {
        Role result = roleRepository.findByName("EMPLOYEE");

        assertNotNull(result);
    }
}

package example.spring.util.validation;

import example.spring.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UniqueEmailValidatorUnitTest {
    @Mock
    private UserRepository userRepository;
    private UniqueEmailValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UniqueEmailValidator(userRepository);
    }

    @Test
    void emailIsUniqueTest() {
        when(userRepository.existsByEmail("unique@mail.com")).thenReturn(false);

        boolean isValid = validator.isValid("unique@mail.com", null);

        assertTrue(isValid);
    }

    @Test
    void emailIsNotUnique() {
        when(userRepository.existsByEmail("notunique@mail.com")).thenReturn(true);

        boolean isValid = validator.isValid("notunique@mail.com", null);

        assertFalse(isValid);
    }
}

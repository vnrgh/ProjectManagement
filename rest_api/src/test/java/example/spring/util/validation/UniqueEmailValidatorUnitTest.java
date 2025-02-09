package example.spring.util.validation;

import example.spring.repository.UserRepository;
import example.spring.util.validation.UniqueEmailValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@ExtendWith(MockitoExtension.class)
public class UniqueEmailValidatorUnitTest {
    @Mock
    private UserRepository userRepository;
    private UniqueEmailValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UniqueEmailValidator(userRepository);
    }

    @Test
    void emailIsUniqueTest() {
        Mockito.when(userRepository.existsByEmail("unique@mail.com")).thenReturn(false);

        boolean isValid = validator.isValid("unique@mail.com", null);

        assertTrue(isValid);
    }

    @Test
    void emailIsNotUnique() {
        Mockito.when(userRepository.existsByEmail("notunique@mail.com")).thenReturn(true);

        boolean isValid = validator.isValid("notunique@mail.com", null);

        assertFalse(isValid);
    }
}

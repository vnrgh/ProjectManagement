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
class UniqueUsernameValidatorUnitTest {
    @Mock
    private UserRepository userRepository;
    private UniqueUsernameValidator validator;

    @BeforeEach
    void setUp() {
        validator = new UniqueUsernameValidator(userRepository);
    }

    @Test
    void usernameIsUnique() {
        when(userRepository.existsByUsername("uniqueUsername")).thenReturn(false);

        boolean isValid = validator.isValid("uniqueUsername", null);

        assertTrue(isValid);
    }

    @Test
    void usernameIsNotUnique() {
        when(userRepository.existsByUsername("notUniqueUsername")).thenReturn(true);

        boolean isValid = validator.isValid("notUniqueUsername", null);

        assertFalse(isValid);
    }
}

package example.spring.util.validation;

import example.spring.repository.TechnologyRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueTechnologyNameValidation implements ConstraintValidator<UniqueTechnologyName, String> {
    private final TechnologyRepository technologyRepository;

    public UniqueTechnologyNameValidation(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext constraintValidatorContext) {
        return !technologyRepository.existsByName(name);
    }
}

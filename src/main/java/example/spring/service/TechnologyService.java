package example.spring.service;

import example.spring.TaskNotFoundException;
import example.spring.model.Technology;
import example.spring.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

@Service
public class TechnologyService {
    private final TechnologyRepository technologyRepository;

    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public Long createTechnology(Technology technology) {
        return technologyRepository.createTechnology(technology);
    }

    public Technology getTechnologyById(Long id) {
        //todo
        return technologyRepository.getTechnologyById(id).orElseThrow(() -> new TaskNotFoundException("Project with id " + id + " was not found"));
    }
}

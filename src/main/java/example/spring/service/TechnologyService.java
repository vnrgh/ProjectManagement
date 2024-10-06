package example.spring.service;

import example.spring.exception.TaskNotFoundException;
import example.spring.exception.TechnologyNotFoundException;
import example.spring.model.Technology;
import example.spring.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return technologyRepository.getTechnologyById(id).orElseThrow(() -> new TechnologyNotFoundException("Technology with id " + id + " was not found"));
    }

    public List<Technology> getAllTechnologies() {
        return technologyRepository.getAllTechnologies();
    }

    public void updateTechnologyById(Long id, Technology technology) {
        technologyRepository.updateTechnologyById(id, technology);
    }

    public void deleteTechnologyById(Long id) {
        technologyRepository.deleteTechnologyById(id);
    }
}

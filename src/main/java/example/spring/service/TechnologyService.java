package example.spring.service;

import example.spring.exception.TechnologyNotFoundException;
import example.spring.model.Technology;
import example.spring.model.dto.TechnologyDTO;
import example.spring.repository.TechnologyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TechnologyService {
    private final TechnologyRepository technologyRepository;

    public TechnologyService(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    public Long createTechnology(TechnologyDTO technologyDTO) {
        Technology technology = Technology.builder()
                .name(technologyDTO.getName())
                .build();
        return technologyRepository.save(technology).getId();
    }

    public Technology getTechnologyById(Long id) {
        return technologyRepository.findById(id).orElseThrow(() -> new TechnologyNotFoundException("Technology with id " + id + " not found"));
    }

    public List<Technology> getAllTechnologies() {
        return technologyRepository.findAll();
    }

    public void updateTechnologyById(Long id, TechnologyDTO technologyDTO) {
        Technology existingTechnology = technologyRepository.findById(id)
                .orElseThrow(() -> new TechnologyNotFoundException("Technology with id " + id + " not found"));

        existingTechnology.setName(technologyDTO.getName());
        technologyRepository.save(existingTechnology);
    }

    public void deleteTechnologyById(Long id) {
        technologyRepository.deleteById(id);
    }
}

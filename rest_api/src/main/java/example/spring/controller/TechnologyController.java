package example.spring.controller;

import example.spring.model.Technology;
import example.spring.model.dto.TechnologyDTO;
import example.spring.service.TechnologyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/tech")
public class TechnologyController {
    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @PostMapping
    private Long createTechnology(@Valid @RequestBody TechnologyDTO technologyDTO) {
        return technologyService.createTechnology(technologyDTO);
    }

    @GetMapping("/{id}")
    private Technology getTechnologyById(@PathVariable Long id) {
        return technologyService.getTechnologyById(id);
    }

    @GetMapping
    private List<Technology> getAllTechnologies() {
        return technologyService.getAllTechnologies();
    }

    @PutMapping("/{id}")
    private ResponseEntity<String> updateTechnologyById(@PathVariable Long id, @RequestBody TechnologyDTO technologyDTO) {
        technologyService.updateTechnologyById(id, technologyDTO);
        return new ResponseEntity<>("Technology with id " + id + " was updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteTechnologyById(@PathVariable Long id) {
        technologyService.deleteTechnologyById(id);
        return new ResponseEntity<>("Technology with id " + id + " was deleted", HttpStatus.OK);
    }
}

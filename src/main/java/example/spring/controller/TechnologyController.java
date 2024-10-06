package example.spring.controller;

import example.spring.model.Technology;
import example.spring.service.TechnologyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tech")
public class TechnologyController {
    private final TechnologyService technologyService;

    public TechnologyController(TechnologyService technologyService) {
        this.technologyService = technologyService;
    }

    @PostMapping
    private Long createTechnology(@RequestBody Technology technology) {
        return technologyService.createTechnology(technology);
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
    private ResponseEntity<String> updateTechnologyById(@PathVariable Long id, @RequestBody Technology technology) {
        technologyService.updateTechnologyById(id, technology);
        return new ResponseEntity<>("Technology with id " + id + " was updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> deleteTechnologyById(@PathVariable Long id) {
        technologyService.deleteTechnologyById(id);
        return new ResponseEntity<>("Technology with id " + id + " was deleted", HttpStatus.OK);
    }
}

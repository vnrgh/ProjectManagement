package example.spring.controller;

import example.spring.model.Technology;
import example.spring.service.TechnologyService;
import org.springframework.web.bind.annotation.*;

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
}

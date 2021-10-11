package tech.ropaki.ivoting.web.rest;

import org.springframework.web.bind.annotation.*;
import tech.ropaki.ivoting.domain.University;
import tech.ropaki.ivoting.service.UniversityService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UniversityResource {

    private final UniversityService universityService;

    public UniversityResource(UniversityService universityService) {
        this.universityService = universityService;
    }

    @PostMapping("/universities")
    public University save(@RequestBody University university){

        return universityService.save(university);
    }

    @PutMapping("/universities")
    public University update(@RequestBody University university){

        return universityService.update(university);
    }

    @GetMapping("/universities")
    public List<University> findAll(){

        return universityService.getAll();
    }

    @GetMapping("/universities/{id}")
    public University getOne(@PathVariable Integer id) throws Exception {

        return universityService.getOne(id);
    }

    @PostMapping("/universities/change-state")
    public University changeState(@RequestBody University university){

        return universityService.changeState(university);
    }
}

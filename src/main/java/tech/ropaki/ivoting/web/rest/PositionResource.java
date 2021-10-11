package tech.ropaki.ivoting.web.rest;

import org.springframework.web.bind.annotation.*;
import tech.ropaki.ivoting.domain.Position;
import tech.ropaki.ivoting.service.PositionService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PositionResource {

    private final PositionService positionService;

    public PositionResource(PositionService positionService) {
        this.positionService = positionService;
    }

    @PostMapping("/positions")
    public Position save(@RequestBody Position position){

        return positionService.save(position);
    }

    @GetMapping("/positions")
    public List<Position> getAll(){

        return positionService.getAll();
    }

    @PutMapping("/positions")
    public Position updateOne(@RequestBody Position position){

        return positionService.update(position);
    }

    @DeleteMapping("/positions/{id}")
    public void deleteOne(@PathVariable Long id){

        positionService.deleteOne(id);
    }
}

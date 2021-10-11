package tech.ropaki.ivoting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.ropaki.ivoting.domain.Position;
import tech.ropaki.ivoting.repository.PositionRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    private final Logger logger = LoggerFactory.getLogger(PositionService.class);

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public Position save(Position position){
        logger.info("About to save position : {}",position);

        return positionRepository.save(position);
    }

    public List<Position> getAll(){
        logger.info("About to get all positions");

        List<Position> positions = new ArrayList<>();

        for(Position position : positionRepository.findAll()){
            positions.add(position);
        }

        return positions;
    }

    public void deleteOne(Long id){

        logger.info("About to delete position with id : {}",id);

        positionRepository.deleteById(id);

        logger.info("Successfully deleted position");

    }

    public Position update(Position position){

        logger.info("About to update position with id : {}",position.getId());

        if(position.getId()==null){
            logger.info("Id was not passed, doing an initial save for : {}",position);
            save(position);
        }

        return positionRepository.save(position);
    }

    public List<Position> findByUniversity(String university){
        List<Position> positions = new ArrayList<>();

        for(Position position : positionRepository.findByUniversity(university)){
            positions.add(position);
        }

        return positions;
    }

}

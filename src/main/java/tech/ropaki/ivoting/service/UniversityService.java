package tech.ropaki.ivoting.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tech.ropaki.ivoting.domain.University;
import tech.ropaki.ivoting.repository.UniversityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UniversityService {

    private final Logger logger = LoggerFactory.getLogger(UniversityService.class);

    private final UniversityRepository universityRepository;

    public UniversityService(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    public University save(University university){
        logger.info("About to save university : {}",university);

        university.setIsOpen(Boolean.FALSE);

        if(university.getId()!=null){
            update(university);
        }

        return universityRepository.save(university);
    }

    public University update(University university){
        logger.info("About to update university : {}",university);

        if(university.getId()==null){
            save(university);
        }

        return universityRepository.save(university);
    }

    public University getOne(Integer id) throws Exception {
        logger.info("About to get university with id : {} ",id );

        Optional<University> optionalUniversity = universityRepository.findById(id);

        if(optionalUniversity.isPresent()){
            return optionalUniversity.get();
        }else{
            throw new Exception("University with the specified id does not exist");
        }
    }

    public List<University> getAll(){
        logger.info("About to get all universities");

        List<University> universities = new ArrayList<>();

        for(University university : universityRepository.findAll()){
            universities.add(university);
        }

        return universities;
    }

    public University changeState(University university){
        if(university.getIsOpen()==Boolean.TRUE){
            logger.info("Opening the election");
        }else{
            logger.info("Closing the election");
        }

        return universityRepository.save(university);
    }
}

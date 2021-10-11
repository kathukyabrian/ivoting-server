package tech.ropaki.ivoting.domain;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import tech.ropaki.ivoting.repository.PositionRepository;

@DataJdbcTest
public class PositionTest {

    private final Logger logger = LoggerFactory.getLogger(PositionTest.class);

    @Autowired
    private PositionRepository positionRepository;

    public PositionTest(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @Test
    void checkPositions(){
        Position position = new Position();
        position.setName("President");{
        this.positionRepository = positionRepository;
    }
        positionRepository.save(position);

        logger.info("Saved position");
        assert positionRepository.count()>0;
    }
}

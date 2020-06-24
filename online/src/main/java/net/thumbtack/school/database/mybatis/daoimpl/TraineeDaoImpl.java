package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.TraineeDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class TraineeDaoImpl extends DaoImplBase implements TraineeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraineeDaoImpl.class);

    @Override
    public Trainee insert(Group group, Trainee trainee) {
        LOGGER.debug("DAO insert Trainee{}", trainee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).insert(trainee, group);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t insert Trainee{}", trainee);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return trainee;
    }

    @Override
    public Trainee getById(int id) {
        LOGGER.debug("DAO get Trainee by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getTraineeMapper(sqlSession).getById(id);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t get Trainee  {}", e);
                throw e;
            }
        }
    }

    @Override
    public List<Trainee> getAll() {
        LOGGER.debug("DAO get all Trainees");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getTraineeMapper(sqlSession).getAll();
            } catch (RuntimeException e) {
                LOGGER.info("Can`t get all Trainees {}", e);
                throw e;
            }
        }
    }

    @Override
    public Trainee update(Trainee trainee) {
        LOGGER.debug("DAO update trainee");
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).update(trainee, trainee.getFirstName(), trainee.getLastName(), trainee.getRating());
            } catch (RuntimeException e) {
                LOGGER.info("Can`t update Trainee{}, {}", trainee, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return trainee;
    }

    @Override
    public List<Trainee> getAllWithParams(String firstName, String lastName, Integer rating) {
        LOGGER.debug("DAO insert all Trainees with params {},{},{}", firstName, lastName, rating);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getTraineeMapper(sqlSession).getAllWithParams(firstName, lastName, rating);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t get all Trainees with params {}", e);
                throw e;
            }
        }
    }

    @Override
    public void batchInsert(List<Trainee> trainees) {
        LOGGER.debug("DAO batch insert Trainees{}", trainees);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).batchInsert(trainees);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t batch insert Trainees {}, {}", trainees, e);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(Trainee trainee) {
        LOGGER.debug("DAO delete Trainee {}", trainee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).delete(trainee);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t delete Trainee {}, {}", trainee, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("DAO delete all trainees");
        try (SqlSession sqlSession = getSession()) {
            try {
                getTraineeMapper(sqlSession).deleteAll();
            } catch (RuntimeException e) {
                LOGGER.info("Can`t delete all Trainees {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }
}

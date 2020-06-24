package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.SchoolDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SchoolDaoImpl extends DaoImplBase implements SchoolDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(SchoolDaoImpl.class);

    @Override
    public School insert(School school) {
        LOGGER.debug("DAO insert School");
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).insert(school);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t insert School {}, {}", school, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return school;
    }

    @Override
    public School getById(int id) {
        LOGGER.debug("DAO get School by id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                return getSchoolMapper(sqlSession).getById(id);
            } catch (RuntimeException e) {
                LOGGER.info("Can't get School ");
                throw e;
            }
        }
    }

    @Override
    public List<School> getAllLazy() {
        LOGGER.debug("DAO get all Schools lazy");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getSchoolMapper(sqlSession).getAllLazy();
            } catch (RuntimeException e) {
                LOGGER.info("Can't get all Schools lazy {}", e);
                throw e;
            }
        }
    }

    @Override
    public List<School> getAllUsingJoin() {
        LOGGER.debug("DAO get all Schools using join");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getSchoolMapper(sqlSession).getAllUsingJoin();
            } catch (RuntimeException e) {
                LOGGER.info("Can't get all Schools using join {}", e);
                throw e;
            }
        }
    }

    @Override
    public void update(School school) {
        LOGGER.debug("DAO update School {}", school);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).update(school, school.getYear(), school.getName());
            } catch (RuntimeException e) {
                LOGGER.info("Can't update School {}, {}", school, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void delete(School school) {
        LOGGER.debug("DAO delete School {}", school);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).delete(school);
            } catch (RuntimeException e) {
                LOGGER.info("Can't delete School {}, {}", school, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void deleteAll() {
        LOGGER.debug("DAO delete all Schools");
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).deleteAll();
            } catch (RuntimeException e) {
                LOGGER.info("Can`t delete all Schools {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }


    @Override
    public School insertSchoolTransactional(School school2018) {
        LOGGER.debug("DAO Transactional insert {}", school2018);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).insert(school2018);
                for (Group group : school2018.getGroups()) {
                    getGroupMapper(sqlSession).insert(school2018, group);
                    for (Trainee trainee : group.getTrainees()) {
                        getTraineeMapper(sqlSession).insert(trainee, group);
                    }
                    for (Subject subject : group.getSubjects()) {
                        getGroupMapper(sqlSession).addSubjectToGroup(group, subject);
                    }
                }
            } catch (RuntimeException e) {
                LOGGER.info("Can't transactional insert school {}, {}", school2018, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return school2018;
    }
}

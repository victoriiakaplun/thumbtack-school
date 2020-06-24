package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import net.thumbtack.school.database.mybatis.dao.GroupDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class GroupDaoImpl extends DaoImplBase implements GroupDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(GroupDaoImpl.class);

    @Override
    public Group insert(School school, Group group) {
        LOGGER.debug("DAO insert group {}", group);
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).insert(school, group);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t insert group {},{}", group, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return group;
    }

    @Override
    public Group update(Group group) {
        LOGGER.debug("DAO update Group {}", group);
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).update(group, group.getName(), group.getRoom());
            } catch (RuntimeException e) {
                LOGGER.info("Can`t update group {}, {}", group, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
        return group;
    }

    @Override
    public List<Group> getAll() {
        LOGGER.debug("DAO get all Groups");
        try (SqlSession sqlSession = getSession()) {
            try {
                return getGroupMapper(sqlSession).getAll();
            } catch (RuntimeException e) {
                LOGGER.info("Can`t get all Groups {}", e);
                throw e;
            }
        }
    }

    @Override
    public void delete(Group group) {
        LOGGER.debug("DAO delete Group {}", group);
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).delete(group);
            } catch (RuntimeException e) {
                LOGGER.info("Can`t delete Group {}, {}", group, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }

    @Override
    public Trainee moveTraineeToGroup(Group group, Trainee trainee) {
        LOGGER.debug("DAO move Trainee to Group {}, {}", trainee, group);
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).moveTraineeToGroup(group, trainee);
            } catch (RuntimeException e) {
                LOGGER.info("Can't move Trainee to Group {}, {}, {}", trainee, group, e);
                sqlSession.rollback();
            }
            sqlSession.commit();
        }
        return trainee;
    }

    @Override
    public void deleteTraineeFromGroup(Trainee trainee) {
        LOGGER.debug("DAO delete trainee from group {}", trainee);
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).deleteTraineeFromGroup(trainee);
            } catch (RuntimeException e) {
                LOGGER.info("Can't delete Trainee from Group {}, {}", trainee, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }

    @Override
    public void addSubjectToGroup(Group group, Subject subject) {
        LOGGER.debug("DAO add Subject to Group {}, {}", subject, group);
        try (SqlSession sqlSession = getSession()) {
            try {
                getGroupMapper(sqlSession).addSubjectToGroup(group, subject);
            } catch (RuntimeException e) {
                LOGGER.info("Can't add Subject to Group {},{},{}", subject, group, e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }
}

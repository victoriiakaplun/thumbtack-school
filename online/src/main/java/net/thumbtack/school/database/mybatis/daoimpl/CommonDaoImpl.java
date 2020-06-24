package net.thumbtack.school.database.mybatis.daoimpl;

import net.thumbtack.school.database.mybatis.dao.CommonDao;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonDaoImpl extends DaoImplBase implements CommonDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);

    @Override
    public void clear() {
        LOGGER.debug("DAO clear database");
        try (SqlSession sqlSession = getSession()) {
            try {
                getSchoolMapper(sqlSession).deleteAll();
                getSubjectMapper(sqlSession).deleteAll();
                getTraineeMapper(sqlSession).deleteAll();
            } catch (RuntimeException e) {
                LOGGER.info("Can`t clear database {}", e);
                sqlSession.rollback();
                throw e;
            }
            sqlSession.commit();
        }
    }
}

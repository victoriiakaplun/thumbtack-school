package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Trainee;
import org.apache.ibatis.annotations.*;

import java.util.List;


public interface TraineeMapper {

    @Insert("INSERT into trainee (firstname, lastname, rating, group_id) VALUES " + "( #{trainee.firstName}, #{trainee.lastName}, #{trainee.rating}, #{group.id} )")
    @Options(useGeneratedKeys = true, keyProperty = "trainee.id")
    Integer insert(@Param("trainee") Trainee trainee, @Param("group") Group group);

    @Update("UPDATE trainee SET firstname = #{firstName}, lastname = #{lastName}, rating = #{rating} WHERE id = #{trainee.id}")
    void update(@Param("trainee") Trainee trainee, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("rating") int rating);

    @Delete("DELETE FROM trainee")
    void deleteAll();

    @Insert({"<script>",
            "INSERT INTO trainee (firstname, lastname, rating) VALUES",
            "<foreach item='item' collection='list' separator=','>",
            "( #{item.firstName}, #{item.lastName}, #{item.rating})",
            "</foreach>",
            "</script>" })
    @Options(useGeneratedKeys = true)
    void batchInsert(@Param("list") List<Trainee> trainees);

    @Delete("DELETE FROM trainee WHERE id = #{trainee.id}")
    void delete(@Param("trainee") Trainee trainee);

    @Select("SELECT id, firstname as firstName, lastname as lastName, rating FROM trainee WHERE id = #{id}")
    Trainee getById(int id);

    @Select("SELECT id, firstname as firstName, lastname as lastName, rating FROM trainee")
    List<Trainee> getAll();

    @Select({"<script>",
            "SELECT id, firstname as firstName, lastname as lastName, rating FROM trainee",
            "<where>" +
                    "<if test='firstName != null'> firstname LIKE #{firstName}",
            "</if>",
            "<if test='lastName != null'> AND lastname LIKE #{lastName}",
            "</if>",
            "<if test='rating != null'> AND rating =  #{rating}",
            "</if>",
            "</where>" +
                    "</script>" })
    List<Trainee> getAllWithParams(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("rating") Integer rating);

    @Select("SELECT trainee.id as id, firstname as firstName, lastname as lastName, rating from trainee WHERE group_id = #{group.id}")
    List<Trainee> getByGroup(@Param("group") Group group);
}

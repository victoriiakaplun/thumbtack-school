package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.School;
import net.thumbtack.school.database.model.Subject;
import net.thumbtack.school.database.model.Trainee;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface GroupMapper {
    @Insert("INSERT into `group` (name, room, school_id) VALUES " + "( #{group.name}, #{group.room}, #{school.id} )")
    @Options(useGeneratedKeys = true, keyProperty = "group.id")
    Integer insert(@Param("school") School school, @Param("group") Group group);

    @Update("UPDATE `group` SET name = #{group_name}, room = #{group_room} WHERE id = #{group.id}")
    void update(@Param("group") Group group, @Param("group_name") String name, @Param("group_room") String room);

    @Select("SELECT id, name, room FROM `group`")
    List<Group> getAll();

    @Delete("DELETE from `group` WHERE id = #{group.id}")
    void delete(@Param("group") Group group);

    @Update("UPDATE trainee SET group_id = NULL WHERE id = #{trainee.id}")
    void deleteTraineeFromGroup(@Param("trainee") Trainee trainee);

    @Insert("INSERT into subject_group (subject_id, group_id) VALUES" + "(#{subject.id}, #{group.id})")
    void addSubjectToGroup(@Param("group") Group group, @Param("subject") Subject subject);

    @Update("UPDATE trainee SET group_id = #{group.id} WHERE id = #{trainee.id}")
    void moveTraineeToGroup(@Param("group") Group group, @Param("trainee") Trainee trainee);

    @Select("SELECT id, name, room FROM `group` WHERE school_id = #{school.id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "trainees", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.TraineeMapper.getByGroup", fetchType = FetchType.LAZY)),
            @Result(property = "subjects", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.SubjectMapper.getByGroup", fetchType = FetchType.LAZY))
    }
    )
    List<Group> getBySchool(@Param("school") School school);
}

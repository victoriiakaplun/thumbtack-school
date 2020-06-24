package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.Group;
import net.thumbtack.school.database.model.Subject;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SubjectMapper {
    @Insert("INSERT INTO subject (subject_name) VALUES" + "(#{name})")
    @Options(useGeneratedKeys = true)
    Integer insert(Subject subject);

    @Select("SELECT id, subject_name as name FROM subject WHERE id = #{id}")
    Subject getById(int id);

    @Delete("DELETE FROM subject")
    void deleteAll();

    @Delete("DELETE FROM subject WHERE id = #{subject.id}")
    void delete(@Param("subject") Subject subject);

    @Update("UPDATE subject SET subject_name = #{name} WHERE id = #{subject.id}")
    void update(@Param("subject") Subject subject, @Param("name") String name);

    @Select("SELECT id, subject_name as name FROM subject")
    List<Subject> getAll();

    @Select("SELECT id, subject_name as name from subject WHERE id IN(SELECT subject_id from subject_group WHERE group_id = #{group.id})")
    List<Subject> getByGroup(Group group);
}

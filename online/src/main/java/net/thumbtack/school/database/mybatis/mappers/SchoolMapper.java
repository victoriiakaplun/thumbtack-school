package net.thumbtack.school.database.mybatis.mappers;

import net.thumbtack.school.database.model.School;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

public interface SchoolMapper {

    @Insert("INSERT INTO school (school_name, y) VALUES" + "(#{name}, #{year})")
    @Options(useGeneratedKeys = true)
    Integer insert(School school);


    @Select("SELECT id, school_name as name, y as year FROM school WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "groups", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.GroupMapper.getBySchool", fetchType = FetchType.LAZY)),
    })
    School getById(int id);

    @Delete("DELETE FROM school")
    void deleteAll();

    @Delete("DELETE from school WHERE id = #{school.id}")
    void delete(@Param("school") School school);

    @Update("UPDATE school SET y = #{year}, school_name = #{name} WHERE id = #{school.id}")
    void update(@Param("school") School school, @Param("year") Integer year, @Param("name") String name);


    @Select("SELECT id, school_name as name, y as year FROM school")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "groups", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.database.mybatis.mappers.GroupMapper.getBySchool", fetchType = FetchType.LAZY)),
    })
    List<School> getAllLazy();

    List<School> getAllUsingJoin();
}

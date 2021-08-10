package com.webapp.backend.mappers;

import java.util.List;

import com.webapp.backend.models.Todo;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TodoMapper {
    @Select("SELECT * FROM Todos")
    List<Todo> GetAll();

    @Select("SELECT * FROM Todos WHERE idTodo = #{id}")
    Todo GetTodo(long id);

    @Options(useGeneratedKeys = true, keyProperty = "idTodo", keyColumn = "idTodo")
    @Insert("INSERT into Todos(titel, description, dueDate, isDone) VALUES(#{titel}, #{description}, #{dueDate}, #{isDone}) ")
    Integer AddTodo(Todo newTodo);

    @Options(useGeneratedKeys = true, keyProperty = "idTodo", keyColumn = "idTodo")
    @Update("UPDATE Todos SET titel=#{titel}, description=#{description}, dueDate=#{dueDate}, isDone=#{isDone} WHERE idTodo=#{idTodo}")
    Integer UpdateTodo(Todo newTodo);

    @Delete("DELETE FROM Todos WHERE idTodo =#{id}")
	Integer deleteVillage(int id);
}
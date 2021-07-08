package com.edmundo.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edmundo.todolist.model.Tasks;

public interface TasksRepository extends JpaRepository<Tasks, Integer> {
	
}

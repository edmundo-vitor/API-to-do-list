package com.edmundo.todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edmundo.todolist.dto.MessageDTO;
import com.edmundo.todolist.model.Tasks;
import com.edmundo.todolist.service.TasksService;

@RestController
@RequestMapping(value = "/api")
public class TasksController {
	
	@Autowired
	TasksService tasksService;
	
	@GetMapping("/tasks")
	public List<Tasks> getTasks() {
		return tasksService.getAllTasks();
	}
	
	@PostMapping("/tasks")
	public ResponseEntity<?> addTasks(@RequestBody Tasks task) {
		return tasksService.addTask(task);
	}
	
	@PutMapping("/tasks/{id}")
	public ResponseEntity<?> updateTasks(@PathVariable Integer id, @RequestBody Tasks task) {
		return tasksService.updateTasks(id, task);
	}
	
	@PatchMapping("/tasks/{id}")
	public ResponseEntity<?> patchTasks(@PathVariable Integer id, @RequestBody Tasks task){
		return tasksService.patchTasks(id, task);
	}
	
	@DeleteMapping("/tasks/{id}")
	public  ResponseEntity<MessageDTO> deleteTasks(@PathVariable Integer id) {
		return tasksService.deleteTask(id);
	}
}

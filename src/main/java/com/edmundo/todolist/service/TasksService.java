package com.edmundo.todolist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.edmundo.todolist.dto.MessageDTO;
import com.edmundo.todolist.model.Tasks;
import com.edmundo.todolist.repository.TasksRepository;

@Service
public class TasksService {
	
	@Autowired
	TasksRepository tasksRepo;
	
	public List<Tasks> getAllTasks() {
		return tasksRepo.findAll();
	}
	
	public ResponseEntity<?> addTask(Tasks task) {
		if(task.getTitle() == null || task.getTitle().isEmpty()) {
			MessageDTO error = new MessageDTO("Título da tarefa vazio ou com caracteres inválidos");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
			
		}else {
			try {
				Tasks response = tasksRepo.save(task);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
			}
		}
	}
	
	public ResponseEntity<?> updateTasks(Integer id, Tasks updateTask) {
		boolean change = false;
		
		Optional<Tasks> optionalTask = tasksRepo.findById(id);
		Tasks task = optionalTask.get();
		
		//Update data
		if(updateTask.getTitle() != null && !updateTask.getTitle().isEmpty()) {
			task.setTitle(updateTask.getTitle());
			change = true;
		}
		if(updateTask.getDescription() != null && !updateTask.getDescription().isEmpty()) {
			task.setDescription(updateTask.getDescription());
			change = true;
		}
		
		//check if there was any change in the task
		if(change) {
			Tasks response = tasksRepo.save(task);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			MessageDTO error = new MessageDTO("Nenhum dado encontrado na tarefa");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}
		
	}
	
	public  ResponseEntity<MessageDTO> deleteTask(Integer id) {
		try {
			tasksRepo.deleteById(id);
			MessageDTO message = new MessageDTO("Tarefa deletada com sucesso!");
			return ResponseEntity.status(HttpStatus.OK).body(message);
			
		} catch (Exception e) {
			MessageDTO error = new MessageDTO(e.getMessage());
			return ResponseEntity.status(HttpStatus.OK).body(error);
		}
	}
}

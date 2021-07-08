package com.edmundo.todolist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.edmundo.todolist.dto.MessageDTO;
import com.edmundo.todolist.model.Tasks;
import com.edmundo.todolist.repository.TasksRepository;
import com.edmundo.todolist.util.PatchUtil;

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
		Tasks task = tasksRepo.findById(id).get();

		
		//Update data
		if(task != null && updateTask != null) {
			updateTask.setId(id);
			
			try {
				Tasks response = tasksRepo.save(updateTask);
				return ResponseEntity.ok(response);
			} catch (Exception e) {
				MessageDTO message = new MessageDTO(e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
			}	
			
		}else {
			MessageDTO message = new MessageDTO("Informe valores válidos/não nulos.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
		}
		
	}
	
	public ResponseEntity<?> patchTasks(Integer id, Tasks updateTask) {
		Tasks task = tasksRepo.findById(id).get();
		
		if(task != null) {
			PatchUtil.updateIfNotNull(task::setTitle, updateTask.getTitle());
			PatchUtil.updateIfNotNull(task::setDescription, updateTask.getDescription());
			
			try {
				Tasks response = tasksRepo.save(task);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} catch (Exception e) {
				MessageDTO message = new MessageDTO(e.getMessage());
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
			}
		}else {
			MessageDTO message = new MessageDTO("Essa task não existe.");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
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

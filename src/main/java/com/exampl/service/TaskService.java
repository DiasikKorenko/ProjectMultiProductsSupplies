package com.exampl.service;

import com.exampl.domain.Task;
import com.exampl.domain.User;
import com.exampl.repository.TaskRepository;
import com.exampl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    private final AdminService adminService;

    @Autowired
    public TaskService(TaskRepository taskRepository, AdminService adminService) {
        this.taskRepository = taskRepository;

        this.adminService = adminService;
    }


    public List<Task> getAllTaskByAdmin(int userId) {
        List<Task> tasks = taskRepository.findAllByAdminId(userId);
        return tasks;
    }

    public void adminCreateTask(Integer adminId, Task task) {
        task.setAdminId(adminId);
        taskRepository.saveAndFlush(task);
    }

    public boolean userDeleteTask(Integer taskId, Integer adminId) {
        Task taskDBEntity = taskRepository.findById(taskId).orElse(null);
        if (taskDBEntity == null || taskDBEntity.getAdminId() != adminId)
            return false;

        taskRepository.deleteById(taskId);
        taskRepository.flush();
        return true;
    }

    public boolean updateTaskById(Integer taskId, Integer currentUserId, Task updatedTask) {
        Task taskDBEntity = taskRepository.findById(taskId).orElse(null);

        if (taskDBEntity == null || !currentUserId.equals(taskDBEntity.getAdminId())) {
            return false;
        }

        // Проверяем, изменились ли данные задачи
        if (updatedTask.getNameTask() != null) {
            taskDBEntity.setNameTask(updatedTask.getNameTask());
        }
        if (updatedTask.getDiscription() != null) {
            taskDBEntity.setDiscription(updatedTask.getDiscription());
        }
        if (updatedTask.getDateTask() != null) {
            taskDBEntity.setDateTask(updatedTask.getDateTask());
        }

        // Сохранение обновленных данных задачи
        taskRepository.save(taskDBEntity);

        return true;
    }
}

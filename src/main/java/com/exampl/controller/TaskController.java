package com.exampl.controller;

import com.exampl.domain.Order;
import com.exampl.domain.Task;
import com.exampl.service.AdminService;
import com.exampl.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component
@RequestMapping("/task")
public class TaskController {

    AdminService adminService;
    TaskService taskService;

    @Autowired
    public TaskController(AdminService adminService,TaskService taskService) {
        this.adminService = adminService;
        this.taskService = taskService;
    }

    @GetMapping("/taskCreated")
    public String allTask(Model model) {
        List<Task> tasks = taskService.getAllTaskByAdmin(adminService.getCurrentAdmin().getId());
        model.addAttribute("tasks", tasks);
        model.addAttribute("task", new Task());
        return "taskList";
    }

    @PostMapping("/create")
    public String createTask(@ModelAttribute Task task) {
        taskService.adminCreateTask(adminService.getCurrentAdmin().getId(), task);
        return "redirect:/task/taskCreated";
    }

    @PostMapping("/delete/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Integer taskId, Model model) {
        boolean isDeleted = taskService.userDeleteTask(taskId, adminService.getCurrentAdmin().getId());

        if (isDeleted) {

            return ResponseEntity.ok().build();
        } else {

            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/loadTasks")
    public List<Task> loadTasks() {
        int adminId = adminService.getCurrentAdmin().getId();
        return taskService.getAllTaskByAdmin(adminId);
    }



}

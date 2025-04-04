package com.example.workflow.controller;

import com.example.workflow.common.Result;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 获取任务列表
     */
    @GetMapping("/list")
    public Result<List<Map<String, Object>>> list(
            @RequestParam(required = false) String assignee,
            @RequestParam(required = false) String candidateUser,
            @RequestParam(required = false) String candidateGroup) {
        
        org.flowable.task.api.TaskQuery query = taskService.createTaskQuery();
        
        if (assignee != null && !assignee.isEmpty()) {
            query.taskAssignee(assignee);
        }
        if (candidateUser != null && !candidateUser.isEmpty()) {
            query.taskCandidateUser(candidateUser);
        }
        if (candidateGroup != null && !candidateGroup.isEmpty()) {
            query.taskCandidateGroup(candidateGroup);
        }

        List<Task> list = query.orderByTaskCreateTime().desc().list();
        List<Map<String, Object>> resultList = new ArrayList<>();

        for (Task task : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", task.getId());
            map.put("name", task.getName());
            map.put("description", task.getDescription());
            map.put("assignee", task.getAssignee());
            map.put("createTime", task.getCreateTime());
            map.put("dueDate", task.getDueDate());
            map.put("processInstanceId", task.getProcessInstanceId());
            map.put("processDefinitionId", task.getProcessDefinitionId());
            resultList.add(map);
        }

        return Result.success(resultList);
    }

    /**
     * 认领任务
     */
    @PostMapping("/{taskId}/claim")
    public Result<Void> claim(@PathVariable String taskId,
                            @RequestParam String userId) {
        try {
            taskService.claim(taskId, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error("认领任务失败：" + e.getMessage());
        }
    }

    /**
     * 取消认领任务
     */
    @PostMapping("/{taskId}/unclaim")
    public Result<Void> unclaim(@PathVariable String taskId) {
        try {
            taskService.unclaim(taskId);
            return Result.success();
        } catch (Exception e) {
            return Result.error("取消认领任务失败：" + e.getMessage());
        }
    }

    /**
     * 完成任务
     */
    @PostMapping("/{taskId}/complete")
    public Result<Void> complete(@PathVariable String taskId,
                               @RequestBody(required = false) Map<String, Object> variables) {
        try {
            if (variables != null) {
                taskService.complete(taskId, variables);
            } else {
                taskService.complete(taskId);
            }
            return Result.success();
        } catch (Exception e) {
            return Result.error("完成任务失败：" + e.getMessage());
        }
    }

    /**
     * 委派任务
     */
    @PostMapping("/{taskId}/delegate")
    public Result<Void> delegate(@PathVariable String taskId,
                               @RequestParam String userId) {
        try {
            taskService.delegateTask(taskId, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error("委派任务失败：" + e.getMessage());
        }
    }

    /**
     * 转办任务
     */
    @PostMapping("/{taskId}/assign")
    public Result<Void> assign(@PathVariable String taskId,
                             @RequestParam String userId) {
        try {
            taskService.setAssignee(taskId, userId);
            return Result.success();
        } catch (Exception e) {
            return Result.error("转办任务失败：" + e.getMessage());
        }
    }

    /**
     * 获取任务表单数据
     */
    @GetMapping("/{taskId}/form-data")
    public Result<Map<String, Object>> getTaskFormData(@PathVariable String taskId) {
        try {
            Map<String, Object> variables = taskService.getVariables(taskId);
            return Result.success(variables);
        } catch (Exception e) {
            return Result.error("获取任务表单数据失败：" + e.getMessage());
        }
    }

    /**
     * 添加任务评论
     */
    @PostMapping("/{taskId}/comment")
    public Result<Void> addComment(@PathVariable String taskId,
                                 @RequestParam String message) {
        try {
            taskService.addComment(taskId, null, message);
            return Result.success();
        } catch (Exception e) {
            return Result.error("添加任务评论失败：" + e.getMessage());
        }
    }
}
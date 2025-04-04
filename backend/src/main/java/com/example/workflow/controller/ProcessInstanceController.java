package com.example.workflow.controller;

import com.example.workflow.common.Result;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/process/instance")
public class ProcessInstanceController {

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 启动流程实例
     */
    @PostMapping("/start/{processDefinitionKey}")
    public Result<Map<String, Object>> startProcess(
            @PathVariable String processDefinitionKey,
            @RequestBody(required = false) Map<String, Object> variables) {
        try {
            ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
                    processDefinitionKey,
                    variables
            );

            Map<String, Object> result = new HashMap<>();
            result.put("processInstanceId", processInstance.getId());
            result.put("processDefinitionId", processInstance.getProcessDefinitionId());
            result.put("businessKey", processInstance.getBusinessKey());

            return Result.success(result);
        } catch (Exception e) {
            return Result.error("启动流程实例失败：" + e.getMessage());
        }
    }

    /**
     * 获取流程实例列表
     */
    @GetMapping("/list")
    public Result<List<Map<String, Object>>> list() {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery()
                .orderByProcessInstanceId().desc()
                .list();

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (ProcessInstance processInstance : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", processInstance.getId());
            map.put("processDefinitionId", processInstance.getProcessDefinitionId());
            map.put("processDefinitionKey", processInstance.getProcessDefinitionKey());
            map.put("processDefinitionName", processInstance.getProcessDefinitionName());
            map.put("businessKey", processInstance.getBusinessKey());
            map.put("startTime", processInstance.getStartTime());
            map.put("suspended", processInstance.isSuspended());
            resultList.add(map);
        }

        return Result.success(resultList);
    }

    /**
     * 删除流程实例
     */
    @DeleteMapping("/{processInstanceId}")
    public Result<Void> delete(@PathVariable String processInstanceId,
                             @RequestParam(required = false) String deleteReason) {
        try {
            runtimeService.deleteProcessInstance(processInstanceId, deleteReason);
            return Result.success();
        } catch (Exception e) {
            return Result.error("删除流程实例失败：" + e.getMessage());
        }
    }

    /**
     * 激活或挂起流程实例
     */
    @PutMapping("/{processInstanceId}/{action}")
    public Result<Void> updateState(@PathVariable String processInstanceId,
                                  @PathVariable String action) {
        try {
            if ("activate".equals(action)) {
                runtimeService.activateProcessInstanceById(processInstanceId);
            } else if ("suspend".equals(action)) {
                runtimeService.suspendProcessInstanceById(processInstanceId);
            } else {
                return Result.error("不支持的操作类型");
            }
            return Result.success();
        } catch (Exception e) {
            return Result.error("操作失败：" + e.getMessage());
        }
    }

    /**
     * 获取流程变量
     */
    @GetMapping("/{processInstanceId}/variables")
    public Result<Map<String, Object>> getVariables(@PathVariable String processInstanceId) {
        try {
            Map<String, Object> variables = runtimeService.getVariables(processInstanceId);
            return Result.success(variables);
        } catch (Exception e) {
            return Result.error("获取流程变量失败：" + e.getMessage());
        }
    }

    /**
     * 设置流程变量
     */
    @PostMapping("/{processInstanceId}/variables")
    public Result<Void> setVariables(@PathVariable String processInstanceId,
                                   @RequestBody Map<String, Object> variables) {
        try {
            runtimeService.setVariables(processInstanceId, variables);
            return Result.success();
        } catch (Exception e) {
            return Result.error("设置流程变量失败：" + e.getMessage());
        }
    }
}
package com.example.workflow.controller;

import com.example.workflow.common.Result;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/process/definition")
public class ProcessDefinitionController {

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 获取流程定义列表
     */
    @GetMapping("/list")
    public Result<List<Map<String, Object>>> list() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()
                .orderByProcessDefinitionVersion().desc()
                .list();

        List<Map<String, Object>> resultList = new ArrayList<>();
        for (ProcessDefinition processDefinition : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", processDefinition.getId());
            map.put("name", processDefinition.getName());
            map.put("key", processDefinition.getKey());
            map.put("version", processDefinition.getVersion());
            map.put("deploymentId", processDefinition.getDeploymentId());
            map.put("suspended", processDefinition.isSuspended());
            resultList.add(map);
        }

        return Result.success(resultList);
    }

    /**
     * 部署流程定义
     */
    @PostMapping("/deploy")
    public Result<Map<String, Object>> deploy(@RequestParam("file") MultipartFile file,
                                            @RequestParam("name") String name) {
        try {
            // 部署流程定义
            org.flowable.engine.repository.Deployment deployment = repositoryService.createDeployment()
                    .addInputStream(file.getOriginalFilename(), file.getInputStream())
                    .name(name)
                    .deploy();

            // 获取流程定义
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployment.getId())
                    .singleResult();

            Map<String, Object> result = new HashMap<>();
            result.put("deploymentId", deployment.getId());
            result.put("processDefinitionId", processDefinition.getId());
            result.put("processDefinitionKey", processDefinition.getKey());
            result.put("processDefinitionVersion", processDefinition.getVersion());

            return Result.success(result);
        } catch (IOException e) {
            return Result.error("部署失败：" + e.getMessage());
        }
    }

    /**
     * 删除流程定义
     */
    @DeleteMapping("/{deploymentId}")
    public Result<Void> delete(@PathVariable String deploymentId,
                             @RequestParam(defaultValue = "false") boolean cascade) {
        try {
            if (cascade) {
                repositoryService.deleteDeployment(deploymentId, true);
            } else {
                repositoryService.deleteDeployment(deploymentId);
            }
            return Result.success();
        } catch (Exception e) {
            return Result.error("删除失败：" + e.getMessage());
        }
    }

    /**
     * 激活或挂起流程定义
     */
    @PutMapping("/{processDefinitionId}/{action}")
    public Result<Void> updateState(@PathVariable String processDefinitionId,
                                  @PathVariable String action) {
        try {
            if ("activate".equals(action)) {
                repositoryService.activateProcessDefinitionById(processDefinitionId);
            } else if ("suspend".equals(action)) {
                repositoryService.suspendProcessDefinitionById(processDefinitionId);
            } else {
                return Result.error("不支持的操作类型");
            }
            return Result.success();
        } catch (Exception e) {
            return Result.error("操作失败：" + e.getMessage());
        }
    }

    /**
     * 获取流程定义XML
     */
    @GetMapping("/xml/{processDefinitionId}")
    public Result<String> getProcessDefinitionXML(@PathVariable String processDefinitionId) {
        try {
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionId(processDefinitionId)
                    .singleResult();

            if (processDefinition == null) {
                return Result.error("流程定义不存在");
            }

            String resourceName = processDefinition.getResourceName();
            org.flowable.engine.repository.Deployment deployment = repositoryService.createDeploymentQuery()
                    .deploymentId(processDefinition.getDeploymentId())
                    .singleResult();

            if (deployment == null) {
                return Result.error("部署信息不存在");
            }

            java.io.InputStream resourceAsStream = repositoryService.getResourceAsStream(
                    deployment.getId(), resourceName);

            byte[] bytes = new byte[resourceAsStream.available()];
            resourceAsStream.read(bytes);
            return Result.success(new String(bytes));
        } catch (Exception e) {
            return Result.error("获取XML失败：" + e.getMessage());
        }
    }
}
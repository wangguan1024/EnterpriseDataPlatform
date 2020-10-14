package com.zyv1.modelmanager.controller;

import com.zyv1.modelmanager.entity.ModelInfo;
import com.zyv1.modelmanager.service.ModelManagerService;
import com.zyv1.modelmanager.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelmanager")
public class ModelManagerController {
    @Autowired
    private ModelManagerService modelManagerService;

    @GetMapping("/")
    public ReturnMessage<List<ModelInfo>> selectAll(){
        return modelManagerService.selectAll();
    }

    @GetMapping("/language/{language}")
    public ReturnMessage<List<ModelInfo>> selectByModelLanguage(@PathVariable("language") String language){
        return modelManagerService.selectByModelLanguage(language);
    }

    @PostMapping("/")
    public ReturnMessage<String> insert(@RequestBody ModelInfo modelInfo){
        return modelManagerService.Insert(modelInfo);
    }

    @PutMapping("/")
    public ReturnMessage<String> update(@RequestBody ModelInfo modelInfo){
        return modelManagerService.update(modelInfo);
    }

    @DeleteMapping("/modelname/{modelname}")
    public ReturnMessage<String> deleteByModelName(@PathVariable("modelname") String modelname){
        return modelManagerService.deleteByModelName(modelname);
    }

    @DeleteMapping("/id/{id}")
    public ReturnMessage<String> deleteById(@PathVariable("id") Integer id){
        return modelManagerService.deleteById(id);
    }
}

package com.zyv1.modelmanager.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyv1.modelmanager.dao.ModelManagerDao;
import com.zyv1.modelmanager.entity.ModelInfo;
import com.zyv1.modelmanager.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ModelManagerService {

    @Autowired
    private ModelManagerDao modelManagerDao;

    public boolean judgeRepeat(String fieldName, String fieldValue) {
        return modelManagerDao.selectCount(new QueryWrapper<ModelInfo>().eq(fieldName, fieldValue)) > 0;
    }

    public ReturnMessage<List<ModelInfo>> selectAll() {
        ReturnMessage<List<ModelInfo>> returnMessage = new ReturnMessage<>();
        List<ModelInfo> modelInfoList = modelManagerDao.selectList(null);

        returnMessage.success(modelInfoList);

        return returnMessage;
    }

    public ReturnMessage<List<ModelInfo>> selectByModelLanguage(String modelLanguage){
        ReturnMessage<List<ModelInfo>> returnMessage = new ReturnMessage<>();
        List<ModelInfo> modelInfoList = modelManagerDao.selectList(new QueryWrapper<ModelInfo>().eq("model_language",modelLanguage));
        if (modelInfoList.isEmpty()) {
            returnMessage.failed("未找到该语言的算法模型");
        } else {
            returnMessage.success(modelInfoList);
        }
        return returnMessage;
    }

    public ReturnMessage<String> Insert(ModelInfo modelInfo) {
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        if (judgeRepeat("model_name", modelInfo.getModelName())) {
            returnMessage.failed("算法模型命名重复");
            return returnMessage;
        }

        if (modelManagerDao.selectCount(new QueryWrapper<ModelInfo>().eq("predict_url", modelInfo.getPredictUrl())) > 0) {
            returnMessage.failed("该模型已被导入");
            return returnMessage;
        }

        if (modelManagerDao.insert(modelInfo)>0){
            returnMessage.success("");
        }else{
            returnMessage.failed("算法模型插入操作出现异常");
        }
        return returnMessage;
    }

    public ReturnMessage<String> deleteByModelName(String modelName){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        if(!judgeRepeat("model_name", modelName)){
            returnMessage.failed("该模型不存在");
            return returnMessage;
        }
        if(modelManagerDao.delete(new QueryWrapper<ModelInfo>().eq("model_name",modelName) )==0){
            returnMessage.failed("删除模型失败");
        }else{
            returnMessage.success("");
        }
        return returnMessage;
    }

    public ReturnMessage<String> deleteById(Integer id){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();
        if(modelManagerDao.delete(new QueryWrapper<ModelInfo>().eq("id",id) )==0){
            returnMessage.failed("删除模型失败");
        }else{
            returnMessage.success("");
        }
        return returnMessage;
    }

    public ReturnMessage<String> update(ModelInfo modelInfo){
        ReturnMessage<String> returnMessage = new ReturnMessage<>();

        if(judgeRepeat("model_name", modelInfo.getModelName())){
            returnMessage.failed  ("数据库名称重复");
            return returnMessage;
        }

        if(modelManagerDao.update(modelInfo, new QueryWrapper<ModelInfo>().eq("id", modelInfo.getId()))>0){
            returnMessage.success("");
        }else{
            returnMessage.failed ("修改数据库信息失败");
        }
        return returnMessage;
    }
}

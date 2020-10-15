package com.zyv1.modelmanager.api;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zyv1.modelmanager.dao.ModelManagerDao;
import com.zyv1.modelmanager.entity.ModelInfo;
import com.zyv1.modelmanager.util.ReturnMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiService {

    @Autowired
    private ModelManagerDao modelManagerDao;

    public ReturnMessage<ModelInfo> selectModelByModelName(String modelName) {
        ReturnMessage<ModelInfo> returnMessage = new ReturnMessage<>();
        ModelInfo modelInfo = modelManagerDao.selectOne(new QueryWrapper<ModelInfo>().eq("model_name", modelName));
        if(modelInfo==null){
            returnMessage.failed("该模型名对应的模型不存在");
            return returnMessage;
        }
        returnMessage.success(modelInfo);
        return returnMessage;
    }
}

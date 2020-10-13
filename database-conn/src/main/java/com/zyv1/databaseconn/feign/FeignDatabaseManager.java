package com.zyv1.databaseconn.feign;

import com.zyv1.databaseconn.bean.Dbinfo;
import com.zyv1.databaseconn.util.ReturnMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "DATABASE-MANAGER", fallback = FeignDatabaseManager.FeignDatabaseManagerFallback.class)
public interface FeignDatabaseManager {
    @GetMapping("/api/dbname/{dbname}")
    public ReturnMessage<Dbinfo> selectByDbname(@PathVariable("dbname") String dbname);

    @Component
    class FeignDatabaseManagerFallback implements FeignDatabaseManager{

        @Override
        public ReturnMessage<Dbinfo> selectByDbname(String dbname) {
            ReturnMessage<Dbinfo> returnMessage = new ReturnMessage<>();
            returnMessage.failed("调用微服务获取数据库信息超时，请重试");
            return returnMessage;
        }
    }
}

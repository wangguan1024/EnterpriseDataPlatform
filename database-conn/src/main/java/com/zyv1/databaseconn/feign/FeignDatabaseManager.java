package com.zyv1.databaseconn.feign;

import com.zyv1.databaseconn.bean.Dbinfo;
import com.zyv1.databaseconn.util.ReturnMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "DATABASE-MANAGER")
public interface FeignDatabaseManager {
    @GetMapping("/api/dbname/{dbname}")
    public ReturnMessage<Dbinfo> selectByDbname(@PathVariable("dbname") String dbname);

}

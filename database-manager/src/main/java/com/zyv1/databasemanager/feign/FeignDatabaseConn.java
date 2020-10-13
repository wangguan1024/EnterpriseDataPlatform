package com.zyv1.databasemanager.feign;



import com.zyv1.databasemanager.entity.Dbinfo;
import com.zyv1.databasemanager.util.ReturnMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "DATABASE-CONN")
public interface FeignDatabaseConn {

    @PostMapping("/api/init")
    public ReturnMessage<String> init(@RequestBody Dbinfo dbinfo);

}

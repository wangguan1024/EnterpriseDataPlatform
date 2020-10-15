package com.zyv1.warnserver.feign;


import com.zyv1.warnserver.entity.WarnInfo;
import com.zyv1.warnserver.util.ReturnMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("DATABASE-CONN")
public interface FeignDatabaseConn {
    @PostMapping("api/itemcount")
    public ReturnMessage<Integer> getItemCount(@RequestBody WarnInfo warnInfo);
}

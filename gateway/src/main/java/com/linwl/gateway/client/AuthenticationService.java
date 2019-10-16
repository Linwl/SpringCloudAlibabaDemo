package com.linwl.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author ：linwl
 * @date ：Created in 2019/10/16 17:14
 * @description ：
 * @modified By：
 */
@FeignClient(value = "authentication-service")
public interface AuthenticationService {
//
//    @GetMapping("/verify/refreshToken")
//    Msg refreshToken(@RequestParam(value = "apiToken") String apiToken);

}

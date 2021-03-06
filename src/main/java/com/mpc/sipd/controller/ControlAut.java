package com.mpc.sipd.controller;

import com.mpc.sipd.utils.MapperJSONUtil;
import lombok.extern.log4j.Log4j2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/sipd-bsb/sipd/api/transaction")

public class ControlAut {

    @Value("${aut.usrtoback}")
    private String username;

    @Value("${aut.passtoback}")
    private String pasword;

    @Value("${url.inquiry}")
    private String urlinq;

    @Value("${url.checkStatus}")
    private String checkStatus;

    @Value("${url.trxHistory}")
    private String trxHistory;

    @Value("${url.overbooking}")
    private String overbooking;

    @Value("${url.callback}")
    private String callback;

    @Value("${aut.id}")
    private String originalInput;

    @Value("${aut.pass}")
    private String originalpass;

    String encodedString = Base64.getEncoder().encodeToString(("aUTh3nt!C@t0r:v3r!f!3dU$er").getBytes());
    String authHeader = "Basic " + new String( encodedString );

    RestTemplate restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors().add((request, body, execution) -> {
        request.getHeaders().add("Authorization",authHeader );
        return execution.execute(request, body);
    })).build();

    @PostMapping(value = "/inquiry")
    public Map<String,Object> getProductList1(@RequestBody Map<String,Object> json) {
        log.debug(MapperJSONUtil.prettyLog(json));
        Map<String,Object> s =restTemplate.postForObject(urlinq,json, Map.class);
        log.error(MapperJSONUtil.prettyLog(s));

        return s;
    }

    @PostMapping(value = "/checkStatus")
    public Map<String,Object> getProductList2(@RequestBody Map<String,Object> json) {
        log.info(MapperJSONUtil.prettyLog(json));
        log.info("this header {}",authHeader);
        Map<String,Object> s =restTemplate.postForObject(checkStatus,json, Map.class);
        log.info(MapperJSONUtil.prettyLog(s));
        return s;
    }

    @PostMapping(value = "/trxHistory")
    public Map<String,Object> getProductList3(@RequestBody Map<String,Object> json) {
        log.info(MapperJSONUtil.prettyLog(json));
        Map<String,Object> s =restTemplate.postForObject(trxHistory,json, Map.class);
        log.info(MapperJSONUtil.prettyLog(s));
        return s;
    }

    @PostMapping(value = "/overbooking")
    public Map<String,Object> getProductList4(@RequestBody Map<String,Object> json) {
        log.info(MapperJSONUtil.prettyLog(json));
        Map<String,Object> s =restTemplate.postForObject(overbooking,json, Map.class);
        log.info(MapperJSONUtil.prettyLog(s));
        return s;
    }
    @PostMapping(value = "/callback")
    public Map<String,Object> getProductList5(@RequestBody Map<String,Object> json, HttpServletResponse response) {
        log.info(MapperJSONUtil.prettyLog(json));
//        Map<String,Object> s =restTemplate.postForObject(callback,json, Map.class);
//        log.info(MapperJSONUtil.prettyLog(s));
        Map<String,Object> s =new HashMap<>();
        s.put("X-Aunthenticate","asdasd");
        String encodeid = Base64.getEncoder().encodeToString(originalInput.getBytes());
        String encodepass = Base64.getEncoder().encodeToString(originalpass.getBytes());
        response.setHeader("X-Authorization", "clientid:"+encodeid+"secretid:"+encodepass);
        log.info(MapperJSONUtil.prettyLog(s));
        return s;
    }

    @GetMapping(value = "/halo")
    public String getProductList5() {
        System.out.println("json");
        return "oke";
    }
}

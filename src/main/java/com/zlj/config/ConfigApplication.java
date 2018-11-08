package com.zlj.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigServer
@RestController
public class ConfigApplication {

    @Value("${zlj.webhook}")
    private String webhook;

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

    @PostMapping("/refresh")
    public void refresh() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = webhook + "/actuator/bus-refresh";
        log.info("url = {}", url);
        HttpPost httpPost = new HttpPost(url);
        try {
            httpclient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

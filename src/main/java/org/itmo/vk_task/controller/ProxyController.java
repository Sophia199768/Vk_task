package org.itmo.vk_task.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.itmo.vk_task.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class ProxyController {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private LogService service;

    private final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    @Cacheable("proxyGetCache")
    @GetMapping("/{path}")
    public ResponseEntity<String> proxyGet(@PathVariable String path, @RequestParam(required = false) String id, HttpServletRequest request) {
        String url = BASE_URL + path;
        if (id != null) {
            url += "/" + id;
        }
        String jwt = request.getHeader("Authorization");
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        service.createLog(jwt, response.getStatusCode().toString());

        return response;
    }

    @Cacheable("proxyPostCache")
    @PostMapping("/{path}")
    public ResponseEntity<String> proxyPost(@PathVariable String path, @RequestBody String body, HttpServletRequest request) {
        String url = BASE_URL + path;
        restTemplate.put(url, body);

        String jwt = request.getHeader("Authorization");
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        service.createLog(jwt, response.getStatusCode().toString());
        return response;
    }
    
    @PutMapping("/{path}/{id}")
    public ResponseEntity<String> proxyPut(@PathVariable String path, @PathVariable String id, @RequestBody String body, HttpServletRequest request) {
        String url = BASE_URL + path + "/" + id;
        restTemplate.put(url, body);

        String jwt = request.getHeader("Authorization");
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        service.createLog(jwt, response.getStatusCode().toString());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{path}/{id}")
    public ResponseEntity<String> proxyDelete(@PathVariable String path, @PathVariable String id, HttpServletRequest request) {
        String url = BASE_URL + path + "/" + id;
        restTemplate.delete(url);

        String jwt = request.getHeader("Authorization");
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        service.createLog(jwt, response.getStatusCode().toString());

        return ResponseEntity.ok().build();
    }
}

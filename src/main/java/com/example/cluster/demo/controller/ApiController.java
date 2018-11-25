package com.example.cluster.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class ApiController {

    private final String instanceName;

    public ApiController(final @Value("${instance.name}") String instanceName) {
        this.instanceName = instanceName;
    }

    @GetMapping(path = "/get-current", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> getCurrentData(final HttpServletRequest request) {
        final Map<String, Object> res = new HashMap<>();
        res.put("instanceName", instanceName);
        final HttpSession session = request.getSession(true);
        res.put("sessionId", session.getId());
        final Object sessionAttr = session.getAttribute("sessionAttr");
        if (sessionAttr == null) {
            final UUID newSessionAttr = UUID.randomUUID();
            session.setAttribute("sessionAttr", newSessionAttr);
            res.put("sessionAttr", newSessionAttr);
        } else {
            res.put("sessionAttr", sessionAttr);
        }
        return res;
    }

    @GetMapping(path = "/new-value", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> getNewValueData(final HttpServletRequest request) {
        final Map<String, Object> res = new HashMap<>();
        res.put("instanceName", instanceName);
        final HttpSession session = request.getSession(true);
        res.put("sessionId", session.getId());
        final UUID sessionAttr = UUID.randomUUID();
        session.setAttribute("sessionAttr", sessionAttr);
        res.put("sessionAttr", sessionAttr);
        return res;
    }

    @GetMapping(path = "/invalidate", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Map<String, Object> getSessionData(final HttpServletRequest request) {
        final Map<String, Object> res = new HashMap<>();
        res.put("instanceName", instanceName);
        final HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return res;
        }
        return res;
    }
}

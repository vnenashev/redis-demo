package com.example.cluster.demo.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    private final String instanceName;

    public DemoController(final @Value("${instance.name}") String instanceName) {
        this.instanceName = instanceName;
    }

    @GetMapping(path = "/")
    public String index(final ModelMap modelMap,
                        final HttpServletRequest request) {
        modelMap.addAttribute("instanceName", instanceName);
        final HttpSession session = request.getSession(true);
        modelMap.addAttribute("sessionId", session.getId());
        if (session.getAttribute("sessionAttr") == null) {
            session.setAttribute("sessionAttr", UUID.randomUUID().toString());
        }
        modelMap.addAttribute("sessionAttr", session.getAttribute("sessionAttr"));
        return "index";
    }

    @GetMapping(path = "/new-value")
    public String newValue(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/";
        }
        session.setAttribute("sessionAttr", UUID.randomUUID().toString());
        return "redirect:/";
    }

    @GetMapping(path = "/invalidate")
    public String invalidate(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return "redirect:/";
        }
        session.invalidate();
        return "redirect:/";
    }
}

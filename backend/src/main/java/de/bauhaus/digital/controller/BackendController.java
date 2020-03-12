package de.bauhaus.digital.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class BackendController {

    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    /*******************************************
     * Login API
     ******************************************/

    @RequestMapping(path = "/login")
    public @ResponseBody
    String sayHello() {
        LOG.info("GET called on /login resource");
        return "You're successfully 'logged' in.";
    }

}

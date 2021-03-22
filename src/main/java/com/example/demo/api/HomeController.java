package com.example.demo.api;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RuntimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class HomeController {

    @GetMapping
    public Dict index() {
        Dict env = Dict.create()
                .set("JAVA_HOME", System.getenv("JAVA_HOME"))
                .set("CATALINA_HOME", System.getenv("CATALINA_HOME"));

        Dict prop = Dict.create()
                .set("catalina.base", System.getProperty("catalina.base"))
                .set("catalina.home", System.getProperty("catalina.home"));

        return Dict.create().set("env", env).set("prop", prop);
    }

    @GetMapping("/sh")
    public List<String> sh() {
        String catalina_base = System.getProperty("catalina.base");
        return RuntimeUtil.execForLines(catalina_base+"/bin/catalina.sh", "version");
    }

    @PostMapping("/upload")
    public Dict upload(MultipartFile file) {
        return Dict.create()
                .set("OriginalFilename", file.getOriginalFilename())
                .set("ContentType", file.getContentType())
                .set("Name", file.getName())
                .set("Size", file.getSize());
    }
}

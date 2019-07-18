package com.tdeado.apidoc.boot;

import com.tdeado.apidoc.Main;
import com.tdeado.apidoc.framework.SpringWebFramework;
import com.tdeado.apidoc.model.ApiDoc;
import com.tdeado.apidoc.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 的Spring Web入口
 *
 * @author leaf
 * @date 2017-03-09 15:36
 */
@RestController
@RequestMapping("apidoc")
public class ApiDocController {


    @Autowired
    private ApiDocProperties apiDocProperties;

    private static ApiDoc apiDoc;

    @PostConstruct
    public void init() {
        if (!apiDocProperties.isEnable()) {
            return;
        }

        String path = apiDocProperties.getSourcePath();

        if (StringUtils.isBlank(path)) {
            path = ".";//默认为当前目录
        }

        List<String> paths = Arrays.asList(path.split(","));

        System.err.println("starting ApiDoc");
        try {
            Main main = new Main(paths, new SpringWebFramework());
            Thread thread = new Thread(() -> {
                try {
                    apiDoc = main.resolve();
                    HashMap<String, Object> properties = new HashMap<>();
                    properties.put("version", apiDocProperties.getVersion());
                    properties.put("title", apiDocProperties.getTitle());
                    apiDoc.setProperties(properties);
                    System.err.println("start up ApiDoc");
                } catch (Exception e) {
                    System.err.println("start up ApiDoc error");
                }
            });
            thread.start();
        } catch (Exception e) {
            System.err.println("start up ApiDoc error");
        }
    }

    /**
     * 获取所有文档api
     *
     * @return 系统所有文档接口的数据(json格式)
     */
    @ResponseBody
    @RequestMapping("apis")
    public Object apis() {
        return JsonUtils.toJson(apiDoc);
    }

    /**
     * 重新构建文档
     *
     * @return 文档页面
     */
    @GetMapping("rebuild")
    public String rebuild() {
        init();
        return "ok";
    }
}

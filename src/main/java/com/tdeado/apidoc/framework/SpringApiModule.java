package com.tdeado.apidoc.framework;

import com.tdeado.apidoc.model.ApiModule;
import lombok.Data;

import java.util.List;

/**
 * Created by leaf on 2017/3/4.
 */
@Data
public class SpringApiModule extends ApiModule {

    /**
     * 业务模块首地址
     */
    private List<String> uris;

    /**
     * 接口限制必须采用访问方式
     */
    private List<String> methods;

    /**
     * 是否返回json
     */
    private boolean json;

}

package com.tdeado.apidoc.tag;

import com.tdeado.apidoc.utils.Constant;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 针对@Resp注释的内容封装
 *
 * Created by leaf on 2017/3/12 0012.
 */
@Data
public class RespTagImpl extends DocTag<String> {

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 参数描述
     */
    private String paramDesc;

    /**
     * 是否必填,默认false
     */
    private boolean require;

    /**
     * 类型
     */
    private String paramType;

    public RespTagImpl(String tagName, String paramName, String paramDesc, String paramType, boolean require) {
        super(tagName);
        this.paramName = paramName;
        this.paramDesc = paramDesc;
        this.paramType = paramType;
        this.require = require;
    }

    @Override
    public String getValues() {
        String s = paramName + " " + paramDesc;
        if (StringUtils.isNotBlank(paramType)) {
            s += " " + paramType;
        }
        s += " " + (require ? Constant.YES_ZH : Constant.NOT_ZH);
        return s;
    }
}

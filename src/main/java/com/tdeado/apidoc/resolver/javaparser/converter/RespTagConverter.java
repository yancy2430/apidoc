package com.tdeado.apidoc.resolver.javaparser.converter;


import com.tdeado.apidoc.tag.DocTag;
import com.tdeado.apidoc.tag.ParamTagImpl;
import com.tdeado.apidoc.tag.RespTagImpl;

/**
 * 针对@resp的转换器
 * @author leaf
 * @date 2017/3/12 0012
 */
public class RespTagConverter extends ParamTagConverter {

    @Override
    public DocTag converter(String comment) {
        ParamTagImpl paramTag = (ParamTagImpl) super.converter(comment);
        RespTagImpl respTag = new RespTagImpl(paramTag.getTagName(), paramTag.getParamName(), paramTag.getParamDesc(),
                paramTag.getParamType(), paramTag.isRequire());
        return respTag;
    }
}

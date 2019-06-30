package com.tdeado.apidoc.resolver.javaparser.converter;


import com.tdeado.apidoc.tag.DocTag;
import com.tdeado.apidoc.tag.DocTagImpl;
import com.tdeado.apidoc.utils.CommentUtils;

/**
 * 基于JavaParser包的默认注释解析转换器
 *
 * @author leaf
 * @date 2017/3/4
 */
public class DefaultJavaParserTagConverterImpl implements JavaParserTagConverter<String> {

    @Override
    public DocTag converter(String comment) {
        String tagType = CommentUtils.getTagType(comment);
        String coment = comment.substring(tagType.length()).trim();
        return new DocTagImpl(tagType, coment);
    }
}

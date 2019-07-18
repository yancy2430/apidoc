package com.tdeado.apidoc.tag;

import com.tdeado.apidoc.model.ObjectInfo;

/**
 * 针对@req注释标签进行封装,返回@req上注释的类信息
 * <p>
 * Created by leaf on 2017/3/4.
 */
public class ReqTagImpl extends DocTag<ObjectInfo> {

    private ObjectInfo objectInfo;

    public ReqTagImpl(String tagName, ObjectInfo objectInfo) {
        super(tagName);
        this.objectInfo = objectInfo;
    }

    @Override
    public ObjectInfo getValues() {
        return objectInfo;
    }
}

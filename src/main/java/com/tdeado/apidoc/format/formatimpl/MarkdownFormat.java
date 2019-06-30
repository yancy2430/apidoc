package com.tdeado.apidoc.format.formatimpl;

import com.tdeado.apidoc.format.Format;
import com.tdeado.apidoc.framework.SpringApiAction;
import com.tdeado.apidoc.model.ApiAction;
import com.tdeado.apidoc.model.ApiDoc;
import com.tdeado.apidoc.model.ApiModule;
import com.tdeado.apidoc.utils.JsonFormatUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by leaf on 2017/3/4.
 */
public class MarkdownFormat implements Format {


    private VelocityTemplater templater = new VelocityTemplater("com/tdeado/apidoc/format/formatimpl/api.vm");

    @Override
    public String format(ApiDoc apiDoc) {
        StringBuilder sb = new StringBuilder();
        for (ApiModule apiModule : apiDoc.getApiModules()) {
            sb.append(format(apiModule)).append("\n\n");
        }
        return sb.toString();
    }

    private String format(ApiModule apiModule) {

        for (ApiAction apiAction : apiModule.getApiActions()) {
            SpringApiAction saa = (SpringApiAction) apiAction;
            if (saa.isJson() && StringUtils.isNotBlank(saa.getRespbody())) {
                saa.setRespbody(JsonFormatUtils.formatJson(saa.getRespbody()));
            }
        }

        try {
            Map<String, Object> map = PropertyUtils.describe(apiModule);
            return templater.parse(map);
        } catch (Exception e) {
            System.err.println("输出markdown文档格式失败");
        }
        return null;
    }
}

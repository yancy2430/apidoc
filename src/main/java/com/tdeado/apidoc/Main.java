package com.tdeado.apidoc;

import com.tdeado.apidoc.format.Format;
import com.tdeado.apidoc.framework.Framework;
import com.tdeado.apidoc.model.ApiDoc;
import com.tdeado.apidoc.model.ApiModule;
import com.tdeado.apidoc.resolver.DocTagResolver;
import com.tdeado.apidoc.resolver.javaparser.JavaParserDocTagResolver;
import com.tdeado.apidoc.utils.FileUtils;
import lombok.Data;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 主入口,核心处理从这里开始
 *
 * @author leaf
 * @date 2017-03-03 16:25
 */
@Data
public class Main {

    private static final String CHARSET = "utf-8";


    /**
     * 源码路径
     */
    private List<String> srcPaths;

    /**
     * api框架类型
     */
    private Framework framework;

    /**
     * 默认的java注释解析器实现
     * <p>
     * 备注:基于sun doc的解析方式已经废弃,若需要请参考v1.0之前的版本
     *
     * @see JavaParserDocTagResolver
     */
    private DocTagResolver docTagResolver = (DocTagResolver) new JavaParserDocTagResolver();

    /**
     * 构建XDoc对象
     *
     * @param srcPath 源码路径
     */
    public Main(String srcPath, Framework framework) {
        this(Arrays.asList(srcPath), framework);
    }

    /**
     * 构建XDoc对象
     *
     * @param srcPaths 源码路径,支持多个
     */
    public Main(List<String> srcPaths, Framework framework) {
        this.srcPaths = srcPaths;
        this.framework = framework;
    }

    /**
     * 解析源码并返回对应的接口数据
     *
     * @return API接口数据
     */
    public ApiDoc resolve() {
        List<String> files = new ArrayList<>();
        for (String srcPath : this.srcPaths) {
            File dir = new File(srcPath);
            System.err.println("解析源码路径:" + dir.getAbsolutePath());
            files.addAll(FileUtils.getAllJavaFiles(dir));
        }

        List<ApiModule> apiModules = this.docTagResolver.resolve(files, framework);

        if (framework != null) {
            apiModules = framework.extend(apiModules);
        }
        return new ApiDoc(apiModules);
    }

    /**
     * 构建接口文档
     *
     * @param out    输出位置
     * @param format 文档格式
     */
    public void build(OutputStream out, Format format) {
        this.build(out, format, null);
    }

    /**
     * 构建接口文档
     *
     * @param out        输出位置
     * @param format     文档格式
     * @param properties 文档属性
     */
    public void build(OutputStream out, Format format, Map<String, Object> properties) {
        ApiDoc apiDoc = this.resolve();
        if (properties != null) {
            apiDoc.getProperties().putAll(properties);
        }

        if (apiDoc.getApiModules() != null && out != null && format != null) {
            String s = format.format(apiDoc);
            try {
                IOUtils.write(s, out, CHARSET);
            } catch (IOException e) {
                System.err.println("接口文档写入文件失败");
            } finally {
                IOUtils.closeQuietly(out);
            }
        }
    }
}

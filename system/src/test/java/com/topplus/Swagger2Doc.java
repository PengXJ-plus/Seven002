package com.topplus;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.Test;

import java.net.URL;
import java.nio.file.Paths;

/**
 * Swagger2Doc
 *
 * @author PengXJ
 * @version 2.0
 * @date 2020/3/17 11:33
 **/
public class Swagger2Doc {
    /**
     * 生成AsciiDocs格式文档
     *
     * @throws Exception
     */
    @Test
    public void generateAsciiDocs() throws Exception {

        //    输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8001/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./docs/asciidoc/generated"));
    }


    /**
     * 生成Markdown格式文档,并汇总成一个文件
     *
     * @throws Exception
     */
    @Test
    public void generateMarkdownDocsToFile() throws Exception {
        //    输出Markdown到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();
        Swagger2MarkupConverter.from(new URL("http://localhost:8001/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFile(Paths.get("./docs/markdown/generated/all"));
    }
}

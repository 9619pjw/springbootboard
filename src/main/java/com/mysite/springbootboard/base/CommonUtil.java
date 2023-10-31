package com.mysite.springbootboard.base;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;
@Component
public class CommonUtil {
    public String markdown(String markdown) {
        Parser parser = Parser.builder().build();
        Node document = parser.parse(markdown);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document);
    }

    // Response Object 생성 Method
    public static ResCommDTO setResponseObject(Object obj, String code, String message) {
		ResCommDTO res = ResCommDTO.builder()
                .code(code)
                .data(obj)
                .message(message)
                .build();

		return res;
	}
}
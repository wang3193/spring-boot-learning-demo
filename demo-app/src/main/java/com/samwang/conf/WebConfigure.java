package com.samwang.conf;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@PropertySource(value = "classpath:web.properties") //该注解只能用于properties文件
@ConfigurationProperties(prefix = "source")
@Data
@ToString
public class WebConfigure {

    private String userName;

    private String password;

    private Map<String, String> map;

    private List<Integer> list;


}

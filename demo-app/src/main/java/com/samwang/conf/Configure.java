package com.samwang.conf;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "web")
@Data
@ToString
public class Configure {

    private String userName;

    private String password;

    private Map<String, String> map;

    private List<Integer> list;


}

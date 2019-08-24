package com.samwang.sys;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
//@Table(name="t_user")
//@EntityListeners(AuditingEntityListener.class) //监听数据库插入和更新操作
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String name;

}

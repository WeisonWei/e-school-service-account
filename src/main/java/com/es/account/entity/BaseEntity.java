package com.es.account.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.messaging.support.MessageBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Slf4j
@ApiModel(description = "Entity的父类")
@MappedSuperclass
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@EntityListeners({AuditingEntityListener.class})
public abstract class BaseEntity implements Serializable {

    public static final String INT_DEFAULT_0 = "int default 0";
    public static final String TINY_INT_DEFAULT_0 = "tinyint default 0";
    public static final String BIGINT_DEFAULT_0 = "bigint default 0";
    public static final String DECIMAL_DEFAULT_0 = "decimal default 0";
    public static final String VARCHAR_DEFAULT_0 = "varchar(255) default ''";
    public static final String VARCHAR_DEFAULT_20 = "varchar(20) default ''";
    public static final String VARCHAR_DEFAULT_50 = "varchar(50) default ''";
    public static final String VARCHAR_DEFAULT_1024 = "varchar(1024) default ''";
    public static final String DATETIME_DEFAULT_0 = "datetime default null";
    public static final String TEXT_DEFAULT = "text";

    @ApiModelProperty("ID")
    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ApiModelProperty("创建人")
    @CreatedBy
    @Column(name = "cid", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long cid;

    @ApiModelProperty("修改人")
    @LastModifiedBy
    @Column(name = "mid", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long mid;

    @ApiModelProperty("创建时间")
    @CreatedDate
    @Column(name = "create_time", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long createTime;

    @ApiModelProperty("修改时间")
    @LastModifiedDate
    @Column(name = "update_time", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long updateTime;

    @Deprecated
    @ApiModelProperty("软删除")
    @Column(name = "del", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long del;

    public BaseEntity delete() {
        setDel(System.currentTimeMillis());
        return this;
    }

    public BaseEntity insert() {
        setCreateTime(System.currentTimeMillis());
        return this;
    }

    public BaseEntity update() {
        setUpdateTime(System.currentTimeMillis());
        return this;
    }

    public boolean send(Source source) {
        String type = this.getClass().getSimpleName();
        log.info("SEND Message: {}", this);
        return source.output().send(MessageBuilder
                .withPayload(this)
                .setHeader("type", type)
                .build());
    }

    public void recv(Logger log) {
        log.info("RECV Message: {}", this);
    }

    @ApiModel(description = "状态")
    public enum STATUS {
        /**
         * 未激活状态
         */
        INACTIVE,
        /**
         * 已激活状态
         */
        ACTIVE;
    }
}

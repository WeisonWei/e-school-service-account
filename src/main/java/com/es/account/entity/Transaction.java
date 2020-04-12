package com.es.account.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.math.BigDecimal;


@ApiModel(description = "用户交易")
@Table(name = "t_transaction", indexes = @Index(name = "idx_userId_accountType", columnList = "user_id,account_type"))
@Entity
@Data
@DynamicInsert(true)
@DynamicUpdate(true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class Transaction extends BaseEntity {

    @ApiModelProperty("用户ID")
    @Column(name = "user_id", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long userId = null;

    @ApiModelProperty("账户类型")
    @Column(name = "account_type", nullable = true, columnDefinition = BaseEntity.INT_DEFAULT_0)
    private Account.AccountType accountType = null;

    @ApiModelProperty("交易类型")
    @Column(name = "trans_type", nullable = true, columnDefinition = BaseEntity.INT_DEFAULT_0)
    private Transaction.TransType transType = null;

    @ApiModelProperty("金额")
    @Column(name = "amount", nullable = true, columnDefinition = BaseEntity.DECIMAL_DEFAULT_0)
    private BigDecimal amount = null;

    @ApiModelProperty("订单id")
    @Column(name = "order_id", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long orderId = null;

    @ApiModelProperty("转账openId")
    @Column(name = "transfer_id", nullable = true, columnDefinition = BaseEntity.VARCHAR_DEFAULT_0)
    private String transferId = null;

    @ApiModelProperty("交易状态")
    @Column(name = "status", nullable = true, columnDefinition = BaseEntity.INT_DEFAULT_0)
    private Transaction.STATUS status = null;

    @ApiModelProperty("交易回执")
    @Column(name = "trans_return", nullable = true, columnDefinition = BaseEntity.VARCHAR_DEFAULT_0)
    private String transReturn = null;

    @ApiModelProperty("描述信息")
    @Column(name = "desc", nullable = true, columnDefinition = BaseEntity.VARCHAR_DEFAULT_0)
    private String desc = null;

    @ApiModelProperty("备注")
    @Column(name = "remark", nullable = true, columnDefinition = BaseEntity.VARCHAR_DEFAULT_0)
    private String remark = null;

    /**
     * 交易类型: 用户发起的交易行为
     */
    public enum TransType {
        /**
         * 默认
         */
        DEFAULT,
        /**
         * 提现
         */
        WITHDRAWAL,
        /**
         * 兑换
         */
        EXCHANGE
    }

    /**
     * 交易状态
     */
    public enum STATUS {
        /**
         * 默认
         */
        DEFAULT,
        /**
         * 提交审核
         */
        COMMIT,
        /**
         * 审核通过
         */
        APPROVAL,
        /**
         * 提现完成
         */
        COMPLETE,
        /**
         * 失败
         */
        FAILED
    }

}

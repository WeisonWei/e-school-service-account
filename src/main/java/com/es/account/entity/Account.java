package com.es.account.entity;

import com.es.base.util.DataUtils;
import com.es.base.util.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;


@ApiModel(description = "用户账户")
@Table(name = "t_account", indexes = @Index(name = "idx_user_id_account_type", columnList = "user_id,account_type"))
@Entity
@Data
@DynamicInsert(true)
@DynamicUpdate(true)
@Accessors(chain = true)
@ToString(callSuper = true)
public class Account extends BaseEntity {

    @ApiModelProperty("用户ID")
    @Column(name = "user_id", nullable = true, columnDefinition = BaseEntity.BIGINT_DEFAULT_0)
    private Long userId = null;

    @ApiModelProperty("账户类型")
    @Column(name = "account_type", nullable = true, columnDefinition = BaseEntity.INT_DEFAULT_0)
    private Account.AccountType accountType = null;

    @ApiModelProperty("余额")
    @Column(name = "balance", nullable = true, columnDefinition = BaseEntity.DECIMAL_DEFAULT_0)
    private Float balance = null;

    @ApiModelProperty("预计收入")
    @Column(name = "expected_amount", nullable = true, columnDefinition = BaseEntity.DECIMAL_DEFAULT_0)
    private Float expectedAmount = null;

    @ApiModelProperty("更新时间 yyyy-MM-dd hh24:mm:ss")
    @Column(name = "update_date", nullable = true, columnDefinition = BaseEntity.VARCHAR_DEFAULT_0)
    private String updateDate = null;

    @ApiModelProperty("备注")
    @Column(name = "remark", nullable = true, columnDefinition = BaseEntity.VARCHAR_DEFAULT_0)
    private String remark = null;

    @ApiModelProperty("乐观锁字段")
    @Column(name = "version", nullable = true, columnDefinition = BIGINT_DEFAULT_0)
    @Version //乐观锁字段
    private Long version = null;

    /**
     * 账户类型
     */
    @ApiModel(description = "账户类型")
    public enum AccountType {
        /**
         * 默认
         */
        DEFAULT,
        /**
         * 现金
         */
        CASH,
        /**
         * 积分
         */
        POINTS
    }

    /**
     * 交易类型
     */
    @ApiModel(description = "交易类型")
    public enum TransType {
        /**
         * 默认
         */
        DEFAULT,
        /**
         * 初始化
         */
        INIT,
        /**
         * 购买
         */
        BUY,
        /**
         * 任务奖励
         */
        TASK,
        /**
         * 红包
         */
        RED_ENVELOPE,
        /**
         * 提现
         */
        WITHDRAWAL,
        /**
         * 兑换
         */
        EXCHANGE
    }


    public Account newAccount(Float amount, Boolean isAdd) {
        Account account = this.newEmptyAccount();
        float cashOrPointsBalance = isAdd ? DataUtils.floatAdd(this.balance, amount) : DataUtils.floatSubtract(this.balance, amount);
        account.setBalance(cashOrPointsBalance);
        return account;
    }

    public Account newEmptyAccount() {
        Account account = new Account();
        account.setAccountType(this.accountType);
        account.setUserId(this.userId);
        account.setUpdateDate(DateUtils.todayStr());
        return account;
    }

    public boolean isBalanceEnough(Float amount) {
        return this.balance >= amount;
    }

}

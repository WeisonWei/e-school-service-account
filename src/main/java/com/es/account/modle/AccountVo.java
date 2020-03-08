package com.es.account.modle;

import com.es.account.entity.Account;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@ApiModel
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class AccountVo implements Serializable {

    @ApiModelProperty("用户ID")
    @Builder.Default
    private Long userId = null;

    @ApiModelProperty("账户类型")
    @Builder.Default
    private Account.AccountType accountType = null;

    @ApiModelProperty("当前余额")
    @Builder.Default
    private Float balance = null;

    @ApiModelProperty("获取红包数")
    @Builder.Default
    private Long totalRedEnvelope = null;

    @ApiModelProperty("备注")
    @Builder.Default
    private String remark = null;
}

package com.es.account.modle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SignStatus implements Serializable {

    @Builder.Default
    private Boolean isSigned = null;

    @Builder.Default
    private Boolean isToday = null;

    @Builder.Default
    private String signDate = null;

    @Builder.Default
    private Float amount = null;

}

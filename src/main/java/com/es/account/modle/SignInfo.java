package com.es.account.modle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SignInfo implements Serializable {

    @Builder.Default
    private Integer continuousSignInNumber = 0;

    @Builder.Default
    private List<SignStatus> signStatuses = new ArrayList<>();

}

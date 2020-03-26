package com.es.account.controller;

import com.es.account.common.CommonTest;
import com.es.account.entity.Account;
import com.es.account.exception.AccountException;
import com.es.account.service.AccountService;
import com.es.base.util.DataUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class AccountControllerTest extends CommonTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Test
    public void getAccount() throws Exception {
        Account account = DataUtils.mockObject(Account.class);
        when(accountService.getAccount(anyLong(), any(Account.AccountType.class))).thenReturn(account);
        mockMvc.perform(get("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", String.valueOf(account.getUserId()))
                .param("accountType", "CASH")
                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.userId").value(account.getUserId()))
                .andDo(document("getAccountUsingGET",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void getAccountError() throws Exception {
        Account account = DataUtils.mockObject(Account.class);
        Long userId = account.getUserId();
        when(accountService.getAccount(anyLong(), any(Account.AccountType.class))).thenThrow(new AccountException(500, "Account Exception!"));
        mockMvc.perform(get("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", String.valueOf(userId))
                .param("accountType", "CASH")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isInternalServerError())
                .andExpect(result -> result.getResolvedException().getClass().equals(AccountException.class))
                .andExpect(result -> result.getResolvedException().getMessage().equals("Account Exception!"));
    }

    @Test
    public void getAccounts() throws Exception {
        Page<Account> accounts = DataUtils.mockPageable(Account.class);
        Long userId = accounts.getContent().get(0).getUserId();
        when(accountService.getAccounts(any(Account.AccountType.class), any(int.class))).thenReturn(accounts);
        mockMvc.perform(get("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", String.valueOf(userId))
                .param("accountType", "CASH")
                .param("page", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.content[0].userId").value(userId))
                .andDo(document("getAccountsUsingGET",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }


    @Test
    public void getRedEnvelope() throws Exception {
        Float aFloat = DataUtils.mockFloat();
        when(accountService.getRedEnvelope(any(Long.class))).thenReturn(aFloat);
        mockMvc.perform(get("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", DataUtils.mockLong().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload").value(aFloat))
                .andDo(document("getRedEnvelopeUsingGET",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }


}
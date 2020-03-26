package com.es.account.controller;

import com.es.account.common.CommonTest;
import com.es.account.entity.Account;
import com.es.account.entity.Transaction;
import com.es.account.service.TransactionService;
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
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class TransactionControllerTest extends CommonTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    public void createTransaction() throws Exception {
        Transaction transaction = DataUtils.mockObject(Transaction.class);
        when(transactionService.createCashTransaction(any(Transaction.class))).thenReturn(transaction);
        mockMvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.userId").value(transaction.getUserId()))
                .andDo(document("cashTransactionUsingPOST",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void updateTransaction() throws Exception {
        Transaction transaction = DataUtils.mockObject(Transaction.class);
        when(transactionService.updateTransaction(any(Transaction.class))).thenReturn(transaction);
        mockMvc.perform(put("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.userId").value(transaction.getUserId()))
                .andDo(document("updateTransactionUsingPUT",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    public void getTransactions() throws Exception {
        Page<Transaction> transactions = DataUtils.mockPageable(Transaction.class);
        when(transactionService.getPagedTransactions(
                any(long.class),
                any(Account.AccountType.class),
                any(Transaction.TransType.class),
                any(Transaction.STATUS.class),
                any(int.class)))
                .thenReturn(transactions);

        mockMvc.perform(get("/v1/transaction/getTransactions")
                .contentType(MediaType.APPLICATION_JSON)
                .param("userId", "1")
                .param("accountType", "CASH")
                .param("transactionType", "WITHDRAWAL")
                .param("status", "APPROVAL")
                .param("page", "1")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.payload.content[0].userId")
                        .value(transactions.getContent().get(0).getUserId()))
                .andDo(document("getTransactionsUsingGET",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
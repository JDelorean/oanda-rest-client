package pl.jdev.opes.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import pl.jdev.opes.helper.EntityProviderHelper;
import pl.jdev.opes_commons.rest.exception.NotFoundException;
import pl.jdev.opes.service.AccountService;
import pl.jdev.opes_commons.domain.account.Account;
import pl.jdev.opes_commons.domain.broker.BrokerName;

import javax.persistence.EntityExistsException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static java.util.Collections.EMPTY_LIST;
import static org.junit.Assert.fail;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static pl.jdev.opes.helper.EntityProviderHelper.account;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//@AutoConfigureJsonTesters
//@Import(EntityProviderHelper.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc opesCore;
    @Autowired
    private JacksonTester<Object> json;
    @Autowired
    private EntityProviderHelper helper;
    @MockBean
    private AccountService accountService;

    //    @Test
    public void getAllAccounts_AccountsExist_ListOfAccounts() {
        List<Account> accounts = helper.accountSet();
        given(this.accountService.getAllAccounts()).willReturn(new HashSet<>(accounts));
        try {
            this.opesCore.perform(get("/accounts"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(this.json.write(accounts).getJson()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void getAllAccounts_NoAccountsExist_ListOfAccounts() {
        List<Account> accounts = EMPTY_LIST;
        given(this.accountService.getAllAccounts()).willReturn(new HashSet<>(accounts));
        try {
            this.opesCore.perform(get("/accounts"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(this.json.write(accounts).getJson()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void getSyncedAccounts_AccountsExist_ListOfSyncedAccounts() {
        List<Account> accounts = helper.accountSet();
        given(this.accountService.getSyncedAccounts()).willReturn(new HashSet<>(accounts));
        try {
            this.opesCore.perform(get("/accounts/synced"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(this.json.write(accounts).getJson()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void getSyncedAccounts_NoAccountsExist_ListOfSyncedAccounts() {
        List<Account> accounts = EMPTY_LIST;
        given(this.accountService.getSyncedAccounts()).willReturn(new HashSet<>(accounts));
        try {
            this.opesCore.perform(get("/accounts/synced"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(this.json.write(accounts).getJson()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void getAccount_ValidId_AccountWithId() {
        Account account = account();
        UUID accountId = account.getId();
        given(this.accountService.getAccount(accountId)).willReturn(account);
        try {
            this.opesCore.perform(get("/accounts/" + accountId))
                    .andExpect(status().isOk())
                    .andExpect(content().json(this.json.write(account).getJson()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void getAccount_InvalidId_ThrowNotFoundException() {
        UUID invalidId = UUID.randomUUID();
        given(this.accountService.getAccount(invalidId)).willThrow(new NotFoundException());
        try {
            this.opesCore.perform(get("/accounts/" + invalidId))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void syncAccount_WhenAccountNotSynced_AccountIsSynced() {
        Account account = account();
        String extId = account.getExtId();
        BrokerName broker = account.getBroker();
        given(this.accountService.syncExtAccount(extId, broker)).willReturn(account);
        try {
            this.opesCore.perform(post("/accounts/sync")
                    .contentType(APPLICATION_JSON)
                    .content(this.json.write(Map.of("extId", extId, "broker", broker)).getJson()))
                    .andExpect(status().isCreated())
                    .andExpect(content().json(this.json.write(account).getJson()));
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void syncAccount_WhenAccountSynced_ThrowBadRequestException() {
        Account account = account();
        String extId = account.getExtId();
        BrokerName broker = account.getBroker();
        given(this.accountService.syncExtAccount(extId, broker)).willThrow(new EntityExistsException());
        try {
            this.opesCore.perform(post("/accounts/sync")
                    .content(this.json.write(Map.of("extId", extId, "broker", broker)).getJson()))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void syncAccount_InvalidExtId_ThrowNotFoundException() {

    }

    //    @Test
    public void syncAccount_NullExtId_ThrowBadRequestException() {
        Account account = account();
        String extId = account.getExtId();
        BrokerName broker = account.getBroker();
        try {
            this.opesCore.perform(post("/accounts/sync")
                    .content(this.json.write(Map.of(null, null, "broker", broker)).getJson()))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void syncAccount_InvalidBroker_ThrowNotFoundException() {
        Account account = account();
        String extId = account.getExtId();
        String invalidBroker = "brok, eh?";
        try {
            this.opesCore.perform(post("/accounts/sync")
                    .content(this.json.write(Map.of("extId", extId, "broker", invalidBroker)).getJson()))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void syncAccount_NullBroker_ThrowBadRequestException() {
        Account account = account();
        String extId = account.getExtId();
        try {
            this.opesCore.perform(post("/accounts/sync")
                    .content(this.json.write(Map.of("extId", extId, null, null)).getJson()))
                    .andExpect(status().isBadRequest());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void unsyncAccount_WhenAccountSynced_AccountIsUnsynced() {
        Account account = account();
        UUID accountId = account.getId();
        try {
            this.opesCore.perform(patch("/accounts/" + accountId + "/unsync"))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void unsyncAccount_InvalidId_ThrowNotFoundException() {
        Account account = account();
        UUID invalidId = account.getId();
        given(this.accountService.unsyncAccount(invalidId)).willThrow(new NotFoundException());
        try {
            this.opesCore.perform(patch("/accounts/" + invalidId + "/unsync"))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void deleteAccount_AccountIsActive_AccountIsDeleted() {
        Account account = account();
        UUID accountId = account.getId();
        try {
            this.opesCore.perform(delete("/accounts/" + accountId))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void deleteAccount_InvalidId_ThrowNotFoundException() {
        Account account = account();
        UUID accountId = account.getId();
//        given(this.accountService.deleteAccount(accountId)).willThrow(new NotFoundException());
        try {
            this.opesCore.perform(delete("/accounts/" + accountId))
                    .andExpect(status().isNotFound());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void tagAccount_AccountNotTagged_AccountIsTagged() {
        Account account = account();
        UUID accountId = account.getId();
        try {
            this.opesCore.perform(patch("/accounts/" + accountId + "/tag"))
                    .andExpect(status().isNoContent());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    //    @Test
    public void tagAccount_InvalidId_ThrowNotFoundException() {

    }

    //    @Test
    public void untagAccount_AccountIsTagged_AccountIsNotTagged() {

    }

    //    @Test
    public void untagAccount_InvalidId_ThrowNotFoundException() {

    }
}

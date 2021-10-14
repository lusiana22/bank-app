package com.bank.bankapplication.service;

import com.bank.bankapplication.data.AccountType;
import com.bank.bankapplication.data.TransferMoneyRequest;
import com.bank.bankapplication.exception.NotFoundException;
import com.bank.bankapplication.persistence.entity.BankAccount;
import com.bank.bankapplication.persistence.entity.Customer;
import com.bank.bankapplication.persistence.entity.TransferHistory;
import com.bank.bankapplication.persistence.repository.BankAccountRepository;
import com.bank.bankapplication.persistence.repository.TransferHistoryRepository;
import com.bank.bankapplication.service.impl.BankAccountServiceImpl;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class BankAccountServiceTest {
    private static final String CUSTOMER_ID = "23232321";
    private static final String CUSTOMER_ID_2 = "434343443";
    private static final String FIRST_NAME = "Lusiana";
    private static final String LAST_NAME = "Velcani";
    private static final Customer CUSTOMER = Customer.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();
    private static final Customer CUSTOMER_2 = Customer.builder()
            .firstName(FIRST_NAME)
            .lastName(LAST_NAME)
            .build();
    private static final String ACCOUNT_NUMBER = "6330546547";
    private static final String FROM_ACCOUNT_NUMBER = "45675434";
    private static final String TO_ACCOUNT_NUMBER = "4546464464";
    private static final AccountType ACCOUNT_TYPE = AccountType.CHECKING;
    private static final BigDecimal INITIAL_BALANCE = BigDecimal.TEN;
    private static final BankAccount BANK_ACCOUNT = BankAccount.builder()
            .accountNumber(ACCOUNT_NUMBER)
            .accountType(ACCOUNT_TYPE)
            .balance(INITIAL_BALANCE)
            .customer(CUSTOMER)
            .build();
    private static final BankAccount FROM_BANK_ACCOUNT = BankAccount.builder()
            .accountNumber(FROM_ACCOUNT_NUMBER)
            .accountType(ACCOUNT_TYPE)
            .balance(INITIAL_BALANCE)
            .customer(CUSTOMER)
            .build();
    private static final BankAccount TO_BANK_ACCOUNT = BankAccount.builder()
            .accountNumber(TO_ACCOUNT_NUMBER)
            .accountType(ACCOUNT_TYPE)
            .balance(INITIAL_BALANCE)
            .customer(CUSTOMER_2)
            .build();
    private static final BigDecimal AMOUNT = BigDecimal.TEN;
    private static final TransferHistory TRANSFER_HISTORY = TransferHistory.builder()
            .fromAccountNumber(FROM_ACCOUNT_NUMBER)
            .toAccountNumber(TO_ACCOUNT_NUMBER)
            .amount(AMOUNT)
            .build();
    private static final TransferMoneyRequest TRANSFER_MONEY_REQUEST = TransferMoneyRequest.builder()
            .fromAccountNumber(FROM_ACCOUNT_NUMBER)
            .toAccountNumber(TO_ACCOUNT_NUMBER)
            .amount(AMOUNT)
            .build();

    private BankAccountRepository bankAccountRepository;
    private TransferHistoryRepository transferHistoryRepository;

    @RegisterExtension
    Mockery context = new JUnit5Mockery();

    @BeforeEach
    public void setUp() {
        bankAccountRepository = context.mock(BankAccountRepository.class);
        transferHistoryRepository = context.mock(TransferHistoryRepository.class);
    }

    @Test
    public void getBalance() {
       context.checking(new Expectations() {
           {
               oneOf(bankAccountRepository).findByAccountNumber(ACCOUNT_NUMBER);
               will(returnValue(Optional.of(BANK_ACCOUNT)));
           }
       });
       BankAccountService service = new BankAccountServiceImpl(bankAccountRepository, transferHistoryRepository);
       BigDecimal returnedBalance = service.getBalance(ACCOUNT_NUMBER);
       assertThat(returnedBalance, is(equalTo(BANK_ACCOUNT.getBalance())));
    }

    @Test
    public void getBalanceCustomerNotFound() {
        context.checking(new Expectations() {
            {
                oneOf(bankAccountRepository).findByAccountNumber(ACCOUNT_NUMBER);
                will(returnValue(Optional.empty()));
            }
        });
        BankAccountService service = new BankAccountServiceImpl(bankAccountRepository, transferHistoryRepository);
        Assertions.assertThrows(NotFoundException.class, () -> service.getBalance(ACCOUNT_NUMBER));
    }

    @Test
    public void transferBetweenAccounts() {
        context.checking(new Expectations() {
            {
                oneOf(bankAccountRepository).findByAccountNumber(FROM_ACCOUNT_NUMBER);
                will(returnValue(Optional.of(FROM_BANK_ACCOUNT)));
                oneOf(bankAccountRepository).findByAccountNumber(TO_ACCOUNT_NUMBER);
                will(returnValue(Optional.of(TO_BANK_ACCOUNT)));
                oneOf(bankAccountRepository).save(FROM_BANK_ACCOUNT);
                oneOf(bankAccountRepository).save(TO_BANK_ACCOUNT);
                oneOf(transferHistoryRepository).save(TRANSFER_HISTORY);
            }
        });
        BankAccountService service = new BankAccountServiceImpl(bankAccountRepository, transferHistoryRepository);
        service.transferBetweenAccounts(TRANSFER_MONEY_REQUEST);
    }

    @Test
    public void transferBetweenAccountsFromAccountNotFound() {
        context.checking(new Expectations() {
            {
                oneOf(bankAccountRepository).findByAccountNumber(FROM_ACCOUNT_NUMBER);
                will(returnValue(Optional.empty()));
            }
        });
        BankAccountService service = new BankAccountServiceImpl(bankAccountRepository, transferHistoryRepository);
        Assertions.assertThrows(NotFoundException.class, () -> service.transferBetweenAccounts(TRANSFER_MONEY_REQUEST));
    }

    @Test
    public void transferBetweenAccountsToAccountNotFound() {
        context.checking(new Expectations() {
            {
                oneOf(bankAccountRepository).findByAccountNumber(FROM_ACCOUNT_NUMBER);
                will(returnValue(Optional.of(FROM_BANK_ACCOUNT)));
                oneOf(bankAccountRepository).findByAccountNumber(TO_ACCOUNT_NUMBER);
                will(returnValue(Optional.empty()));
            }
        });
        BankAccountService service = new BankAccountServiceImpl(bankAccountRepository, transferHistoryRepository);
        Assertions.assertThrows(NotFoundException.class, () -> service.transferBetweenAccounts(TRANSFER_MONEY_REQUEST));
    }
}
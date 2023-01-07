package com.mungwin.payngwinteller.service;

import com.mungwin.payngwinteller.constant.TransactionStatuses;
import com.mungwin.payngwinteller.domain.model.account.Account;
import com.mungwin.payngwinteller.domain.model.iam.App;
import com.mungwin.payngwinteller.domain.model.payment.CollectionOrder;
import com.mungwin.payngwinteller.domain.repository.account.AccountRepository;
import com.mungwin.payngwinteller.domain.repository.payment.CollectionOrderRepository;
import com.mungwin.payngwinteller.domain.request.payment.CollectionPayRequest;
import com.mungwin.payngwinteller.domain.response.payment.InitCollectionResponse;
import com.mungwin.payngwinteller.exception.ApiException;
import com.mungwin.payngwinteller.exception.Precondition;
import com.mungwin.payngwinteller.network.payment.PayProviderFactory;
import com.mungwin.payngwinteller.security.service.AppSecurityContextHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CollectionService {
    @Value("${ui.host}")
    private String UI_HOST;
    private final AccountRepository accountRepository;
    private final CollectionOrderRepository collectionOrderRepository;
    private final PayProviderFactory payProviderFactory;

    public CollectionService(AccountRepository accountRepository,
                             CollectionOrderRepository collectionOrderRepository,
                             PayProviderFactory payProviderFactory) {
        this.accountRepository = accountRepository;
        this.collectionOrderRepository = collectionOrderRepository;
        this.payProviderFactory = payProviderFactory;
    }

    public InitCollectionResponse initCollection(
            App app, Double amount, String currency, String externalId, String returnUrl, String returnToken,
            String cancelUrl, String orderLogoUrl, String lineItems, String comment, String source,
            String tag, Boolean captureMode) {
        Precondition.check(
                Arrays.stream((new Object[]{app, amount, currency, externalId, returnUrl, returnToken, cancelUrl, comment}))
                        .noneMatch(Objects::isNull), ApiException.BAD_REQUEST_INPUTS);
        Optional<Account> accountOptional = accountRepository.findById(app.getAccountId());
        Precondition.check(accountOptional.isPresent(), ApiException.RESOURCE_NOT_FOUND);
        Precondition.check(
                !collectionOrderRepository.findFirstByExternalIdAndAccountIdAndTag(
                        externalId, app.getAccountId(), tag).isPresent(), ApiException.EXTERNAL_ID_NOT_UNIQUE);
        CollectionOrder order = new CollectionOrder();
        order.setAppId(app.getId());
        order.setAccountId(app.getAccountId());
        order.setAmount(amount);
        order.setCurrency(currency);
        order.setStatus(TransactionStatuses.INITIALIZED.toString());
        String token = UUID.randomUUID().toString().replace("-", "");
        order.setToken(token);
        order.setExternalId(externalId);
        order.setReturnToken(returnToken);
        order.setReturnUrl(returnUrl);
        order.setCancelUrl(cancelUrl);
        order.setOComment(comment);
        order.setOSource(source);
        order.setLineItems(lineItems);
        order.setOrderLogoUrl(orderLogoUrl);
        order.setCaptureMode(captureMode);
        order.setTag(tag);
        collectionOrderRepository.save(order);
        return new InitCollectionResponse(token, order.getCreatedAt(), order.getStatus(), UI_HOST);
    }

    public Boolean pushRequestToPay(CollectionPayRequest payRequest) {
        return payProviderFactory.getPayProvider(payRequest.getChannel()).push(
                payRequest.getOrderToken(),
                payRequest.getPayerId(), payRequest.getReturnEmail(), payRequest.getPayerExpireDate(),
                payRequest.getPayerCode(), payRequest.getComment()
        );
    }
}

package com.mungwin.payngwinteller.network.payment.providers.mtn;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mungwin.payngwinteller.domain.model.payment.CollectionOrder;
import com.mungwin.payngwinteller.exception.ApiException;
import com.mungwin.payngwinteller.exception.Precondition;
import com.mungwin.payngwinteller.network.payment.PayProvider;
import com.mungwin.payngwinteller.network.payment.clients.mtn.MoMoRestClient;
import com.mungwin.payngwinteller.network.payment.dto.mtn.MoMoPayRequest;
import com.mungwin.payngwinteller.network.payment.dto.mtn.MoMoPayer;
import com.mungwin.payngwinteller.network.payment.providers.BasePayProvider;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MtnPayProvider extends BasePayProvider implements PayProvider {
    private final MoMoRestClient moMoRestClient;

    public MtnPayProvider(MoMoRestClient moMoRestClient) {
        this.moMoRestClient = moMoRestClient;
    }

    @Override
    public boolean push(
            String orderToken, String payerId,
            String returnEmail, String payerExpireDate,
            String payerCode, String comment) {
        Optional<CollectionOrder> orderOptional = collectionOrderRepository.findFirstByToken(orderToken);
        Precondition.check(orderOptional.isPresent(), ApiException.RESOURCE_NOT_FOUND);
        CollectionOrder order = orderOptional.get();
        String referenceId = UUID.randomUUID().toString();
        String accessToken = moMoRestClient.login().getAccess_token();
        MoMoPayRequest moMoPayRequest = new MoMoPayRequest();
        moMoPayRequest.setAmount(String.valueOf(order.getAmount()));
        moMoPayRequest.setCurrency(order.getCurrency());
        moMoPayRequest.setExternalId(order.getExternalId());
        MoMoPayer payer = new MoMoPayer();
        payer.setPartyId(payerId);
        payer.setPartyIdType("MSISDN");
        moMoPayRequest.setPayer(payer);
        moMoPayRequest.setPayerMessage(order.getOComment());
        moMoPayRequest.setPayeeNote(comment);
        order.setProviderToken(referenceId);
        order.setNotifyEmail(returnEmail);
        order.setNotifyPhoneNumber(payerId);
        collectionOrderRepository.save(order);
        boolean pushed = moMoRestClient.requestToPay(moMoPayRequest, referenceId, accessToken);
        // start a thread to monitor this payment
        return pushed;
    }

    @Override
    public void capture() {

    }
}

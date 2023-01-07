package com.mungwin.payngwinteller.controller.console;

import com.mungwin.payngwinteller.domain.request.payment.CollectionPayRequest;
import com.mungwin.payngwinteller.domain.response.ApiResponse;
import com.mungwin.payngwinteller.network.payment.PayProviderFactory;
import com.mungwin.payngwinteller.network.payment.clients.mtn.MoMoRestClient;
import com.mungwin.payngwinteller.network.payment.dto.mtn.MoMoPayRequest;
import com.mungwin.payngwinteller.network.payment.dto.mtn.MoMoPayer;
import com.mungwin.payngwinteller.network.payment.dto.mtn.MoMoTokenDTO;
import com.mungwin.payngwinteller.security.service.AppSecurityContextHolder;
import com.mungwin.payngwinteller.security.service.aspects.ConsoleAuthHandler;
import com.mungwin.payngwinteller.security.service.aspects.PreApprove;
import com.mungwin.payngwinteller.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("${api.base}/public/console")
public class ConsoleController {

    private MoMoRestClient moMoRestClient;
    private final CollectionService collectionService;

    public ConsoleController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Autowired
    public void setMoMoRestClient(MoMoRestClient moMoRestClient) {
        this.moMoRestClient = moMoRestClient;
    }

    @GetMapping("/test/momo")
    @PreApprove(handler = ConsoleAuthHandler.class)
    public ResponseEntity<ApiResponse<MoMoTokenDTO>> testMomoLogin() {
        return ResponseEntity.ok(ApiResponse.from(moMoRestClient.login()));
    }
    @PostMapping("/request_to_pay")
    @PreApprove(handler = ConsoleAuthHandler.class)
    public ResponseEntity<Boolean> pushRequestToPay(@RequestBody @Valid CollectionPayRequest payRequest) {
        return ResponseEntity.ok(collectionService.pushRequestToPay(payRequest));
    }
    @GetMapping("/momo/requesttopay")
    public ResponseEntity<Boolean> testRequestToPay() {
        MoMoPayRequest moMoPayRequest = new MoMoPayRequest();
        moMoPayRequest.setAmount("10");
        moMoPayRequest.setCurrency("XAF");
        moMoPayRequest.setExternalId("12345");
        MoMoPayer payer = new MoMoPayer();
        payer.setPartyId("237653954068");
        payer.setPartyIdType("MSISDN");
        moMoPayRequest.setPayer(payer);
        moMoPayRequest.setPayerMessage("Collection for CNPS");
        moMoPayRequest.setPayeeNote("Collection for CNPS");
        String referenceId = UUID.randomUUID().toString();
        return ResponseEntity.ok(moMoRestClient.requestToPay(
                moMoPayRequest, referenceId, moMoRestClient.login().getAccess_token()));
    }
}

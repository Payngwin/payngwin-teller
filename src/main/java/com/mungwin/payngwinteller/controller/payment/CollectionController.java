package com.mungwin.payngwinteller.controller.payment;

import com.mungwin.payngwinteller.domain.request.payment.InitCollectionRequest;
import com.mungwin.payngwinteller.domain.response.ApiResponse;
import com.mungwin.payngwinteller.domain.response.payment.InitCollectionResponse;
import com.mungwin.payngwinteller.security.logs.aspects.LogActivity;
import com.mungwin.payngwinteller.security.service.AppSecurityContextHolder;
import com.mungwin.payngwinteller.service.CollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("${api.base}/collection")
public class CollectionController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping("/init")
    @LogActivity("payment.order.create")
    public ResponseEntity<ApiResponse<InitCollectionResponse>> initCollection(@RequestBody @Valid InitCollectionRequest body){
        return ResponseEntity.ok(ApiResponse.from(collectionService.initCollection(
                AppSecurityContextHolder.getPrincipal().getUser(), body.getAmount(), body.getCurrency(),
                body.getExternalId(), body.getReturnUrl(), body.getReturnToken(), body.getCancelUrl(),
                body.getOrderLogoUrl(), body.lineItemsToString(), body.getComment(), body.getSource(), body.getTag(),
                body.getCaptureMode())));
    }
}

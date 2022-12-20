package com.mungwin.payngwinteller.controller.console;

import com.mungwin.payngwinteller.domain.response.ApiResponse;
import com.mungwin.payngwinteller.network.payment.clients.mtn.MoMoRestClient;
import com.mungwin.payngwinteller.network.payment.dto.mtn.MoMoTokenDTO;
import com.mungwin.payngwinteller.security.service.aspects.ConsoleAuthHandler;
import com.mungwin.payngwinteller.security.service.aspects.PreApprove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.base}/public/console")
public class ConsoleController {

    private MoMoRestClient moMoRestClient;
    @Autowired
    public void setMoMoRestClient(MoMoRestClient moMoRestClient) {
        this.moMoRestClient = moMoRestClient;
    }

    @GetMapping("/test/momo")
    @PreApprove(handler = ConsoleAuthHandler.class)
    public ResponseEntity<ApiResponse<MoMoTokenDTO>> testMomoLogin() {
        return ResponseEntity.ok(ApiResponse.from(moMoRestClient.login()));
    }
}

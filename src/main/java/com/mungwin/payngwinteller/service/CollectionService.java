package com.mungwin.payngwinteller.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {
    @Value("${ui.host}")
    private String UI_HOST;
}

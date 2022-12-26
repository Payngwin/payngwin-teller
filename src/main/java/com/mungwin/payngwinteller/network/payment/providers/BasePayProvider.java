package com.mungwin.payngwinteller.network.payment.providers;

import com.mungwin.payngwinteller.domain.repository.payment.CollectionOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BasePayProvider {
    protected CollectionOrderRepository collectionOrderRepository;

    @Autowired
    public void setCollectionOrderRepository(CollectionOrderRepository collectionOrderRepository) {
        this.collectionOrderRepository = collectionOrderRepository;
    }
}

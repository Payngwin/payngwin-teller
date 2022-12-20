package com.mungwin.payngwinteller.domain.model.mail;

import com.mungwin.payngwinteller.domain.model.account.BillingAddress;

import java.util.ArrayList;
import java.util.List;

public class AddressBean {
    private List<EmailAddress> ccAddresses;
    private List<EmailAddress> bccAddresses;

    public AddressBean() {
        this.ccAddresses = new ArrayList<>();
        this.bccAddresses = new ArrayList<>();

    }

    public List<EmailAddress> getCcAddresses() {
        return ccAddresses;
    }

    public void setCcAddresses(List<EmailAddress> ccAddresses) {
        this.ccAddresses = ccAddresses;
    }

    public List<EmailAddress> getBccAddresses() {
        return bccAddresses;
    }

    public void setBccAddresses(List<EmailAddress> bccAddresses) {
        this.bccAddresses = bccAddresses;
    }

    public static AddressBean processBillingAddresses(List<BillingAddress> billingAddresses) {
        AddressBean addressBean = new AddressBean();
        for (BillingAddress billingAddress: billingAddresses) {
            EmailAddress emailAddress = new EmailAddress(billingAddress.getName(), billingAddress.getEmail());
            if (billingAddress.getScope().equals("cc")) {
                addressBean.getCcAddresses().add(emailAddress);
            } else  {
                addressBean.getBccAddresses().add(emailAddress);
            }
        }
        return addressBean;
    }
}

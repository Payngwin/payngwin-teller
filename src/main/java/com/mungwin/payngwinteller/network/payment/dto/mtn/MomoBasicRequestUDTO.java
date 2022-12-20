package com.mungwin.payngwinteller.network.payment.dto.mtn;

public class MomoBasicRequestUDTO {
    private Long _amount;
    private String _tel;
    private String comment;

    public Long get_amount() {
        return _amount;
    }

    public void set_amount(Long _amount) {
        this._amount = _amount;
    }

    public String get_tel() {
        return _tel;
    }

    public void set_tel(String _tel) {
        this._tel = _tel;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

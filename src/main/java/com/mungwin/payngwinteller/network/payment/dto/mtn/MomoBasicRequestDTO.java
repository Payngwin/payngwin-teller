package com.mungwin.payngwinteller.network.payment.dto.mtn;

import java.util.Objects;

public class MomoBasicRequestDTO {
            /*'idbouton' => 2,
            'typebouton' => 'PAIE',
            '_amount' => $query['amount'],
            '_tel' => $query['phone'],
            '_clP' => '',
            '_email' => $this->host_email,*/
            private int idbouton;
            private String typebouton;
            private Long _amount;
            private String _tel;
            private String _clp;

    public MomoBasicRequestDTO() {
        this.idbouton = 2;
        this.typebouton = "PAIE";
        this._clp = "";
    }

    public MomoBasicRequestDTO(Long _amount, String _tel) {
        this();
        this._amount = _amount;
        this._tel = _tel;
    }

    public int getIdbouton() {
        return idbouton;
    }

    public void setIdbouton(int idbouton) {
        this.idbouton = idbouton;
    }

    public String getTypebouton() {
        return typebouton;
    }

    public void setTypebouton(String typebouton) {
        this.typebouton = typebouton;
    }

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

    public String get_clp() {
        return _clp;
    }

    public void set_clp(String _clp) {
        this._clp = _clp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MomoBasicRequestDTO)) return false;
        MomoBasicRequestDTO that = (MomoBasicRequestDTO) o;
        return idbouton == that.idbouton &&
                Objects.equals(typebouton, that.typebouton) &&
                Objects.equals(_clp, that._clp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idbouton, typebouton, _clp);
    }

    @Override
    public String toString() {
        return "MomoBasicRequestDTO{" +
                "idbouton=" + idbouton +
                ", typebouton='" + typebouton + '\'' +
                ", _amount=" + _amount +
                ", _tel='" + _tel + '\'' +
                ", _clp='" + _clp + '\'' +
                '}';
    }
}

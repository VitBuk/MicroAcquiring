package lv.lpb.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "MERCHANT_AGREEMENT")
//@NamedQueries({
//    @NamedQuery(name = "MerchantAgreement.findAll", query = "SELECT m FROM MerchantAgreement m")
//})
public class MerchantAgreement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 3)
    private Currency currency;

    public MerchantAgreement() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "MerchantAgreement{" + "id=" + id + ", currency=" + currency + '}';
    }
}

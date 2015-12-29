package lv.lpb.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import lv.lpb.Constants;

@Entity
@Table(name = "MERCHANT")
@NamedQueries({
    @NamedQuery(name = "Merchant.findAll", query = "SELECT m FROM Merchant m")
})
public class Merchant implements Serializable {

    private static final long serialVersionUID = 0L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH}, targetEntity = MerchantAgreement.class)
    @JoinColumn(name = "MERCHANT_AGREEMENT_ID")
    private Set<MerchantAgreement> merchantAgreements = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(length = Constants.MERCH_STATUS_MAX_LENGTH)
    private Status status;

    @Column
    private LocalDateTime created;

    @Version
    private int version;

    public Merchant() {
    }

    public Merchant(Long id) {
        this.id = id;
        MerchantAgreement merchantAgreement1 = new MerchantAgreement();
        merchantAgreement1.setCurrency(Currency.EUR);
        MerchantAgreement merchantAgreement2 = new MerchantAgreement();
        merchantAgreement2.setCurrency(Currency.USD);
    }

    public void add(Currency currency) {
        MerchantAgreement merchantAgreement = new MerchantAgreement();
        merchantAgreement.setCurrency(currency);
        merchantAgreements.add(merchantAgreement);
    }

    public Long getId() {
        return id;
    }

    public Set<MerchantAgreement> getMerchantAgreements() {
        return merchantAgreements;
    }

    public boolean allowedCurrency(Currency currency) {
        MerchantAgreement merchantAgreement = new MerchantAgreement();
        merchantAgreement.setCurrency(currency);
        return merchantAgreements.contains(merchantAgreement);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Merchant{" + "id=" + id + ", merchantAgreements=" + getMerchantAgreements() + ", status=" + status + ", created=" + created + ", version=" + version + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Merchant other = (Merchant) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public static enum Status{

        ACTIVE,
        INACTIVE;
    }
}

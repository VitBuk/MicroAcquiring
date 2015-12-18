package lv.lpb.beans;

import java.util.List;
import lv.lpb.domain.Merchant;

public interface MerchantBean extends Bean<Merchant> {

    @Override
    public void persist(Merchant merchant);

    @Override
    public Merchant find(Long id);

    @Override
    public void update(Merchant merchant);

    @Override
    public void delete(Long id);

    @Override
    public List<Merchant> getAll();

}

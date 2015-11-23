/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.lpb.database;

import java.util.ArrayList;
import java.util.List;
import lv.lpb.domain.Currency;
import lv.lpb.domain.Merchant;

/**
 *
 * @author VitBuk
 */
public class MerchantCollectionDAO implements DAO<Merchant> {

    private List<Merchant> merchants = new ArrayList<>();    
    
    private static final MerchantCollectionDAO merchantDAO = new MerchantCollectionDAO();
    
    static {
        //init test merchants
        Merchant merchant1 = new Merchant(1L);
        merchant1.add(Currency.JPY);
        merchant1.setStatus(Merchant.Status.ACTIVE);
        merchantDAO.create(merchant1);
        Merchant merchant2 = new Merchant(2L);
        merchant2.add(Currency.RUB);
        merchant2.setStatus(Merchant.Status.ACTIVE);
        merchantDAO.create(merchant2);
        Merchant merchant3 = new Merchant(3L);
        merchantDAO.create(merchant3);
        merchant3.add(Currency.GBP);
        merchant3.add(Currency.RUB); 
        merchant3.setStatus(Merchant.Status.ACTIVE);
    }
    
    public MerchantCollectionDAO getInstance() {
        return merchantDAO;
    }
    
    @Override
    public Merchant create(Merchant t) {
        merchants.add(t);
        return t;
    }

    @Override
    public Merchant update(Merchant t) {
        //Merchant merchantToUpdate = get(t.getId());
        //and update
        return null;
    }

    @Override
    public Merchant get(Long id) {
        
        //get by id 
        
        return null;
    }
    
    
    //additionalk merchant DAO methods
    
}

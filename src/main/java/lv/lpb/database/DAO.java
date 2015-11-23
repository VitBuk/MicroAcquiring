/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lv.lpb.database;

/**
 *
 * @author VitBuk
 */
public interface DAO<T> {
    public T create(T t);
    public T update(T t);    
    public T get(Long id);
}

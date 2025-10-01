package com.user.gestaoaluguel.service;

import com.user.gestaoaluguel.dao.MoradorDao;
import com.user.gestaoaluguel.model.Morador;

import java.time.LocalDate.Time;
import java.util.List;


public class MoradorService {
    private final MoradorDao dao = new MoradorDao();
    public List<Morador> activeToList(){return dao.activeToList};
    public Morador searchForId(int id) {return dao.searchForId};
    public int insert (Morador m) {return dao.insert};
    public void update (Morador m) {dao.update(m)};
    public void remove (int id, boolean softDelete){
        if (softDelete) dao.inactive(id);
        else dao.removePhysical
    }
    public void setPayed(int id, boolean payStatus){
        dao.payCheck(id, payStatus, payStatus ? LocalDateTime.now() : null);
    }
}

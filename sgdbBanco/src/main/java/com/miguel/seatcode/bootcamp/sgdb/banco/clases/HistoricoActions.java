package com.miguel.seatcode.bootcamp.sgdb.banco.clases;

public interface HistoricoActions {
    public String crearHistorico(String movimiento, float balance, Long fk_id_cuenta);
}

package com.miguel.seatcode.bootcamp.sgdb.banco.clases;

public class Historico implements HistoricoActions{
    private Long id;
    private String movimiento;
    private float balance;
    private String fecha;
    private Long FK_id_cuenta;

    public Historico() {
    }

    @Override
    public String crearHistorico(String movimiento, float balance, Long fk_id_cuenta){
        this.setMovimiento(movimiento);
        this.setBalance(balance);
        this.setFK_id_cuenta(fk_id_cuenta);

        String sql=null;

        sql = "insert into historicos values (default," +
                "\'"+this.getMovimiento()+"\', " +
                "\'"+this.getBalance()+"\', " +
                "CURRENT_TIMESTAMP," +
                "\'"+this.getFK_id_cuenta()+"\')";

        return sql;    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Long getFK_id_cuenta() {
        return FK_id_cuenta;
    }

    public void setFK_id_cuenta(Long FK_id_cuenta) {
        this.FK_id_cuenta = FK_id_cuenta;
    }

    @Override
    public String toString() {
        return "Historico{" +
                "id=" + id +
                ", movimiento='" + movimiento + '\'' +
                ", balance=" + balance +
                ", fecha='" + fecha + '\'' +
                ", FK_id_cuenta=" + FK_id_cuenta +
                '}';
    }


}

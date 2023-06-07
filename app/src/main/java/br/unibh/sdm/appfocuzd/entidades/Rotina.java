package br.unibh.sdm.appfocuzd.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Rotina implements Serializable {

    private String id;
    private String hora;
    private String diaDaSemana;
    private Date data;

    public Rotina() {
    }

    public String getId() {
        return id;
    }
    public String getHora() {
        return hora;
    }

    public String getDiaDaSemana() { return diaDaSemana; }

    public Date getData() {
        return data;
    }

    public void getId(String id) {
        this.id = id;
    }

    public void getHora(String nome) {
        this.hora = hora;
    }

    public void getDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public void getData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Rotina{" +
                "id='" + id + '\'' +
                ", hora='" + hora + '\'' +
                ", diaDaSemana='" + diaDaSemana + '\'' +
                ", dataConsulta=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rotina)) return false;
        Rotina that = (Rotina) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getHora(), that.getHora()) &&
                Objects.equals(getDiaDaSemana(), that.getDiaDaSemana()) &&
                Objects.equals(getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getHora(), getDiaDaSemana(), getData());
    }
}
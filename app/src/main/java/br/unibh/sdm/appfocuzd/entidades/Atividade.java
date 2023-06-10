package br.unibh.sdm.appfocuzd.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Atividade implements Serializable {

    private Long id;
    private String nomeAtividade;
    private String diaSemana;
    private String horaInicio;
    private String horaTermino;
    private String repete;

    public Atividade() {
    }

    public Long getId() {
        return id;
    }
    public String getNomeAtividade() {
        return nomeAtividade;
    }

    public String getDiaSemana() { return diaSemana; }

    public String getHoraInicio() { return horaInicio; }
    public String getHoraTermino() { return horaTermino; }

    public String getRepete() { return repete; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomeAtividade(String nomeAtividade) {
        this.nomeAtividade = nomeAtividade;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }
    public void setHoraTermino(String horaTermino) {
        this.horaTermino = horaTermino;
    }

    public void setRepete(String repete) {
        this.repete = repete;
    }

    @Override
    public String toString() {
        return "Atividade{" +
                "id='" + id + '\'' +
                ", nomeAtividade='" + nomeAtividade + '\'' +
                ", diaSemana='" + diaSemana + '\'' +
                ", horaInicio='" + horaInicio + '\'' +
                ", horaTermino='" + horaTermino + '\'' +
                ", repete='" + repete +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Atividade)) return false;
        Atividade that = (Atividade) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getNomeAtividade(), that.getNomeAtividade()) &&
                Objects.equals(getDiaSemana(), that.getDiaSemana()) &&
                Objects.equals(getHoraInicio(), that.getHoraInicio()) &&
                Objects.equals(getHoraTermino(), that.getHoraTermino()) &&
                Objects.equals(getRepete(), that.getRepete());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNomeAtividade(), getDiaSemana(), getHoraInicio(), getHoraTermino(), getRepete());
    }
}
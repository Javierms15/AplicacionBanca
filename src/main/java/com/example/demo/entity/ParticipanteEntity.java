package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "participante", schema = "sql7641808", catalog = "")
public class ParticipanteEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_participante")
    private int idParticipante;
    @Basic
    @Column(name = "id_banco")
    private int idBanco;
    @Basic
    @Column(name = "id_deal")
    private int idDeal;
    @Basic
    @Column(name = "porcentaje_participacion")
    private double porcentajeParticipacion;
    @Basic
    @Column(name = "agente")
    private byte agente;

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public int getIdDeal() {
        return idDeal;
    }

    public void setIdDeal(int idDeal) {
        this.idDeal = idDeal;
    }

    public double getPorcentajeParticipacion() {
        return porcentajeParticipacion;
    }

    public void setPorcentajeParticipacion(double porcentajeParticipacion) {
        this.porcentajeParticipacion = porcentajeParticipacion;
    }

    public byte getAgente() {
        return agente;
    }

    public void setAgente(byte agente) {
        this.agente = agente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ParticipanteEntity that = (ParticipanteEntity) o;
        return idParticipante == that.idParticipante && idBanco == that.idBanco && idDeal == that.idDeal && Double.compare(that.porcentajeParticipacion, porcentajeParticipacion) == 0 && agente == that.agente;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idParticipante, idBanco, idDeal, porcentajeParticipacion, agente);
    }
}

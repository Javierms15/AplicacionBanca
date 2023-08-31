package com.example.demo.models.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "deal", schema = "sql7641808", catalog = "")
public class DealEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_deal")
	private int idDeal;
	@Basic
	@Column(name = "estado")
	private Object estado;
	@Basic
	@Column(name = "cantidad_prestamo")
	private double cantidadPrestamo;
	@Basic
	@Column(name = "cantidad_abonada")
	private double cantidadAbonada;
	@Basic
	@Column(name = "cantidad_a_pagar")
	private double cantidadAPagar;
	@Basic
	@Column(name = "moneda")
	private Object moneda;
	@Basic
	@Column(name = "tipo")
	private Object tipo;
	@Basic
	@Column(name = "descuento")
	private byte descuento;
	@Basic
	@Column(name = "cliente")
	private int cliente;
	@Basic
	@Column(name = "creado_por")
	private int creadoPor;
	@Basic
	@Column(name = "aprobado_por", nullable = true)
	private Integer aprobadoPor;
	@Basic
	@Column(name = "cerrado_por", nullable = true)
	private Integer cerradoPor;

	public Integer getAprobadoPor() {
		return aprobadoPor;
	}

	public void setAprobadoPor(Integer aprobadoPor) {
		this.aprobadoPor = aprobadoPor;
	}

	public Integer getCerradoPor() {
		return cerradoPor;
	}

	public void setCerradoPor(Integer cerradoPor) {
		this.cerradoPor = cerradoPor;
	}

	public int getCreadoPor() {
		return creadoPor;
	}

	public void setCreadoPor(int creadoPor) {
		this.creadoPor = creadoPor;
	}

	public int getIdDeal() {
		return idDeal;
	}

	public void setIdDeal(int idDeal) {
		this.idDeal = idDeal;
	}

	public Object getEstado() {
		return estado;
	}

	public void setEstado(Object estado) {
		this.estado = estado;
	}

	public double getCantidadPrestamo() {
		return cantidadPrestamo;
	}

	public void setCantidadPrestamo(double cantidadPrestamo) {
		this.cantidadPrestamo = cantidadPrestamo;
	}

	public double getCantidadAbonada() {
		return cantidadAbonada;
	}

	public void setCantidadAbonada(double cantidadAbonada) {
		this.cantidadAbonada = cantidadAbonada;
	}

	public double getCantidadAPagar() {
		return cantidadAPagar;
	}

	public void setCantidadAPagar(double cantidadAPagar) {
		this.cantidadAPagar = cantidadAPagar;
	}

	public Object getMoneda() {
		return moneda;
	}

	public void setMoneda(Object moneda) {
		this.moneda = moneda;
	}

	public Object getTipo() {
		return tipo;
	}

	public void setTipo(Object tipo) {
		this.tipo = tipo;
	}

	public byte getDescuento() {
		return descuento;
	}

	public void setDescuento(byte descuento) {
		this.descuento = descuento;
	}

	public int getCliente() {
		return cliente;
	}

	public void setCliente(int cliente) {
		this.cliente = cliente;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		DealEntity that = (DealEntity) o;
		return idDeal == that.idDeal && Double.compare(that.cantidadPrestamo, cantidadPrestamo) == 0
				&& Double.compare(that.cantidadAbonada, cantidadAbonada) == 0
				&& Double.compare(that.cantidadAPagar, cantidadAPagar) == 0 && descuento == that.descuento
				&& cliente == that.cliente && Objects.equals(estado, that.estado) && Objects.equals(moneda, that.moneda)
				&& Objects.equals(tipo, that.tipo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idDeal, estado, cantidadPrestamo, cantidadAbonada, cantidadAPagar, moneda, tipo, descuento,
				cliente);
	}
}

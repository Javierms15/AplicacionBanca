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
	private String estado;
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
	private String moneda;
	@Basic
	@Column(name = "tipo")
	private String tipo;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
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

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DealEntity other = (DealEntity) obj;
		return Objects.equals(aprobadoPor, other.aprobadoPor)
				&& Double.doubleToLongBits(cantidadAPagar) == Double.doubleToLongBits(other.cantidadAPagar)
				&& Double.doubleToLongBits(cantidadAbonada) == Double.doubleToLongBits(other.cantidadAbonada)
				&& Double.doubleToLongBits(cantidadPrestamo) == Double.doubleToLongBits(other.cantidadPrestamo)
				&& Objects.equals(cerradoPor, other.cerradoPor) && cliente == other.cliente
				&& creadoPor == other.creadoPor && descuento == other.descuento && Objects.equals(estado, other.estado)
				&& idDeal == other.idDeal && Objects.equals(moneda, other.moneda) && Objects.equals(tipo, other.tipo);
	}

	@Override
	public int hashCode() {
		return Objects.hash(aprobadoPor, cantidadAPagar, cantidadAbonada, cantidadPrestamo, cerradoPor, cliente,
				creadoPor, descuento, estado, idDeal, moneda, tipo);
	}
}

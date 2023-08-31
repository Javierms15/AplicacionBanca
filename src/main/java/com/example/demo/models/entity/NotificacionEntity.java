package com.example.demo.models.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "notificacion", schema = "sql7641808", catalog = "")
public class NotificacionEntity {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id_notificacion")
	private int idNotificacion;
	@Basic
	@Column(name = "titulo")
	private String titulo;
	@Basic
	@Column(name = "mensaje")
	private String mensaje;
	@Basic
	@Column(name = "enviado_a")
	private int enviadoA;
	@Basic
	@Column(name = "enviado_por")
	private int enviadoPor;
	@Basic
	@Column(name = "enlace")
	private String enlace;
	@Basic
	@Column(name = "fecha_notificacion")
	private Date fechaNotificacion;

	public int getIdNotificacion() {
		return idNotificacion;
	}

	public void setIdNotificacion(int idNotificacion) {
		this.idNotificacion = idNotificacion;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public int getEnviadoA() {
		return enviadoA;
	}

	public void setEnviadoA(int enviadoA) {
		this.enviadoA = enviadoA;
	}

	public int getEnviadoPor() {
		return enviadoPor;
	}

	public void setEnviadoPor(int enviadoPor) {
		this.enviadoPor = enviadoPor;
	}

	public String getEnlace() {
		return enlace;
	}

	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}

	public Date getFechaNotificacion() {
		return fechaNotificacion;
	}

	public void setFechaNotificacion(Date fechaNotificacion) {
		this.fechaNotificacion = fechaNotificacion;
	}
}

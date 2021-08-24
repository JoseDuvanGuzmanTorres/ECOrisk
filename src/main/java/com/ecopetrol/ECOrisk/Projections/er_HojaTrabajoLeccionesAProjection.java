package com.ecopetrol.ECOrisk.Projections;

import java.util.Date;

public interface er_HojaTrabajoLeccionesAProjection {

	Integer getId();
	String getTipo();
	String getNompro();
	String getCoddoc();
	String getEstado();
	String getOportunidad();
	String getCierre();
	String getNombre();
	String getResumen();
	String getTema();
	String getSubtema();
	String getArea();
	String getRmaterializado();
	String getRmaterializadoIden();
	String getSalio_bien();
	String getSalio_mal();
	String getCausa_raiz();
	String getQue_aprendio();
	String getDescriptor();
	String getAccion_desarrollar();
	String getResponsable();
	Date getFecha();
	Integer getGap();
	Date getFechacierre();
	String getObservaciones();
	String getSeguimiento();
	String getEvidencia();
}

package com.ecopetrol.ECOrisk.Projections;

import java.util.Date;

public interface er_HojaTrabajoREmergenteProjection {

	Integer getId();
	String getTipo();
	String getNompro();
	String getCoddoc();
	String getEstado();
	String getOportunidad();
	String getCierre();
	String getEvento();
	String getEspecialidad();
	String getRam();
	String getRaconsecuencia();
	String getRaprobabilidad();
	String getRavaloracion();
	String getControl();
	String getReconsecuencia();
	String getReprobabilidad();
	String getRevaloracion();
	String getResponsable();
	Integer getGap();
	Date getFechaplaneada();
	Date getFechacierre();
	String getObservacion();
	String getSeguimiento();
	String getEvidencia();
	
}

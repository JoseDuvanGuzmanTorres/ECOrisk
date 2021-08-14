package com.ecopetrol.ECOrisk.Projections;

import java.util.Date;

public interface er_HojaTrabajoRMaterializadoProjection {

	Integer getId();
	String getTipo();
	String getNompro();
	String getCoddoc();
	String getEstado();
	String getOportunidad();
	String getCierre();
	String getDescripcionRM();
	String getRmaterializadoIden();
	String getCausaRM();
	String getConsecuenciaRM();
	String getImpactoCosto();
	String getImpactoTiempo();
	String getArea();
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

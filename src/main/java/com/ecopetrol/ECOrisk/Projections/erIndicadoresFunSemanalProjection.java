package com.ecopetrol.ECOrisk.Projections;

import java.util.Date;

public interface erIndicadoresFunSemanalProjection {

	Integer getId();
	Date getFecha();
	Integer getIddue();
	String getNomdue();
	Integer getEventos();
	Integer getRiesgosvh();
	Integer getRiesgosh();
	Integer getRiesgosm();
	Integer getRiesgosl();
	Integer getRiesgostotal();
	Integer getAbiertostotal();
	Integer getAbiertosvencidos();
	Integer getAbiertosatiempo();
	Integer getCerradostotal();
	Integer getPromgaps();
	Integer getCumplimiento();
	Integer getWhatif();
	Integer getConstructabilidad();
	Integer getDreview();
	Integer getPreview();
	
}

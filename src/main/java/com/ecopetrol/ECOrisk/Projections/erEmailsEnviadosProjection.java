package com.ecopetrol.ECOrisk.Projections;

import java.util.Date;

public interface erEmailsEnviadosProjection {

	Integer getId();
	String getAsunto();
	String getUsuario();
	Date getFecha();
		
}

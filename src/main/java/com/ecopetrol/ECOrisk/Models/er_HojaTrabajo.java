package com.ecopetrol.ECOrisk.Models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Data
@EqualsAndHashCode(exclude="seguimientos")
@NoArgsConstructor
@AllArgsConstructor
public class er_HojaTrabajo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int ht_id;

	@ManyToOne
	@JoinColumn(name="er_encabezado_id",insertable = false, updatable = false)
	private erEncabezado encabezado;
	private Integer er_encabezado_id;
	
	@Getter
	@Column(length = 2000)
	private String ht_pregunta;
	
	@Column(length = 2000)
	private String ht_evento;
	
	@ManyToOne
	@JoinColumn(name="es_especialidad_id",insertable = false, updatable = false)
	private erEspecialidad especialidad;
	private Integer es_especialidad_id;
	
	@ManyToOne
	@JoinColumn(name="er_matrizram_id",insertable = false, updatable = false)
	private erMatrizRAM matriz;
	private Integer er_matrizram_id;
	
	@Column(length = 2000)
	private String ht_salvaguardas;
	
	@ManyToOne
	@JoinColumn(name="ra_rc_id",insertable = false, updatable = false)
	private erRiesgos_Consecuencias consecuencia;
	private Integer ra_rc_id;
	
	@ManyToOne
	@JoinColumn(name="ra_rp_id",insertable = false, updatable = false)
	private erRiesgos_Probabilidad probabilidad;
	private Integer ra_rp_id;
	
	@ManyToOne
	@JoinColumn(name="ra_rv_id",insertable = false, updatable = false)
	private erRiesgos_Valoracion valoracion;
	private Integer ra_rv_id;
	
	@Column(length = 2000)
	private String ht_recomendacion;
	
	@ManyToOne
	@JoinColumn(name="ht_responsableimplementacion",insertable = false, updatable = false)
	private Users usuario;
	private Integer ht_responsableimplementacion;
	
	private Date ht_fechaplaneadacierre;
	
	@ManyToOne
	@JoinColumn(name="rr_rc_id",insertable = false, updatable = false)
	private erRiesgos_Consecuencias consecuencia2;
	private Integer rr_rc_id;
	
	@ManyToOne
	@JoinColumn(name="er_cierre_id",insertable = false, updatable = false)
	private erCierre cierre;
	private Integer er_cierre_id;
	
	@ManyToOne
	@JoinColumn(name="rr_rp_id",insertable = false, updatable = false)
	private erRiesgos_Probabilidad probabilidad2;
	private Integer rr_rp_id;
	
	@ManyToOne
	@JoinColumn(name="rr_rv_id",insertable = false, updatable = false)
	private erRiesgos_Valoracion valoracion2;
	private Integer rr_rv_id;
	
	@Column(length = 2000)
	private String ht_observacionesacta;
	
	@ManyToOne
	@JoinColumn(name="er_estado_id",insertable = false, updatable = false)
	private erEstados estados;
	private Integer er_estado_id;
	
	private Integer gap;
	private Date ht_fechacierre;
	
	private String ht_fase;
	
	@ManyToOne
	@JoinColumn(name="er_oportunidad_id",insertable = false, updatable = false)
	private erOportunidad oportunidad;
	private Integer er_oportunidad_id;
	
	@Column(length = 2000)
	private String ht_respuestaresp;
	
	//Lecciones Aprendidas
	@Column(length = 2000)
	private String nombre;
	
	@Column(length = 2000)
	private String resumen;
	
	@ManyToOne
	@JoinColumn(name="er_tema_id",insertable = false, updatable = false)
	private erTema tema;
	private Integer er_tema_id;
	
	@ManyToOne
	@JoinColumn(name="er_subtema_id",insertable = false, updatable = false)
	private erSubtema subtema;
	private Integer er_subtema_id;
	
	@Column(length = 2000)
	private String riesgo_materializado;

	@Column(length = 2000)
	private String riesgo_materializado_identi;
	
	@Column(length = 2000)
	private String salio_bien;
	
	@Column(length = 2000)
	private String salio_mal;
	
	@Column(length = 2000)
	private String causa_raiz;
	
	@Column(length = 2000)
	private String que_aprendio;
	
	@Column(length = 2000)
	private String descriptor;
	
	@Column(length = 2000)
	private String accion_desarrollar;
	//Lecciones Aprendidas fin
	
	//Riesgos Materializados
	@Column(length = 2000)
	private String descripcionrm;
	
	@Column(length = 2000)
	private String causarm;
	
	@Column(length = 2000)
	private String consecuenciarm;
	private Integer impactocosto;
	private Integer impactotiempo;
	//Riesgos Materializados fin
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name = "er_hojas_x_er_seguimiento")
	
	private Set<er_HojaSeguimiento> seguimientos = new HashSet<er_HojaSeguimiento>();
	
	public int getHt_id() {
		return ht_id;
	}

	public void setHt_id(int ht_id) {
		this.ht_id = ht_id;
	}

	public Integer getEr_encabezado_id() {
		return er_encabezado_id;
	}

	public void setEr_encabezado_id(Integer er_encabezado_id) {
		this.er_encabezado_id = er_encabezado_id;
	}

	public String getHt_pregunta() {
		return ht_pregunta;
	}

	public void setHt_pregunta(String ht_pregunta) {
		this.ht_pregunta = ht_pregunta;
	}

	public String getHt_evento() {
		return ht_evento;
	}

	public void setHt_evento(String ht_evento) {
		this.ht_evento = ht_evento;
	}

	public Integer getEs_especialidad_id() {
		return es_especialidad_id;
	}

	public void setEs_especialidad_id(Integer es_especialidad_id) {
		this.es_especialidad_id = es_especialidad_id;
	}

	public Integer getEr_matrizram_id() {
		return er_matrizram_id;
	}

	public void setEr_matrizram_id(Integer er_matrizram_id) {
		this.er_matrizram_id = er_matrizram_id;
	}

	public String getHt_salvaguardas() {
		return ht_salvaguardas;
	}

	public void setHt_salvaguardas(String ht_salvaguardas) {
		this.ht_salvaguardas = ht_salvaguardas;
	}

	public Integer getRa_rc_id() {
		return ra_rc_id;
	}

	public void setRa_rc_id(Integer ra_rc_id) {
		this.ra_rc_id = ra_rc_id;
	}

	public Integer getRa_rp_id() {
		return ra_rp_id;
	}

	public void setRa_rp_id(Integer ra_rp_id) {
		this.ra_rp_id = ra_rp_id;
	}

	public Integer getRa_rv_id() {
		return ra_rv_id;
	}

	public void setRa_rv_id(Integer ra_rv_id) {
		this.ra_rv_id = ra_rv_id;
	}

	public String getHt_recomendacion() {
		return ht_recomendacion;
	}

	public void setHt_recomendacion(String ht_recomendacion) {
		this.ht_recomendacion = ht_recomendacion;
	}

	public Integer getHt_responsableimplementacion() {
		return ht_responsableimplementacion;
	}

	public void setHt_responsableimplementacion(Integer ht_responsableimplementacion) {
		this.ht_responsableimplementacion = ht_responsableimplementacion;
	}

	public Date getHt_fechaplaneadacierre() {
		return ht_fechaplaneadacierre;
	}

	public void setHt_fechaplaneadacierre(Date ht_fechaplaneadacierre) {
		this.ht_fechaplaneadacierre = ht_fechaplaneadacierre;
	}

	public Integer getRr_rc_id() {
		return rr_rc_id;
	}

	public void setRr_rc_id(Integer rr_rc_id) {
		this.rr_rc_id = rr_rc_id;
	}

	public Integer getRr_rp_id() {
		return rr_rp_id;
	}

	public void setRr_rp_id(Integer rr_rp_id) {
		this.rr_rp_id = rr_rp_id;
	}

	public Integer getRr_rv_id() {
		return rr_rv_id;
	}

	public void setRr_rv_id(Integer rr_rv_id) {
		this.rr_rv_id = rr_rv_id;
	}

	public String getHt_observacionesacta() {
		return ht_observacionesacta;
	}

	public void setHt_observacionesacta(String ht_observacionesacta) {
		this.ht_observacionesacta = ht_observacionesacta;
	}

	public Integer getEr_estado_id() {
		return er_estado_id;
	}

	public void setEr_estado_id(Integer er_estado_id) {
		this.er_estado_id = er_estado_id;
	}

	public Integer getGap() {
		return gap;
	}

	public void setGap(Integer gap) {
		this.gap = gap;
	}

	public Date getHt_fechacierre() {
		return ht_fechacierre;
	}

	public void setHt_fechacierre(Date ht_fechacierre) {
		this.ht_fechacierre = ht_fechacierre;
	}


	public String getHt_fase() {
		return ht_fase;
	}

	public void setHt_fase(String ht_fase) {
		this.ht_fase = ht_fase;
	}

	public String getHt_respuestaresp() {
		return ht_respuestaresp;
	}

	public void setHt_respuestaresp(String ht_respuestaresp) {
		this.ht_respuestaresp = ht_respuestaresp;
	}

	public Integer getEr_oportunidad_id() {
		return er_oportunidad_id;
	}

	public void setEr_oportunidad_id(Integer er_oportunidad_id) {
		this.er_oportunidad_id = er_oportunidad_id;
	}

	public Set<er_HojaSeguimiento> getSeguimientos() {
		return seguimientos;
	}

	public void setSeguimientos(Set<er_HojaSeguimiento> seguimientos) {
		this.seguimientos = seguimientos;
	}
	
	public void addSeguimiento(er_HojaSeguimiento seguimiento) {
		this.seguimientos.add(seguimiento);
		seguimiento.getHojas().add(this);
	}
	
	public void removeSeguimiento(er_HojaSeguimiento seguimiento) {
		this.seguimientos.remove(seguimiento);
		seguimiento.getHojas().remove(this);
	}

	public erEncabezado getEncabezado() {
		return encabezado;
	}

	public void setEncabezado(erEncabezado encabezado) {
		this.encabezado = encabezado;
	}

	public erEspecialidad getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(erEspecialidad especialidad) {
		this.especialidad = especialidad;
	}

	public erMatrizRAM getMatriz() {
		return matriz;
	}

	public void setMatriz(erMatrizRAM matriz) {
		this.matriz = matriz;
	}

	public erRiesgos_Consecuencias getConsecuencia() {
		return consecuencia;
	}

	public void setConsecuencia(erRiesgos_Consecuencias consecuencia) {
		this.consecuencia = consecuencia;
	}

	public erRiesgos_Probabilidad getProbabilidad() {
		return probabilidad;
	}

	public void setProbabilidad(erRiesgos_Probabilidad probabilidad) {
		this.probabilidad = probabilidad;
	}

	public erRiesgos_Valoracion getValoracion() {
		return valoracion;
	}

	public void setValoracion(erRiesgos_Valoracion valoracion) {
		this.valoracion = valoracion;
	}

	public Users getUsuario() {
		return usuario;
	}

	public void setUsuario(Users usuario) {
		this.usuario = usuario;
	}

	public erRiesgos_Consecuencias getConsecuencia2() {
		return consecuencia2;
	}

	public void setConsecuencia2(erRiesgos_Consecuencias consecuencia2) {
		this.consecuencia2 = consecuencia2;
	}

	public erRiesgos_Probabilidad getProbabilidad2() {
		return probabilidad2;
	}

	public void setProbabilidad2(erRiesgos_Probabilidad probabilidad2) {
		this.probabilidad2 = probabilidad2;
	}

	public erRiesgos_Valoracion getValoracion2() {
		return valoracion2;
	}

	public void setValoracion2(erRiesgos_Valoracion valoracion2) {
		this.valoracion2 = valoracion2;
	}

	public erEstados getEstados() {
		return estados;
	}

	public void setEstados(erEstados estados) {
		this.estados = estados;
	}

	public erOportunidad getOportunidad() {
		return oportunidad;
	}

	public void setOportunidad(erOportunidad oportunidad) {
		this.oportunidad = oportunidad;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}

	public erTema getTema() {
		return tema;
	}

	public void setTema(erTema tema) {
		this.tema = tema;
	}

	public Integer getEr_tema_id() {
		return er_tema_id;
	}

	public void setEr_tema_id(Integer er_tema_id) {
		this.er_tema_id = er_tema_id;
	}

	public erSubtema getSubtema() {
		return subtema;
	}

	public void setSubtema(erSubtema subtema) {
		this.subtema = subtema;
	}

	public Integer getEr_subtema_id() {
		return er_subtema_id;
	}

	public void setEr_subtema_id(Integer er_subtema_id) {
		this.er_subtema_id = er_subtema_id;
	}

	public String getRiesgo_materializado() {
		return riesgo_materializado;
	}

	public void setRiesgo_materializado(String riesgo_materializado) {
		this.riesgo_materializado = riesgo_materializado;
	}

	public String getRiesgo_materializado_identi() {
		return riesgo_materializado_identi;
	}

	public void setRiesgo_materializado_identi(String riesgo_materializado_identi) {
		this.riesgo_materializado_identi = riesgo_materializado_identi;
	}

	public String getSalio_bien() {
		return salio_bien;
	}

	public void setSalio_bien(String salio_bien) {
		this.salio_bien = salio_bien;
	}

	public String getSalio_mal() {
		return salio_mal;
	}

	public void setSalio_mal(String salio_mal) {
		this.salio_mal = salio_mal;
	}

	public String getCausa_raiz() {
		return causa_raiz;
	}

	public void setCausa_raiz(String causa_raiz) {
		this.causa_raiz = causa_raiz;
	}

	public String getQue_aprendio() {
		return que_aprendio;
	}

	public void setQue_aprendio(String que_aprendio) {
		this.que_aprendio = que_aprendio;
	}

	public String getDescriptor() {
		return descriptor;
	}

	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	public String getAccion_desarrollar() {
		return accion_desarrollar;
	}

	public void setAccion_desarrollar(String accion_desarrollar) {
		this.accion_desarrollar = accion_desarrollar;
	}

	public String getDescripcionrm() {
		return descripcionrm;
	}

	public void setDescripcionrm(String descripcionrm) {
		this.descripcionrm = descripcionrm;
	}

	public String getCausarm() {
		return causarm;
	}

	public void setCausarm(String causarm) {
		this.causarm = causarm;
	}

	public String getConsecuenciarm() {
		return consecuenciarm;
	}

	public void setConsecuenciarm(String consecuenciarm) {
		this.consecuenciarm = consecuenciarm;
	}

	public Integer getImpactocosto() {
		return impactocosto;
	}

	public void setImpactocosto(Integer impactocosto) {
		this.impactocosto = impactocosto;
	}

	public Integer getImpactotiempo() {
		return impactotiempo;
	}

	public void setImpactotiempo(Integer impactotiempo) {
		this.impactotiempo = impactotiempo;
	}

	public erCierre getCierre() {
		return cierre;
	}

	public void setCierre(erCierre cierre) {
		this.cierre = cierre;
	}

	public Integer getEr_cierre_id() {
		return er_cierre_id;
	}

	public void setEr_cierre_id(Integer er_cierre_id) {
		this.er_cierre_id = er_cierre_id;
	}
	
}

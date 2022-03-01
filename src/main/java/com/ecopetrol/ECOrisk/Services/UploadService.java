package com.ecopetrol.ECOrisk.Services;

import java.util.ArrayList;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ecopetrol.ECOrisk.Models.er_HojaTrabajo;
import com.ecopetrol.ECOrisk.Models.erDepartamentos;
import com.ecopetrol.ECOrisk.Models.erEspecialidad;
import com.ecopetrol.ECOrisk.Models.erEtapas;
import com.ecopetrol.ECOrisk.Models.erGerencias;
import com.ecopetrol.ECOrisk.Models.erMatrizRAM;
import com.ecopetrol.ECOrisk.Models.erParticipantes;
import com.ecopetrol.ECOrisk.Models.erProceso;
import com.ecopetrol.ECOrisk.Models.erProyecto;
import com.ecopetrol.ECOrisk.Models.erRegionales;
import com.ecopetrol.ECOrisk.Models.erRiesgos_Consecuencias;
import com.ecopetrol.ECOrisk.Models.erRiesgos_Probabilidad;
import com.ecopetrol.ECOrisk.Models.erRiesgos_Valoracion;
import com.ecopetrol.ECOrisk.Models.erSubtema;
import com.ecopetrol.ECOrisk.Models.erTema;
import com.ecopetrol.ECOrisk.Models.erEncabezado;
import com.ecopetrol.ECOrisk.Models.Users;
import com.ecopetrol.ECOrisk.Repositories.er_HojaTrabajoRepository;
import com.ecopetrol.ECOrisk.Repositories.UserRepository;
import com.ecopetrol.ECOrisk.Repositories.erDepartamentosRepository;
import com.ecopetrol.ECOrisk.Repositories.erEspecialidadRepository;
import com.ecopetrol.ECOrisk.Repositories.erEtapasRepository;
import com.ecopetrol.ECOrisk.Repositories.erGerenciasRepository;
import com.ecopetrol.ECOrisk.Repositories.erMatrizRAMRepository;
import com.ecopetrol.ECOrisk.Repositories.erParticipantesRepository;
import com.ecopetrol.ECOrisk.Repositories.erProcesoRepository;
import com.ecopetrol.ECOrisk.Repositories.erProyectoRepository;
import com.ecopetrol.ECOrisk.Repositories.erRegionalesRepository;
import com.ecopetrol.ECOrisk.Repositories.erRiesgos_ConsecuenciasRepository;
import com.ecopetrol.ECOrisk.Repositories.erRiesgos_ProbabilidadRepository;
import com.ecopetrol.ECOrisk.Repositories.erRiesgos_ValoracionRepository;
import com.ecopetrol.ECOrisk.Repositories.erSubtemaRepository;
import com.ecopetrol.ECOrisk.Repositories.erTemaRepository;
import com.ecopetrol.ECOrisk.Repositories.erEncabezadoRepository;

import com.ecopetrol.ECOrisk.Services.UserService;

@Service
public class UploadService {

	@Autowired
	erDepartamentosRepository ErDepartamentosRepository;
	@Autowired
	erEtapasRepository ErEtapasRepository;
	@Autowired
	erGerenciasRepository ErGerenciasRepository;
	@Autowired
	erRegionalesRepository ErRegionalesRepository;
	@Autowired
	erEncabezadoRepository ErEncabezadoRepository;
	@Autowired
	er_HojaTrabajoRepository Er_HojaTrabajoRepository;
	@Autowired
	erEspecialidadRepository ErEspecialidadRepository;
	@Autowired
	erMatrizRAMRepository ErMatrizRAMRepository;
	@Autowired
	erRiesgos_ConsecuenciasRepository ErRiesgos_ConsecuenciasRepository;
	@Autowired
	erRiesgos_ProbabilidadRepository ErRiesgos_ProbabilidadRepository;
	@Autowired
	erRiesgos_ValoracionRepository ErRiesgos_ValoracionRepository;
	@Autowired
	erProyectoRepository ErProyectoRepository;
	@Autowired
	erProcesoRepository ErProcesoRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	erParticipantesRepository ErParticipantesRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private erTemaRepository ErTemaRepository;
	@Autowired
	private erSubtemaRepository ErSubtemaRepository;
	
	@Autowired
	private UserService UserService;
	

/**
 * UploadService declara el modelo de las funciones para el de cargue de talleres
 * 
 * @author José Duvan Guzmán Torres
 *
 */


	public List<String> saveDataFromUploadFile(MultipartFile file, Integer TipoEstudio) {
		List<String> getError = new ArrayList<String>();
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		if (extension.equalsIgnoreCase("xls") || extension.equalsIgnoreCase("xlsx")) {
			getError = upload(file, TipoEstudio);
		} else {
			getError.add("Extensión de archivo no válida, cargue un registro en el formato de Excel oficial.");
		}

		return getError;
	}

	private Workbook getWorkbook(MultipartFile file) {
		Workbook workbook = null;
		String extension = FilenameUtils.getExtension(file.getOriginalFilename());
		try {
			if (extension.equalsIgnoreCase("xls")) {
				workbook = new HSSFWorkbook(file.getInputStream());
			} else if (extension.equalsIgnoreCase("xlsx")) {
				workbook = new XSSFWorkbook(file.getInputStream());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return workbook;
	}

	public List<String> upload(MultipartFile file, Integer TipoEstudio) {
		// Define error String
		List<String> Error = new ArrayList<String>();
		// Open Excel file by extension
		Workbook workbook = getWorkbook(file);
		// Define variables to assign and create new Model
		erEncabezado encabezado = new erEncabezado();
		// Obtiene y crea el encabezado y devuelve los errores
		Error = Encabezado(Error, workbook, encabezado, TipoEstudio);

		return Error;
	}

	public List<String> Encabezado(List<String> Error, Workbook workbook, erEncabezado encabezado,
			Integer TipoEstudio) {
		List<Users> usuariosTemporales = new ArrayList<Users>();
		int rowcount = 0;
		List<String> TempError = new ArrayList<String>();
		Sheet sheet3 = workbook.getSheetAt(2);
		Iterator<Row> rows3 = sheet3.iterator();
		List<erParticipantes> ParticipantesList = new ArrayList<erParticipantes>();
		Map<String, Users> Asistentes = new HashMap<>();
		if (!rows3.hasNext()) {

			Error.add("Formato Incorrecto o archivo vacío. \n");
		} else {
			try {
				Integer altura = 13;
				Integer nombre = 3;
				Integer rol = 5;
				Integer dependencia = 7;
				Integer correo = 9;

				while (rows3.hasNext()) {
					Row row = rows3.next();
					if (rowcount > altura) {

						erParticipantes participante = new erParticipantes();

						// Nombre
						String key = "";
						boolean tempNombre = false;
						TempError = getErrores(row, nombre, 255, "Nombre del participante", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(nombre).getStringCellValue();
							key = Temp;

						} else {
							if (row.getCell(nombre).getCellType() == CellType.STRING) {
								Error.addAll(TempError);
							} else {
								tempNombre = true;
							}
						}

						// Correo institucional
						Users TempUsers = null;
						boolean tempCorreo = false;
						TempError = getErrores(row, correo, 255, "Correo institucional del participante", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(correo).getStringCellValue();
							if (esEmail(Temp)) {
								TempUsers = userRepository.findByUsername(Temp);
								if (TempUsers != null) {
									if (!key.equals("")) {
										Asistentes.put(key, TempUsers);
										participante.setEr_participante(TempUsers.getId());
									}
								} else {
									if (!key.equals("")) {
										Users TemporalUser = new Users();
										TemporalUser.setFullname(key);
										TemporalUser.setUsername(Temp);
										TemporalUser.setPassword(encoder.encode("12345"));
										TemporalUser.setRoles_id(4);
										userRepository.save(TemporalUser);
										usuariosTemporales.add(TemporalUser);

										Asistentes.put(key, TemporalUser);
										participante.setEr_participante(TemporalUser.getId());

									}
								}

							} else {
								Error.add(
										"El formato del correo del participante " + Temp + " no es un correo válido.");

							}
						} else {
							if (row.getCell(correo).getCellType() == CellType.STRING) {
								Error.addAll(TempError);
							} else {
								tempCorreo = true;
							}
						}

						// Validación Cierre
						if ((tempNombre == true && tempCorreo == true)) {
							if (rowcount == altura) {
								Error.add("La hoja de participantes está vacía");
							}
							break;
						} else {
							if (tempNombre == true && tempCorreo == false) {
								Error.add("Nombre del participante vacío FILA(" + (rowcount + 1) + ")");
							} else if (tempNombre == false && tempCorreo == true) {
								Error.add("Correo del participante vacío FILA(" + (rowcount + 1) + ")");
							}
						}

						// Rol
						TempError = getErrores(row, rol, 255, "El rol del participante", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(rol).getStringCellValue();
							participante.setEr_rol(Temp);
						} else {
							Error.addAll(TempError);
						}

						// Dependencia
						TempError = getErrores(row, dependencia, 255, "La dependencia/organización del participante",
								rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(dependencia).getStringCellValue();
							participante.setEr_dependencia(Temp);
						} else {
							Error.addAll(TempError);
						}

						// End
						if (Error.isEmpty()) {
							ParticipantesList.add(participante);
						}

					}

					rowcount++;
				}
			} catch (Exception e) {
				Error.add("Ocurrió un error inesperado en la hoja de participantes, por favor revise la información");
			}
		}

		if (Error.isEmpty()) {
			if (TipoEstudio == 6) {
				Error.addAll(EncabezadoLeccionesA(workbook, encabezado, TipoEstudio, Asistentes, usuariosTemporales,
						ParticipantesList));
			} else {
				Error.addAll(EncabezadoNormal(workbook, encabezado, TipoEstudio, Asistentes, usuariosTemporales,
						ParticipantesList));

			}
		}

		return Error;
	}

	public List<String> EncabezadoNormal(Workbook workbook, erEncabezado encabezado, Integer TipoEstudio,
			Map<String, Users> Asistentes, List<Users> usuariosTemporales, List<erParticipantes> ParticipantesList) {
		List<String> TempError = new ArrayList<String>();
		List<String> Error = new ArrayList<String>();
		Sheet sheet1 = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet1.iterator();
		TempError = new ArrayList<String>();
		int rowcount = 0;
		Integer num;
		List<String> PosiblesErrores = new ArrayList<String>();
		if (!rows.hasNext()) {
			Error.add("Formato Incorrecto o archivo vacío. \n");
		} else {
			try {
				
				while (rows.hasNext()) {
					Row row = rows.next();
					switch (rowcount) {
					case 5:

						// Nombre proyecto
						num = 5;
						TempError = getErrores(row, num, 255, "Nombre del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String TempNom = row.getCell(num).getStringCellValue();

							encabezado.setE_nombreproyecto(TempNom);
						} else {
							Error.addAll(TempError);
						}

						// Objetivo Proyecto
						num = 11;
						TempError = getErrores(row, num, 255, "Objetivo del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();

							encabezado.setE_objetivoproyecto(Temp);
						} else {
							PosiblesErrores.addAll(TempError);
						}

						break;

					case 6:

						// Etapa proyecto
						num = 5;
						TempError = getErrores(row, num, 255, "Etapa del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erEtapas Tempo = ErEtapasRepository.findByEtapa(Temp);
							if (Tempo == null) {
								Error.add("Etapa Inválida.");
							} else {
								encabezado.setEe_etapaproyecto_id(Tempo.getEe_etapaproyecto_id());
								
							}
						} else {
							if (TipoEstudio != 5) {
								PosiblesErrores.addAll(TempError);
							}
						}

						num = 11;
						TempError = getErrores(row, num, 255, "Regional del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erRegionales Tempo = ErRegionalesRepository.findByRegional(Temp);
							if (Tempo == null) {
								Error.add("Regional Inválida.");
							} else {
								encabezado.setEr_regionalgsc_id(Tempo.getEr_regionalgsc_id());
							}
						} else {
							if (TipoEstudio != 5) {
								Error.addAll(TempError);
							}
						}

						break;

					case 7:

						// Codigo documento
						num = 5;
						TempError = getErrores(row, num, 255, "Código del documento", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erEncabezado Tempo = ErEncabezadoRepository.findByCodDoc(Temp);
							if (Tempo == null) {
								encabezado.setE_codigodocumento(Temp);
							} else {
								Error.add("Ya hay un documento registrado con el código " + Temp);
							}
						} else {
							Error.addAll(TempError);
						}

						// Gerencia
						num = 11;
						TempError = getErrores(row, num, 255, "Gerencia del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erGerencias Tempo = ErGerenciasRepository.findByGerencia(Temp);
							if (Tempo == null) {
								Error.add("Gerencia Inválida.");
							} else {
								encabezado.setEg_gerenciacliente_id(Tempo.getEg_gerenciacliente_id());
							}
						} else {
							if (TipoEstudio != 5) {
								Error.addAll(TempError);
							}
						}

						break;

					case 8:

						// Dueño proceso o Lider proyecto
						num = 5;
						
						TempError = getErrores(row, num, 255, "Dueño del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							String Correo = row.getCell(5).getStringCellValue();
							
							
							
							Users usuarioN = new Users();
							usuarioN.setFullname(Temp);
							usuarioN.setUsername(Correo);
							UserService.save(usuarioN);
							encabezado.setE_liderproyecto(usuarioN.getId()); 
							
							//comprovaciones antiguas de dueno de proyecto
							/*
							 * if (Asistentes.containsKey(Temp)) { Users usuario = Asistentes.get(Temp);
							 * encabezado.setE_liderproyecto(usuario.getId()); if (usuario.getRoles_id() !=
							 * 2 && usuario.getRoles_id() != 3 && usuario.getRoles_id() != 6) {
							 * usuario.setRoles_id(1); userRepository.save(usuario); }
							 * 
							 * } else { Error.add("El usuario \"" + Temp +
							 * "\" no se encuentra en la lista de participantes"); }
							 */
							
	 					} else {
							Error.addAll(TempError);
						}
						// Departamento
						num = 11;
						TempError = getErrores(row, num, 255, "Departamento del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erDepartamentos Tempo = ErDepartamentosRepository.findByDepartamento(Temp);
							if (Tempo == null) {
								Error.add("Departamento Inválido.");
							} else {
								encabezado.setEd_deptocliente_id(Tempo.getEd_deptocliente_id());
							}
						} else {
							if (TipoEstudio != 5) {
								Error.addAll(TempError);
							}
						}

						break;

					case 9:
						//correo electronico dueño de taller
						//coordinacion cliente
						num = 11;
						TempError = getErrores(row, num, 255, "Coordinacion del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erDepartamentos Tempo = ErDepartamentosRepository.findByDepartamento(Temp);
							if (Tempo == null) {
								Error.add("Coordinacion Inválida.");
							} else {
								encabezado.setEd_coordcliente_id(Tempo.getEd_deptocliente_id());
							}
						} else {
							if (TipoEstudio != 5) {
								Error.addAll(TempError);
							}
						}
						

						break;

					case 10:
						// Lider Taller
						num = 5;
						TempError = getErrores(row, num, 255, "Lider Taller", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();

							
							if (Asistentes.containsKey(Temp)) {
								Users usuario = Asistentes.get(Temp);
								encabezado.setE_lidertaller(usuario.getId());
								if (usuario.getRoles_id() != 1 && usuario.getRoles_id() != 2
										&& usuario.getRoles_id() != 3 && usuario.getRoles_id() != 6) {
									usuario.setRoles_id(5);
									userRepository.save(usuario);
								}
							} else {
								Error.add("El usuario \"" + Temp + "\" no se encuentra en la lista de participantes");
							}

						} else {
							Error.addAll(TempError);
						}

						// Instalacion Cliente
						num = 11;
						TempError = getErrores(row, num, 255, "Instalacion del cliente", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							encabezado.setE_instalacioncliente(Temp);
						} else {
							PosiblesErrores.addAll(TempError);
						}

						break;
					case 11:

						// Fecha taller
						num = 5;
						TempError = getErrores(row, num, 255, "Fecha del taller", rowcount);
						if (TempError.isEmpty()) {
							try {
								Date Temp = row.getCell(5).getDateCellValue();
								encabezado.setE_fechataller(Temp);

							} catch (Exception e) {
								Error.add("La fecha del taller tiene un formato incorrecto.");
							}
						} else {
							Error.addAll(TempError);
						}

						// Proceso
						boolean opex = false;
						num = 13;
						TempError = getErrores(row, num, 255, "Proceso", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erProceso Tempo = ErProcesoRepository.findByProceso(Temp);
							if (Tempo == null) {
								Error.add("El proceso seleccionado no es correcto.");
							} else {
								encabezado.setEr_proceso_id(Tempo.getEr_proceso_id());
								if (Tempo.getEr_proceso_id() == 2) {
									opex = true;
								}
							}
						} else {
							Error.addAll(TempError);
						}

						// Código proyecto

						num = 7;
						TempError = getErrores(row, num, 255, "Código del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							if (!Temp.equals("") && !Temp.equals(" ")) {
								erProyecto Tempo = ErProyectoRepository.findByProyectoCod(Temp);
								if (Tempo == null) {
									Error.add("El código del proyecto no es correcto.");
								} else {
									encabezado.setEr_proyecto_id(Tempo.getEr_proyecto_id());
								}
							}

						} else {
							if (!opex) {
								Error.addAll(PosiblesErrores);
								Error.addAll(TempError);

							} else {
								encabezado.setEr_proyecto_id(952);
								String objetivo = encabezado.getE_objetivoproyecto();
								Integer etapa = encabezado.getEe_etapaproyecto_id();
								String instalacion = encabezado.getE_instalacioncliente();

								if (objetivo == null) {
									encabezado.setE_objetivoproyecto("N/A");
								}
								if (etapa == null) {
									encabezado.setEe_etapaproyecto_id(7);
								}
								if (instalacion == null) {
									encabezado.setE_instalacioncliente("N/A");
								}
							}
						}

						num = 11;
						if (TipoEstudio == 3 || TipoEstudio == 4) {
							// TIPO TALLER
							if (row.getCell(num).getCellType() == CellType.STRING) {
							
								String tipotaller = row.getCell(num).getStringCellValue();
						
								if (tipotaller.equals("")) {
									
									Error.add("El tipo de taller está vacío");

								}else if (tipotaller.equals("Design Review") && TipoEstudio == 4) {

									Error.add(
											"El tipo de taller no corresponde, usted seleccionó un PeerReview en el cargue de Design Review");

								} else if (tipotaller.equals("Peer Review") && TipoEstudio == 3) {
										
									Error.add(
											"El tipo de taller no corresponde, usted seleccionó un Design Review en el cargue de PeerReview");

								}
							} else {
								Error.add("El tipo de taller no válido o está vacío.");
							}
						}
						break;
					}
					rowcount++;
				}
			} catch (Exception e) {
				Error.add("Error Inesperado en la hoja \"Agenda\", revisar la informacion y sus formatos.");
			}
		}

		if (Error.isEmpty()) {
			if (TipoEstudio == 7) {
				// Riesgos Materializados
				TempError = HojaTrabajoRiesgosM(workbook, encabezado, TipoEstudio, usuariosTemporales,
						ParticipantesList, Asistentes);
			} else {
				TempError = HojaTrabajo(workbook, encabezado, TipoEstudio, usuariosTemporales, ParticipantesList,
						Asistentes);
			}
			Error.addAll(TempError);

		}
		return Error;
	}

	public List<String> EncabezadoLeccionesA(Workbook workbook, erEncabezado encabezado, Integer TipoEstudio,
			Map<String, Users> Asistentes, List<Users> usuariosTemporales, List<erParticipantes> ParticipantesList) {
		List<String> TempError = new ArrayList<String>();
		List<String> Error = new ArrayList<String>();
		Sheet sheet1 = workbook.getSheetAt(0);
		Iterator<Row> rows = sheet1.iterator();
		TempError = new ArrayList<String>();
		int rowcount = 0;
		Integer num;
		List<String> PosiblesErrores = new ArrayList<String>();
		if (!rows.hasNext()) {

			Error.add("Formato Incorrecto o archivo vacío. \n");
		} else {
			try {
				while (rows.hasNext()) {
					Row row = rows.next();
					switch (rowcount) {
					case 5:

						// Nombre proyecto
						num = 5;
						TempError = getErrores(row, num, 255, "Nombre del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String TempNom = row.getCell(num).getStringCellValue();
							encabezado.setE_nombreproyecto(TempNom);
						} else {
							Error.addAll(TempError);
						}

						// Objetivo Proyecto
						num = 11;
						TempError = getErrores(row, num, 255, "Objetivo del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							encabezado.setE_objetivoproyecto(Temp);
						} else {
							PosiblesErrores.addAll(TempError);
						}

						break;

					case 6:

						// Lugar
						num = 5;
						TempError = getErrores(row, num, 255, "Lugar de ejecución", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							encabezado.setE_instalacioncliente(Temp);
						} else {
							PosiblesErrores.addAll(TempError);
						}

						// Lider Taller
						num = 11;
						TempError = getErrores(row, num, 255, "Lider Taller", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							if (Asistentes.containsKey(Temp)) {
								Users usuario = Asistentes.get(Temp);
								encabezado.setE_lidertaller(usuario.getId());
								if (usuario.getRoles_id() != 1 && usuario.getRoles_id() != 2
										&& usuario.getRoles_id() != 3 && usuario.getRoles_id() != 6) {
									usuario.setRoles_id(5);
									userRepository.save(usuario);
								}
							} else {
								Error.add("El usuario \"" + Temp + "\" no se encuentra en la lista de participantes");
							}

						} else {
							Error.addAll(TempError);
						}

						break;

					case 7:

						// Etapa proyecto
						num = 5;
						TempError = getErrores(row, num, 255, "Etapa del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erEtapas Tempo = ErEtapasRepository.findByEtapa(Temp);
							if (Tempo == null) {
								Error.add("Etapa Inválida. : " + Temp);
							} else {
								encabezado.setEe_etapaproyecto_id(Tempo.getEe_etapaproyecto_id());
							}
						} else {
							if (TipoEstudio != 5) {
								PosiblesErrores.addAll(TempError);
							}
						}

						// Fecha fecha de inicio
						num = 11;
						TempError = getErrores(row, num, 255, "Fecha de inicio", rowcount);
						if (TempError.isEmpty()) {
							try {
								Date Temp = row.getCell(num).getDateCellValue();
								encabezado.setE_fechainicio(Temp);

							} catch (Exception e) {
								Error.add("La fecha de inicio tiene un formato incorrecto.");
							}
						} else {
							Error.addAll(TempError);
						}

						// Fecha fecha de fin
						num = 13;
						TempError = getErrores(row, num, 255, "Fecha de fin", rowcount);
						if (TempError.isEmpty()) {
							try {
								Date Temp = row.getCell(num).getDateCellValue();
								encabezado.setE_fechafin(Temp);

							} catch (Exception e) {
								Error.add("La fecha de fin tiene un formato incorrecto.");
							}
						} else {
							Error.addAll(TempError);
						}

						break;

					case 8:
						// Gerencia N/A
						encabezado.setEg_gerenciacliente_id(114);
						// Regional N/A
						encabezado.setEr_regionalgsc_id(7);

						boolean dependenciaval = false;

						// Dependencia 1
						num = 5;
						TempError = getErrores(row, num, 255, "Dependencia 1 del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erDepartamentos Tempo = ErDepartamentosRepository.findByDepartamento(Temp);
							if (Tempo == null) {
								Error.add("Dependencia 1 Inválida.");
							} else {
								encabezado.setEd_deptocliente_id(Tempo.getEd_deptocliente_id());
								dependenciaval = true;
							}
						} else {
							Error.addAll(TempError);
						}

						boolean dependenciaval2 = false;

						// Dependencia 2
						num = 6;
						TempError = getErrores(row, num, 255, "Dependencia 2 del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erDepartamentos Tempo = ErDepartamentosRepository.findByDepartamento(Temp);
							if (Tempo == null) {
								Error.add("Dependencia 2 Inválida.");
							} else {
								encabezado.setEd_deptocliente_id2(Tempo.getEd_deptocliente_id());
								dependenciaval2 = true;
							}
						} else {
							if (dependenciaval == false) {
								Error.addAll(TempError);
							}

						}

						// Dependencia 3
						num = 7;
						TempError = getErrores(row, num, 255, "Dependencia 3 del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erDepartamentos Tempo = ErDepartamentosRepository.findByDepartamento(Temp);
							if (Tempo == null) {
								Error.add("Dependencia 3 Inválida.");
							} else {
								encabezado.setEd_deptocliente_id3(Tempo.getEd_deptocliente_id());
							}
						} else {
							if (dependenciaval == false && dependenciaval2 == false) {
								Error.addAll(TempError);
							}

						}

						/*
						 * // Contacto num = 11; TempError = getErrores(row, num, 255, "Contacto",
						 * rowcount); if (TempError.isEmpty()) { String Temp =
						 * row.getCell(num).getStringCellValue(); if (Asistentes.containsKey(Temp)) {
						 * Users usuario = Asistentes.get(Temp);
						 * encabezado.setE_contacto(usuario.getId()); if (usuario.getRoles_id() != 1 &&
						 * usuario.getRoles_id() != 2 && usuario.getRoles_id() != 3 &&
						 * usuario.getRoles_id() != 6 && usuario.getRoles_id() != 5) {
						 * usuario.setRoles_id(4); userRepository.save(usuario); }
						 * 
						 * } else { Error.add("El usuario \"" + Temp +
						 * "\" no se encuentra en la lista de participantes"); } } else {
						 * Error.addAll(TempError); }
						 */

						break;

					case 9:

						// Dueño proceso o Lider proyecto
						num = 5;
						TempError = getErrores(row, num, 255, "Dueño del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							if (Asistentes.containsKey(Temp)) {
								Users usuario = Asistentes.get(Temp);
								encabezado.setE_liderproyecto(usuario.getId());
								if (usuario.getRoles_id() != 2 && usuario.getRoles_id() != 3
										&& usuario.getRoles_id() != 6) {
									usuario.setRoles_id(1);
									userRepository.save(usuario);
								}

							} else {
								Error.add("El usuario \"" + Temp + "\" no se encuentra en la lista de participantes");
							}
						} else {
							Error.addAll(TempError);
						}
						
						// Codigo documento

						num = 11;
						TempError = getErrores(row, num, 255, "Código del documento", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erEncabezado Tempo = ErEncabezadoRepository.findByCodDoc(Temp);
							if (Tempo == null) {
								encabezado.setE_codigodocumento(Temp);
							} else {
								Error.add("Ya hay un documento registrado con el código " + Temp);
							}
						} else {
							Error.addAll(TempError);
						}

						break;

					case 10:

						// Fecha taller
						num = 5;
						TempError = getErrores(row, num, 255, "Fecha del taller", rowcount);
						if (TempError.isEmpty()) {
							try {
								Date Temp = row.getCell(num).getDateCellValue();
								encabezado.setE_fechataller(Temp);

							} catch (Exception e) {
								Error.add("La fecha del taller tiene un formato incorrecto.");
							}
						} else {
							Error.addAll(TempError);
						}



						// Proceso
						boolean opex = false;
						num = 13;
						TempError = getErrores(row, num, 255, "Proceso", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							erProceso Tempo = ErProcesoRepository.findByProceso(Temp);
							if (Tempo == null) {
								Error.add("El proceso seleccionado no es correcto.");
							} else {
								encabezado.setEr_proceso_id(Tempo.getEr_proceso_id());
								if (Tempo.getEr_proceso_id() == 2) {
									opex = true;
								}
							}
						} else {
							Error.addAll(TempError);
						}

						// Código proyecto
						num = 7;
						TempError = getErrores(row, num, 255, "Código del proyecto", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(num).getStringCellValue();
							if (!Temp.equals("") && !Temp.equals(" ")) {
								erProyecto Tempo = ErProyectoRepository.findByProyectoCod(Temp);
								if (Tempo == null) {
									Error.add("El código del proyecto no es correcto.");
								} else {
									encabezado.setEr_proyecto_id(Tempo.getEr_proyecto_id());
								}
							}

						} else {
							if (!opex) {
								Error.addAll(PosiblesErrores);
								Error.addAll(TempError);

							} else {
								encabezado.setEr_proyecto_id(952);
								String objetivo = encabezado.getE_objetivoproyecto();
								Integer etapa = encabezado.getEe_etapaproyecto_id();
								String instalacion = encabezado.getE_instalacioncliente();

								if (objetivo == null) {
									encabezado.setE_objetivoproyecto("N/A");
								}
								if (etapa == null) {
									encabezado.setEe_etapaproyecto_id(7);
								}
								if (instalacion == null) {
									encabezado.setE_instalacioncliente("N/A");
								}
							}
						}

						num = 11;
						if (TipoEstudio == 3 || TipoEstudio == 4) {
							// TIPO TALLER
							if (row.getCell(num).getCellType() == CellType.STRING) {
								String tipotaller = row.getCell(num).getStringCellValue();
								if (tipotaller.equals("")) {

									Error.add("El tipo de taller está vacío");

								} else if (tipotaller.equals("Design Review") && TipoEstudio == 4) {

									Error.add(
											"El tipo de taller no corresponde, usted seleccionó un PeerReview en el cargue de Design Review");

								} else if (tipotaller.equals("Peer Review") && TipoEstudio == 3) {

									Error.add(
											"El tipo de taller no corresponde, usted seleccionó un Design Review en el cargue de PeerReview");

								}
							} else {
								Error.add("El tipo de taller no válido o está vacío.");
							}
						}

					}

					rowcount++;
				}
			} catch (Exception e) {
				Error.add("Formato incorrecto");
			}
		}

		if (Error.isEmpty()) {
			if (TipoEstudio == 6) {
				TempError = HojaTrabajoLeccionesA(workbook, encabezado, TipoEstudio, usuariosTemporales,
						ParticipantesList, Asistentes);
			}

			Error.addAll(TempError);

		}
		return Error;
	}

	public List<String> HojaTrabajoLeccionesA(Workbook workbook, erEncabezado encabezado, Integer TipoEstudio,
			List<Users> usuariosTemporales, List<erParticipantes> ParticipantesList, Map<String, Users> Asistentes) {
		List<String> Error = new ArrayList<String>();
		Integer altura = -1;
		Integer nombre = -1;
		Integer resumen = -1;
		Integer er_tema_id = -1;
		Integer er_subtema_id = -1;
		Integer er_area_id = -1;
		Integer salio_bien = -1;
		Integer salio_mal = -1;
		Integer causa_raiz = -1;
		Integer que_aprendio = -1;
		Integer descriptor = -1;
		Integer accion_desarrollar = -1;
		Integer responsable = -1;
		Integer fecha = -1;
		Integer observaciones = -1;
		switch (TipoEstudio) {
		case 6:
			altura = 13;
			nombre = 2;
			resumen = 3;
			er_tema_id = 4;
			er_subtema_id = 5;
			er_area_id = 6;
			salio_bien = 7;
			salio_mal = 8;
			causa_raiz = 9;
			que_aprendio = 10;
			descriptor = 11;
			accion_desarrollar = 12;
			responsable = 13;
			fecha = 14;
			observaciones = 15;
			break;
		}

		Sheet sheet1 = workbook.getSheetAt(3);
		Iterator<Row> rows = sheet1.iterator();
		List<er_HojaTrabajo> HojaTrabajoList = new ArrayList<er_HojaTrabajo>();
		List<String> TempError = new ArrayList<String>();
		try {
			int rowcount = 0;
			while (rows.hasNext()) {
				Row row = rows.next();
				boolean cerrado = false;
				if (rowcount > altura) {
					er_HojaTrabajo hojatrabajo = new er_HojaTrabajo();

					// Nombre de la lección aprendida
					boolean tempNombre = false;
					if (nombre != -1) {
						TempError = getErrores(row, nombre, 2000, "El nombre de la lección aprendida", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(nombre).getStringCellValue();
							hojatrabajo.setNombre(Temp);

						} else {
							if (row.getCell(nombre).getCellType() == CellType.STRING) {
								Error.addAll(TempError);
							} else {
								tempNombre = true;
							}
						}
					}

					// Resumen
					boolean tempResumen = false;
					if (resumen != -1) {
						TempError = getErrores(row, resumen, 2000, "El resumen", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(resumen).getStringCellValue();
							hojatrabajo.setResumen(Temp);
						} else {
							if (row.getCell(resumen).getCellType() == CellType.STRING) {
								Error.addAll(TempError);
							} else {
								tempResumen = true;
							}
						}
					}

					if (tempNombre == true && tempResumen == false) {
						Error.add("El nombre de la lección aprendida está vacío FILA(" + (rowcount + 1) + ")");
					} else if (tempNombre == false && tempResumen == true) {
						Error.add("El resumen está vacío FILA(" + (rowcount + 1) + ")");
					} else if (tempNombre == true && tempResumen == true) {
						if (rowcount == altura) {
							Error.add("La hoja de trabajo está vacía");
						}
						break;
					}
					
					// Observaciones
					if (observaciones != -1) {
						TempError = getErrores(row, observaciones, 2000, "La observación", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(observaciones).getStringCellValue();

							if (Temp.startsWith("Cerrado") || Temp.startsWith("cerrado")) {
								cerrado = true;
								hojatrabajo.setEr_estado_id(3);
								hojatrabajo.setHt_fechacierre(encabezado.getE_fechataller());
								hojatrabajo.setEr_cierre_id(1);
							}
							hojatrabajo.setHt_observacionesacta(Temp);
						} else {
							if (row.getCell(observaciones).getCellType() == CellType.STRING) {
								Error.addAll(TempError);
							}
						}
					}

					// Tema de conocimiento
					if (er_tema_id != -1) {
						TempError = getErrores(row, er_tema_id, 255, "El tema de conocimiento", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(er_tema_id).getStringCellValue();
							erTema Tempo = ErTemaRepository.findByTema(Temp);
							if (Tempo == null) {
								Error.add("Tema Inválido. FILA (" + (rowcount + 1) + ")");
							} else {
								hojatrabajo.setEr_tema_id(Tempo.getEr_tema_id());
							}
						} else {
							if (cerrado == false) {
								Error.addAll(TempError);
							}
						}
					}

					// Subtema de conocimiento
					if (er_subtema_id != -1) {
						TempError = getErrores(row, er_subtema_id, 255, "El subtema de conocimiento", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(er_subtema_id).getStringCellValue();
							erSubtema Tempo = ErSubtemaRepository.findBySubema(Temp);
							if (Tempo == null) {
								Error.add("Subtema Inválido. FILA (" + (rowcount + 1) + ")");
							} else {
								hojatrabajo.setEr_subtema_id(Tempo.getEr_subtema_id());
							}
						} else {
							if (cerrado == false) {
								Error.addAll(TempError);
							}
						}
					}

					// Area de conocimiento / especialidad
					if (er_area_id != -1) {
						TempError = getErrores(row, er_area_id, 255, "El área de conocimiento", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(er_area_id).getStringCellValue();
							erEspecialidad Tempo = ErEspecialidadRepository.findByEspecialidad(Temp);
							if (Tempo == null) {
								Error.add("Área Inválida. FILA (" + (rowcount + 1) + ")");
							} else {
								hojatrabajo.setEs_especialidad_id(Tempo.getEs_especialidad_id());
							}
						} else {
							if (cerrado == false) {
								Error.addAll(TempError);
							}
						}
					}

					// Qué salió bien
					boolean tempBien = false;
					if (salio_bien != -1) {
						TempError = getErrores(row, salio_bien, 2000, "¿Qué salió bien?", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(salio_bien).getStringCellValue();
							hojatrabajo.setSalio_bien(Temp);
						} else {
							tempBien = true;
						}

					}

					// Qué salió mal
					if (salio_mal != -1) {
						TempError = getErrores(row, salio_mal, 2000, "¿Qué salió mal?", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(salio_mal).getStringCellValue();
							hojatrabajo.setSalio_mal(Temp);
						} else {
							if (tempBien == true) {
								Error.add("Formato de ¿Qué salió bien? incorrecto o está vacío FILA(" + (rowcount + 1)
										+ ")");
								Error.addAll(TempError);
							}

						}

					}

					// Causa raíz
					if (causa_raiz != -1) {
						TempError = getErrores(row, causa_raiz, 2000, "Causa raíz", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(causa_raiz).getStringCellValue();
							hojatrabajo.setCausa_raiz(Temp);
						}

					}

					// Que se aprendió
					if (que_aprendio != -1) {
						TempError = getErrores(row, que_aprendio, 2000, "¿Qué se aprendió?", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(que_aprendio).getStringCellValue();
							hojatrabajo.setQue_aprendio(Temp);
						} else {
							Error.addAll(TempError);
						}

					}

					// Descriptores
					if (descriptor != -1) {
						TempError = getErrores(row, descriptor, 2000, "Descriptores", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(descriptor).getStringCellValue();

							hojatrabajo.setDescriptor(Temp);
						} else {
							Error.addAll(TempError);
						}

					}

					// Acción a desarrollar(control)
					if (accion_desarrollar != -1) {
						TempError = getErrores(row, accion_desarrollar, 2000, "Acción a desarrollar", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(accion_desarrollar).getStringCellValue();
							hojatrabajo.setAccion_desarrollar(Temp);

						} else {
							Error.addAll(TempError);
						}

					}

					// Responsable Implementación
					if (responsable != -1) {
						TempError = getErrores(row, responsable, 255, "El responsable de implementación", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(responsable).getStringCellValue();
							if (Temp.equals("N/A") || Temp.equals("N/A")) {
								hojatrabajo.setHt_responsableimplementacion(105);
							} else {
								if (Asistentes.containsKey(Temp)) {
									Users usuario = Asistentes.get(Temp);
									if (usuario.getRoles_id() != 1 && usuario.getRoles_id() != 2
											&& usuario.getRoles_id() != 3 && usuario.getRoles_id() != 5
											&& usuario.getRoles_id() != 6) {
										usuario.setRoles_id(4);
										userRepository.save(usuario);
									}
									hojatrabajo.setHt_responsableimplementacion(usuario.getId());
								} else {
									Error.add("El usuario \"" + Temp + "\"  en la FILA(" + (rowcount + 1)
											+ ")  no se encuentra en la lista de participantes");
								}
							}

						} else {
							Error.addAll(TempError);
						}

					}
					
					// Fecha
					if (fecha != -1) {
						TempError = getErrores(row, fecha, 255, "La fecha", rowcount);
						if (TempError.isEmpty()) {
							try {
								Date Temp = row.getCell(fecha).getDateCellValue();
								hojatrabajo.setHt_fechaplaneadacierre(Temp);
							} catch (Exception e) {
								Error.add("Formato de fecha incorrecto FILA(" + (rowcount + 1) + ")");
							}
						} else {
							Error.addAll(TempError);
						}
					}


					// End
					if (Error.isEmpty()) {
						HojaTrabajoList.add(hojatrabajo);
					}
				}

				rowcount++;
			}

		} catch (Exception e) {
			Error.add(
					"Error Inesperado, Revise la información de la hoja de trabajo, corrija e intente cargarla nuevamente.");
		}

		if (Error.isEmpty()) {
			Date ahora = new Date();
			encabezado.setAud_fechacreacion(ahora);
			encabezado.setEr_tipoestudio_id(TipoEstudio);
			ErEncabezadoRepository.save(encabezado);
			Integer encaid = encabezado.getEr_encabezado_id();

			for (erParticipantes participante : ParticipantesList) {

				participante.setEr_encabezado_id(encaid);
			}
			ErParticipantesRepository.saveAll(ParticipantesList);

			for (er_HojaTrabajo hojatrabajo : HojaTrabajoList) {
				hojatrabajo.setEr_encabezado_id(encaid);
				if (hojatrabajo.getEr_estado_id() == null) {
					hojatrabajo.setEr_estado_id(1);// 1 Abierto, 2 Cerrado.
				}
				if (hojatrabajo.getHt_fechaplaneadacierre() != null) {
					if (hojatrabajo.getHt_fechaplaneadacierre().compareTo(ahora) >= 0) {
						hojatrabajo.setEr_oportunidad_id(2);
					} else {
						hojatrabajo.setEr_oportunidad_id(1);
					}
				}else {
					hojatrabajo.setEr_oportunidad_id(2);
				}
			}
			Er_HojaTrabajoRepository.saveAll(HojaTrabajoList);

		} else {
			userRepository.deleteAll(usuariosTemporales);
			ErParticipantesRepository.deleteAll(ParticipantesList);
		}

		return Error;
	}

	public List<String> HojaTrabajoRiesgosM(Workbook workbook, erEncabezado encabezado, Integer TipoEstudio,
			List<Users> usuariosTemporales, List<erParticipantes> ParticipantesList, Map<String, Users> Asistentes) {
		List<String> Error = new ArrayList<String>();
		Integer altura = -1;
		Integer descripcionrm = -1;
		Integer riesgo_materializado_identi = -1;
		Integer causarm = -1;
		Integer consecuenciarm = -1;
		Integer impactocosto = -1;
		Integer impactotiempo = -1;
		Integer es_especialidad_id = -1;
		Integer descriptor = -1;
		Integer accion_desarrollar = -1;
		Integer responsable = -1;
		Integer ht_fechaplaneadacierre = -1;
		Integer observaciones = -1;

		switch (TipoEstudio) {
		case 7:
			altura = 13;
			descripcionrm = 2;
			riesgo_materializado_identi = 3;
			causarm = 4;
			consecuenciarm = 5;
			impactocosto = 6;
			impactotiempo = 7;
			es_especialidad_id = 8;
			descriptor = 9;
			accion_desarrollar = 10;
			responsable = 11;
			ht_fechaplaneadacierre = 12;
			observaciones = 13;
			break;
		}

		Sheet sheet1 = workbook.getSheetAt(3);
		Iterator<Row> rows = sheet1.iterator();
		List<er_HojaTrabajo> HojaTrabajoList = new ArrayList<er_HojaTrabajo>();
		List<String> TempError = new ArrayList<String>();
		try {
			int rowcount = 0;
			while (rows.hasNext()) {
				Row row = rows.next();
				boolean cerrado = false;
				if (rowcount > altura) {
					er_HojaTrabajo hojatrabajo = new er_HojaTrabajo();

					// Descripcion riesgo materializado
					boolean tempDesc = false;
					if (descripcionrm != -1) {
						TempError = getErrores(row, descripcionrm, 2000, "La descripción del riesgo materializado",
								rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(descripcionrm).getStringCellValue();
							hojatrabajo.setDescripcionrm(Temp);
						} else {
							if (row.getCell(descripcionrm).getCellType() == CellType.STRING) {
								Error.addAll(TempError);
							} else {
								tempDesc = true;
							}
						}
					}

					// Riesgo materializado identificado
					boolean tempIdenti = false;
					if (riesgo_materializado_identi != -1) {
						TempError = getErrores(row, riesgo_materializado_identi, 2000,
								"¿El riesgo materializado fue identificado en un taller de riesgos?", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(riesgo_materializado_identi).getStringCellValue();
							hojatrabajo.setRiesgo_materializado_identi(Temp);
						} else {
							if (row.getCell(riesgo_materializado_identi).getCellType() == CellType.STRING) {
								Error.addAll(TempError);
							} else {
								tempIdenti = true;
							}
						}
					}

					if (tempDesc == true && tempIdenti == false) {
						Error.add("La descripción del riesgo materializado está vacío FILA(" + (rowcount + 1) + ")");
					} else if (tempDesc == false && tempIdenti == true) {
						Error.add("¿El riesgo materializado fue identificado en un taller de riesgos? está vacío FILA("
								+ (rowcount + 1) + ")");
					} else if (tempDesc == true && tempIdenti == true) {
						if (rowcount == altura) {
							Error.add("La hoja de trabajo está vacía");
						}
						break;
					}

					// Observaciones
					if (observaciones != -1) {
						TempError = getErrores(row, observaciones, 2000, "La observación", rowcount);
						if (TempError.isEmpty())
						{
							String Temp = row.getCell(observaciones).getStringCellValue();

							if (Temp.startsWith("Cerrado") || Temp.startsWith("cerrado")) {
								cerrado = true;
								hojatrabajo.setEr_estado_id(3);
								hojatrabajo.setHt_fechacierre(encabezado.getE_fechataller());
								hojatrabajo.setEr_cierre_id(1);
							}
							hojatrabajo.setHt_observacionesacta(Temp);
						} else {
							if (row.getCell(observaciones).getCellType() == CellType.STRING) {
								Error.addAll(TempError);
							}
						}
					}
					
					// Causa de la materialización
					if (causarm != -1) {
						TempError = getErrores(row, causarm, 2000, "La causa de la materialización", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(causarm).getStringCellValue();
							hojatrabajo.setCausarm(Temp);
						} else {
							Error.addAll(TempError);
						}

					}

					// Consecuencia de la materialización
					if (consecuenciarm != -1) {
						TempError = getErrores(row, consecuenciarm, 2000, "La consecuencia de la materialización",
								rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(consecuenciarm).getStringCellValue();
							hojatrabajo.setConsecuenciarm(Temp);
						} else {
							Error.addAll(TempError);
						}

					}

					// Impacto en costo
					if (impactocosto != -1) {
						TempError = getErrores(row, impactocosto, 2000,
								"El impacto en costo de la materialización del riesgo", rowcount);
						if (TempError.isEmpty()) {
							Integer Temp = (int) row.getCell(impactocosto).getNumericCellValue();
							hojatrabajo.setImpactocosto(Temp);
						} else {
							Error.addAll(TempError);
						}

					}

					// Impacto en tiempo
					if (impactotiempo != -1) {
						TempError = getErrores(row, impactotiempo, 2000,
								"El impacto en tiempo de la materialización del riesgo", rowcount);
						if (TempError.isEmpty()) {
							Integer Temp = (int) row.getCell(impactotiempo).getNumericCellValue();
							hojatrabajo.setImpactotiempo(Temp);
						} else {
							Error.addAll(TempError);
						}

					}

					// Area de conocimiento / especialidad
					if (es_especialidad_id != -1) {
						TempError = getErrores(row, es_especialidad_id, 255, "El área de conocimiento", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(es_especialidad_id).getStringCellValue();
							erEspecialidad Tempo = ErEspecialidadRepository.findByEspecialidad(Temp);
							if (Tempo == null) {
								Error.add("Área de conocimiento Inválida. FILA (" + (rowcount + 1) + ")");
							} else {
								hojatrabajo.setEs_especialidad_id(Tempo.getEs_especialidad_id());
							}
						} else {
							if (cerrado == false) {
								Error.addAll(TempError);
							}
						}
					}

					// Descriptores
					if (descriptor != -1) {
						TempError = getErrores(row, descriptor, 2000, "Los descriptores o palabras clave", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(descriptor).getStringCellValue();
							hojatrabajo.setDescriptor(Temp);
						} else {
							Error.addAll(TempError);
						}

					}

					// Acción a desarrollar
					if (accion_desarrollar != -1) {
						TempError = getErrores(row, accion_desarrollar, 2000, "Acción desarrollada", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(accion_desarrollar).getStringCellValue();
							hojatrabajo.setAccion_desarrollar(Temp);
						} else {
							Error.addAll(TempError);
						}

					}

					// Responsable Implementación
					if (responsable != -1) {
						TempError = getErrores(row, responsable, 255, "El responsable de implementación", rowcount);
						if (TempError.isEmpty()) {
							String Temp = row.getCell(responsable).getStringCellValue();
							if (Temp.equals("N/A") || Temp.equals("N/A")) {
								hojatrabajo.setHt_responsableimplementacion(105);
							} else {
								if (Asistentes.containsKey(Temp)) {
									Users usuario = Asistentes.get(Temp);
									if (usuario.getRoles_id() != 1 && usuario.getRoles_id() != 2
											&& usuario.getRoles_id() != 3 && usuario.getRoles_id() != 5
											&& usuario.getRoles_id() != 6) {
										usuario.setRoles_id(4);
										userRepository.save(usuario);
									}
									hojatrabajo.setHt_responsableimplementacion(usuario.getId());
								} else {
									Error.add("El usuario \"" + Temp + "\"  en la FILA(" + (rowcount + 1)
											+ ")  no se encuentra en la lista de participantes");
								}
							}

						} else {
							Error.addAll(TempError);
						}

					}

					// Fecha
					if (ht_fechaplaneadacierre != -1) {
						TempError = getErrores(row, ht_fechaplaneadacierre, 255, "La fecha", rowcount);
						if (TempError.isEmpty()) {
							try {
								Date Temp = row.getCell(ht_fechaplaneadacierre).getDateCellValue();
								hojatrabajo.setHt_fechaplaneadacierre(Temp);
							} catch (Exception e) {
								Error.add("Formato de fecha incorrecto FILA(" + (rowcount + 1) + ")");
							}
						} else {
							Error.addAll(TempError);
						}
					}
					


					// End
					if (Error.isEmpty()) {
						HojaTrabajoList.add(hojatrabajo);
					}
				}

				rowcount++;
			}

		} catch (Exception e) {
			Error.add(
					"Error Inesperado, Revise la información de la hoja de trabajo, corrija e intente cargarla nuevamente.");
		}

		if (Error.isEmpty()) {
			Date ahora = new Date();
			encabezado.setAud_fechacreacion(ahora);
			encabezado.setEr_tipoestudio_id(TipoEstudio);
			ErEncabezadoRepository.save(encabezado);
			Integer encaid = encabezado.getEr_encabezado_id();

			for (erParticipantes participante : ParticipantesList) {
				participante.setEr_encabezado_id(encaid);
			}
			ErParticipantesRepository.saveAll(ParticipantesList);

			for (er_HojaTrabajo hojatrabajo : HojaTrabajoList) {
				hojatrabajo.setEr_encabezado_id(encaid);
				if (hojatrabajo.getEr_estado_id() == null) {
					hojatrabajo.setEr_estado_id(1);// 1 Abierto, 2 Cerrado.
				}
				if (hojatrabajo.getHt_fechaplaneadacierre() != null) {
					if (hojatrabajo.getHt_fechaplaneadacierre().compareTo(ahora) >= 0) {
						hojatrabajo.setEr_oportunidad_id(2);
					} else {
						hojatrabajo.setEr_oportunidad_id(1);
					}
				}else {
					hojatrabajo.setEr_oportunidad_id(2);
				}
			}
			Er_HojaTrabajoRepository.saveAll(HojaTrabajoList);

		} else {
			userRepository.deleteAll(usuariosTemporales);
			ErParticipantesRepository.deleteAll(ParticipantesList);
		}

		return Error;
	}

	// ALL
	public List<String> HojaTrabajo(Workbook workbook, erEncabezado encabezado, Integer TipoEstudio,
			List<Users> usuariosTemporales, List<erParticipantes> ParticipantesList, Map<String, Users> Asistentes) {
		List<String> Error = new ArrayList<String>();

		Integer altura = -1;
		Integer ht_pregunta = -1;
		Integer ht_evento = -1;
		Integer es_especialidad_id = -1;
		Integer er_matrizram_id = -1;
		Integer ht_salvaguardas = -1;
		Integer ra_rc_id = -1;
		Integer ra_rp_id = -1;
		Integer ra_rv_id = -1;
		Integer ht_recomendacion = -1;
		Integer ht_responsableimplementacion = -1;
		Integer ht_fechaplaneadacierre = -1;
		Integer rr_rc_id = -1;
		Integer rr_rp_id = -1;
		Integer rr_rv_id = -1;
		Integer ht_observacionesacta = -1;
		Integer ht_respuestaresp = -1;
		boolean allowfase = false;

		switch (TipoEstudio) {
		case 1:
			altura = 14;
			ht_pregunta = 2;
			ht_evento = 4;
			es_especialidad_id = 6;
			er_matrizram_id = 7;
			ht_salvaguardas = 8;
			ra_rc_id = 9;
			ra_rp_id = 10;
			ra_rv_id = 11;
			ht_recomendacion = 12;
			rr_rc_id = 15;
			rr_rp_id = 16;
			rr_rv_id = 17;
			ht_responsableimplementacion = 18;
			ht_fechaplaneadacierre = 20;
			ht_observacionesacta = 21;
			break;
		case 999:
			//Old Constructabilidad
			altura = 13;
			ht_evento = 2;
			es_especialidad_id = 4;
			er_matrizram_id = 6;
			ht_recomendacion = 7;
			ra_rc_id = 8;
			ra_rp_id = 9;
			ra_rv_id = 10;
			ht_responsableimplementacion = 11;
			ht_fechaplaneadacierre = 13;
			rr_rc_id = 14;
			rr_rp_id = 15;
			rr_rv_id = 16;
			ht_observacionesacta = 17;
			break;
		case 2:
		case 3:
		case 4:
			altura = 14;
			allowfase = true;
			ht_pregunta = 3;
			ht_respuestaresp = 5;
			es_especialidad_id = 6;
			ht_recomendacion = 7;
			er_matrizram_id = 8;
			ra_rc_id = 9;
			ra_rp_id = 10;
			ra_rv_id = 11;
			ht_responsableimplementacion = 12;
			ht_fechaplaneadacierre = 13;
			rr_rc_id = 14;
			rr_rp_id = 15;
			rr_rv_id = 16;
			ht_observacionesacta = 17;
			
			break;
		case 5:
			altura = 14;
			ht_evento = 2;
			es_especialidad_id = 4;
			er_matrizram_id = 5;
			ra_rc_id = 6;
			ra_rp_id = 7;
			ra_rv_id = 8;
			ht_recomendacion = 9;
			rr_rc_id = 12;
			rr_rp_id = 13;
			rr_rv_id = 14;
			ht_responsableimplementacion = 15;
			ht_fechaplaneadacierre = 17;
			ht_observacionesacta = 18;
			break;

		}

		Sheet sheet1 = workbook.getSheetAt(3);
		Iterator<Row> rows = sheet1.iterator();
		List<er_HojaTrabajo> HojaTrabajoList = new ArrayList<er_HojaTrabajo>();
		List<String> TempError = new ArrayList<String>();
		String fase = "";
		try {
			int rowcount = 0;
			while (rows.hasNext()) {
				Row row = rows.next();
				boolean cerrado = false;
				if (rowcount > altura) {
					er_HojaTrabajo hojatrabajo = new er_HojaTrabajo();

					boolean onFase = false;
					// Validación Cerrado Fase
					if (allowfase) {
						if (row.getCell(2).getCellType() == CellType.STRING) {

							String temp = row.getCell(2).getStringCellValue();
							if (temp.length() <= 255) {
								fase = temp;
							}
							onFase = true;
						} else if (row.getCell(2).getCellType() == CellType.NUMERIC) {
							onFase = false;
						} else {
							break;
						}
					}

					if (onFase == false) {
						if (allowfase) {
							hojatrabajo.setHt_fase(fase);
						}

						// Observaciones
						if (ht_observacionesacta != -1) {
							TempError = getErrores(row, ht_observacionesacta, 2000, "La observación", rowcount);
							
							if (TempError.isEmpty())
							{
								String Temp = row.getCell(ht_observacionesacta).getStringCellValue();

								if (Temp.startsWith("Cerrado") || Temp.startsWith("cerrado")) {
									cerrado = true;
									
									hojatrabajo.setEr_estado_id(3);
									hojatrabajo.setHt_fechacierre(encabezado.getE_fechataller());
									hojatrabajo.setEr_cierre_id(1);
								}
							
								hojatrabajo.setHt_observacionesacta(Temp);
							} else {
								if (row.getCell(ht_observacionesacta).getCellType() == CellType.STRING) {
									Error.addAll(TempError);
								}
							}
						}
						// Pregunta
						boolean tempPregunta = false;
						if (ht_pregunta != -1) {
							TempError = getErrores(row, ht_pregunta, 2000, "La pregunta", rowcount);
							if (TempError.isEmpty()) {
								String Temp = row.getCell(ht_pregunta).getStringCellValue();							
								hojatrabajo.setHt_pregunta(Temp);
							} else {
								if (row.getCell(ht_pregunta).getCellType() == CellType.STRING) {
									Error.addAll(TempError);
								} else {
									if (TipoEstudio == 1 || allowfase) {
										tempPregunta = true;
									}
								}
							}
						}

						// Respuesta del responsable
						boolean tempRespuesta = false;
						if (ht_respuestaresp != -1) {
							TempError = getErrores(row, ht_respuestaresp, 2000, "La respuesta del responsable",
									rowcount);
							if (TempError.isEmpty()) {
								String Temp = row.getCell(ht_respuestaresp).getStringCellValue();								
								hojatrabajo.setHt_respuestaresp(Temp);
							} else {
								if (row.getCell(ht_respuestaresp).getCellType() == CellType.STRING) {
									Error.addAll(TempError);
								} else {
									if (allowfase) {
										tempRespuesta = true;
									}
								}
							}
						}

						// Evento Identificado
						boolean tempEvento = false;
						if (ht_evento != -1) {
							TempError = getErrores(row, ht_evento, 2000, "El evento identificado", rowcount);
							if (TempError.isEmpty()) {
								String Temp = row.getCell(ht_evento).getStringCellValue();
								hojatrabajo.setHt_evento(Temp);
							} else {
								if (row.getCell(ht_evento).getCellType() == CellType.STRING) {
									Error.addAll(TempError);
								} else {
									if (TipoEstudio == 1 || TipoEstudio == 2 || TipoEstudio == 5) {
										tempEvento = true;
									}
								}
							}
						}

						// Especialidad
						boolean tempEspecialidad = false;
						if (es_especialidad_id != -1) {
							TempError = getErrores(row, es_especialidad_id, 255, "La especialidad", rowcount);
							if (TempError.isEmpty()) {
								erEspecialidad TempEs = null;

								if (row.getCell(es_especialidad_id).getCellType() == CellType.STRING) {
									String Temp = row.getCell(es_especialidad_id).getStringCellValue();
									TempEs = ErEspecialidadRepository.findByEspecialidad(Temp);

								} else if (row.getCell(es_especialidad_id).getCellType() == CellType.NUMERIC) {

									Integer Consecuencias = (int) row.getCell(ra_rc_id).getNumericCellValue();
									String Temp = "" + Consecuencias;
									TempEs = ErEspecialidadRepository.findByEspecialidad(Temp);
								}
								if (TempEs == null) {
									Error.add("Especialidad inválida. FILA (" + (rowcount + 1) + ")");
								} else {									
									hojatrabajo.setEs_especialidad_id(TempEs.getEs_especialidad_id());
								}
							} else {
								if (row.getCell(es_especialidad_id).getCellType() == CellType.STRING) {
									if (cerrado == false) {
										Error.addAll(TempError);
									}
								} else {

									tempEspecialidad = true;

								}

							}
						}

						// Validación Límite
						if ((tempPregunta == true && tempRespuesta == true)
								|| (tempPregunta == true && tempEvento == true)
								|| (tempEvento == true && tempEspecialidad == true)) {
							if (rowcount == altura) {
								Error.add("La hoja de trabajo está vacía");
							}
							break;
						} else {
							if (allowfase) {
								if (tempPregunta == true && tempRespuesta == false) {
									Error.add("Pregunta vacía FILA(" + (rowcount + 1) + ")");
								} else if (tempPregunta == false && tempRespuesta == true) {
									if (cerrado == false) {
										Error.add("Respuesta del responsable vacía. FILA(" + (rowcount + 1) + ")");
									}
								}
							} else if (TipoEstudio == 1) {
								if (tempPregunta == true && tempEvento == false) {
									Error.add("Pregunta vacía FILA(" + (rowcount + 1) + ")");
								} else if (tempPregunta == false && tempEvento == true) {
									Error.add("Evento vacío FILA(" + (rowcount + 1) + ")");
								}
							} else if (TipoEstudio == 2 || TipoEstudio == 5) {
								if (tempEspecialidad == true && tempEvento == false) {
									Error.add("Especialidad vacía FILA(" + (rowcount + 1) + ")");
								} else if (tempEspecialidad == false && tempEvento == true) {
									Error.add("Evento vacío FILA(" + (rowcount + 1) + ")");
								}
							}

						}

						// Riesgo Actual
						// ValoracionRAM
						if (ra_rv_id != -1) {
							TempError = getErrores(row, ra_rv_id, 255, "Riesgo actual ValoracionRAM", rowcount);
							if (TempError.isEmpty()) {
								erRiesgos_Valoracion TempRies = null;
								if (row.getCell(ra_rv_id).getCellType() == CellType.STRING) {
									String valoracion = row.getCell(ra_rv_id).getStringCellValue();
									TempRies = ErRiesgos_ValoracionRepository.findByValoracion(valoracion);
								} else if (row.getCell(ra_rv_id).getCellType() == CellType.NUMERIC) {

									Integer valoracion = (int) row.getCell(ra_rv_id).getNumericCellValue();
									String valoracio = "" + valoracion;
									TempRies = ErRiesgos_ValoracionRepository.findByValoracion(valoracio);
								} else if (row.getCell(ra_rv_id).getCellType() == CellType.FORMULA) {
									try {
										String valoracion = row.getCell(ra_rv_id).getStringCellValue();
										TempRies = ErRiesgos_ValoracionRepository.findByValoracion(valoracion);
									} catch (Exception e) {

								}
								}

								if (TempRies != null) {
									hojatrabajo.setRa_rv_id(TempRies.getRv_id());
									if(TempRies.getRv_id() == 1) {
										cerrado = true;
									}
								} else {
									if (cerrado == false) {
										Error.add(
												"Riesgo actual ValoracionRAM inválido. FILA (" + (rowcount + 1) + ")");
									}
								}
							} else {
								if (cerrado == false) {
									Error.addAll(TempError);
								}
							}
						}

						// Recomendacion
						if (ht_recomendacion != -1) {
							TempError = getErrores(row, ht_recomendacion, 2000, "La recomendación", rowcount);
							if (TempError.isEmpty()) {
								String Temp = row.getCell(ht_recomendacion).getStringCellValue();
								hojatrabajo.setHt_recomendacion(Temp);
							} else {
								if (cerrado == false) {
									Error.addAll(TempError);
								}
								
							}

						}

						// Responsable Implementación
						if (ht_responsableimplementacion != -1) {
							TempError = getErrores(row, ht_responsableimplementacion, 255,
									"El responsable de implementación", rowcount);
							if (TempError.isEmpty()) {
								String Temp = row.getCell(ht_responsableimplementacion).getStringCellValue();
								if (Temp.equals("N/A") || Temp.equals("N/A")) {
									if (cerrado == false) {
										Error.add("Debe haber un responsable de cierre en la FILA(" + (rowcount + 1)
												+ ")");
									} else {
										// Usuario N/A = 105
										hojatrabajo.setHt_responsableimplementacion(105);
									}
								} else {

									if (Asistentes.containsKey(Temp)) {
										Users usuario = Asistentes.get(Temp);
										if (usuario.getRoles_id() != 1 && usuario.getRoles_id() != 2
												&& usuario.getRoles_id() != 3 && usuario.getRoles_id() != 5
												&& usuario.getRoles_id() != 6) {
											usuario.setRoles_id(4);
											userRepository.save(usuario);
										}
										hojatrabajo.setHt_responsableimplementacion(usuario.getId());
									} else {
										Error.add("El usuario \"" + Temp + "\"  en la FILA(" + (rowcount + 1)
												+ ")  no se encuentra en la lista de participantes");
									}
								}

							} else {
								if (cerrado == false) {
									Error.addAll(TempError);
								} else {
									// Usuario N/A = 105
									hojatrabajo.setHt_responsableimplementacion(105);
								}
							}

						}

						// Fecha Planeada de cierre
						if (ht_fechaplaneadacierre != -1) {
							TempError = getErrores(row, ht_fechaplaneadacierre, 255, "La fecha planeada de cierre",
									rowcount);
							if (TempError.isEmpty()) {
								try {
									Date Temp = row.getCell(ht_fechaplaneadacierre).getDateCellValue();		
									hojatrabajo.setHt_fechaplaneadacierre(Temp);
								} catch (Exception e) {
									Error.add("Formato de fecha incorrecto FILA(" + (rowcount + 1) + ")");
								}
							} else {
								if (cerrado == false) {
									Error.addAll(TempError);
								} else {
									hojatrabajo.setGap(0);
									hojatrabajo.setEr_oportunidad_id(4);
								}
							}
						}

						// Salvaguardas
						if (ht_salvaguardas != -1) {
							TempError = getErrores(row, ht_salvaguardas, 2000, "El salvaguardas", rowcount);
							if (TempError.isEmpty()) {
								String Temp = row.getCell(ht_salvaguardas).getStringCellValue();
								hojatrabajo.setHt_salvaguardas(Temp);
							} else {
								if (row.getCell(ht_salvaguardas).getCellType() == CellType.STRING) {
									Error.addAll(TempError);
								}

							}
						}

						// MatrizRAM
						if (er_matrizram_id != -1) {
							TempError = getErrores(row, er_matrizram_id, 255, "La Matriz seleccionada", rowcount);
							if (TempError.isEmpty()) {
								String Temp = row.getCell(er_matrizram_id).getStringCellValue();
								erMatrizRAM Tempo = ErMatrizRAMRepository.findByMatriz(Temp);
								if (Tempo == null) {
									Error.add("Matriz Inválida. FILA (" + (rowcount + 1) + ")");
								} else {
									hojatrabajo.setEr_matrizram_id(Tempo.getEr_matriz_id());
								}
							} else {
								if (cerrado == false) {
									Error.addAll(TempError);
								}
							}
						}

						// Riesgo Actual
						// Consecuencias
						if (ra_rc_id != -1) {
							TempError = getErrores(row, ra_rc_id, 255, "Riesgo actual consecuencias", rowcount);
							if (TempError.isEmpty()) {
								erRiesgos_Consecuencias TempRies = null;
								if (row.getCell(ra_rc_id).getCellType() == CellType.STRING) {
									String Temp = row.getCell(ra_rc_id).getStringCellValue();
									TempRies = ErRiesgos_ConsecuenciasRepository.findByValoracion(Temp);

								} else if (row.getCell(ra_rc_id).getCellType() == CellType.NUMERIC) {

									Integer Consecuencias = (int) row.getCell(ra_rc_id).getNumericCellValue();
									String Consecuencia = "" + Consecuencias;
									TempRies = ErRiesgos_ConsecuenciasRepository.findByValoracion(Consecuencia);
								}

								if (TempRies != null) {
									hojatrabajo.setRa_rc_id(TempRies.getRc_id());
								} else {
									Error.add("Riesgo actual consecuencias inválido. FILA (" + (rowcount + 1) + ")");
								}
							} else {
								if (cerrado == false) {
									Error.addAll(TempError);
								}
							}
						}

						// Riesgo Actual
						// Probabilidad
						if (ra_rp_id != -1) {
							TempError = getErrores(row, ra_rp_id, 255, "Riesgo actual probabilidad", rowcount);
							if (TempError.isEmpty()) {
								erRiesgos_Probabilidad TempRies = null;
								if (row.getCell(ra_rp_id).getCellType() == CellType.STRING) {
									String Probabilidad = row.getCell(ra_rp_id).getStringCellValue();
									TempRies = ErRiesgos_ProbabilidadRepository.findByValoracion(Probabilidad);
								} else if (row.getCell(ra_rp_id).getCellType() == CellType.NUMERIC) {

									Integer Probabilidad = (int) row.getCell(ra_rp_id).getNumericCellValue();
									String Probabilida = "" + Probabilidad;
									TempRies = ErRiesgos_ProbabilidadRepository.findByValoracion(Probabilida);
								}

								if (TempRies != null) {
									hojatrabajo.setRa_rp_id(TempRies.getRp_id());
								} else {
									Error.add("Riesgo actual probabilidad inválido. FILA (" + (rowcount + 1) + ")");
								}
							} else {
								if (cerrado == false) {
									Error.addAll(TempError);
								}
							}

						}

						// Riesgo Residual
						// Consecuencias
						if (rr_rc_id != -1) {
							TempError = getErrores(row, rr_rc_id, 255, "Riesgo residual consecuencias", rowcount);
							if (TempError.isEmpty()) {
								erRiesgos_Consecuencias TempRies = null;
								if (row.getCell(rr_rc_id).getCellType() == CellType.STRING) {
									String Temp = row.getCell(rr_rc_id).getStringCellValue();
									TempRies = ErRiesgos_ConsecuenciasRepository.findByValoracion(Temp);

								} else if (row.getCell(rr_rc_id).getCellType() == CellType.NUMERIC) {

									Integer Consecuencias = (int) row.getCell(rr_rc_id).getNumericCellValue();
									String Consecuencia = "" + Consecuencias;
									TempRies = ErRiesgos_ConsecuenciasRepository.findByValoracion(Consecuencia);
								}

								if (TempRies != null) {
									hojatrabajo.setRr_rc_id(TempRies.getRc_id());
								} else {
									Error.add("Riesgo actual consecuencias inválido. FILA (" + (rowcount + 1) + ")");
								}
							} else {
								if (cerrado == false) {
									Error.addAll(TempError);
								}
							}
						}

						// Riesgo Residual
						// Probabilidad
						if (rr_rp_id != -1) {
							TempError = getErrores(row, rr_rp_id, 255, "Riesgo residual probabilidad", rowcount);
							if (TempError.isEmpty()) {
								erRiesgos_Probabilidad TempRies = null;
								if (row.getCell(rr_rp_id).getCellType() == CellType.STRING) {
									String Probabilidad = row.getCell(rr_rp_id).getStringCellValue();
									TempRies = ErRiesgos_ProbabilidadRepository.findByValoracion(Probabilidad);
								} else if (row.getCell(rr_rp_id).getCellType() == CellType.NUMERIC) {

									Integer Probabilidad = (int) row.getCell(rr_rp_id).getNumericCellValue();
									String Probabilida = "" + Probabilidad;
									TempRies = ErRiesgos_ProbabilidadRepository.findByValoracion(Probabilida);
								}
								if (TempRies != null) {
									hojatrabajo.setRr_rp_id(TempRies.getRp_id());
								} else {
									Error.add("Riesgo actual probabilidad inválido. FILA (" + (rowcount + 1) + ")");
								}
							} else {
								if (cerrado == false) {
									Error.addAll(TempError);
								}
							}

						}

						// Riesgo Residual
						// ValoracionRAM
						if (rr_rv_id != -1) {
							TempError = getErrores(row, rr_rv_id, 255, "Riesgo residual ValoracionRAM", rowcount);
							if (TempError.isEmpty()) {
								erRiesgos_Valoracion TempRies = null;
								if (row.getCell(rr_rv_id).getCellType() == CellType.STRING) {
									String valoracion = row.getCell(rr_rv_id).getStringCellValue();
									TempRies = ErRiesgos_ValoracionRepository.findByValoracion(valoracion);
								} else if (row.getCell(rr_rv_id).getCellType() == CellType.NUMERIC) {

									Integer valoracion = (int) row.getCell(rr_rv_id).getNumericCellValue();
									String valoracio = "" + valoracion;
									TempRies = ErRiesgos_ValoracionRepository.findByValoracion(valoracio);
								} else if (row.getCell(rr_rv_id).getCellType() == CellType.FORMULA) {

									try {
										String valoracion = row.getCell(rr_rv_id).getStringCellValue();
										TempRies = ErRiesgos_ValoracionRepository.findByValoracion(valoracion);
									} catch (Exception e) {

									}
								}

								if (TempRies != null) {
									hojatrabajo.setRr_rv_id(TempRies.getRv_id());
								} else {
									if (cerrado == false) {
										Error.add("Riesgo residual ValoracionRAM inválido. FILA (" + (rowcount + 1)
												+ ")");
									}
								}
							} else {
								if (cerrado == false) {
									Error.addAll(TempError);
								}
							}
						}

						// End
						if (Error.isEmpty()) {
							HojaTrabajoList.add(hojatrabajo);
						}
					}
				}

				rowcount++;
			}

			if (rowcount == 0 || rowcount <= 13) {
				Error.add("La Hoja de Trabajo está vacía o tiene datos erroneos.");
			}

		} catch (Exception e) {
			Error.add(
					"Error Inesperado, Revise la información de la hoja de trabajo, corrija e intente cargarla nuevamente.");
		}


		if (Error.isEmpty()) {
			Date ahora = new Date();
			encabezado.setAud_fechacreacion(ahora);
			encabezado.setEr_tipoestudio_id(TipoEstudio);
			ErEncabezadoRepository.save(encabezado);
			Integer encaid = encabezado.getEr_encabezado_id();

			for (erParticipantes participante : ParticipantesList) {
				participante.setEr_encabezado_id(encaid);
			}
			ErParticipantesRepository.saveAll(ParticipantesList);

			for (er_HojaTrabajo hojatrabajo : HojaTrabajoList) {
				hojatrabajo.setEr_encabezado_id(encaid);
				if (hojatrabajo.getEr_estado_id() == null) {
					hojatrabajo.setEr_estado_id(1);// 1 Abierto, 2 Cerrado.
				}
				if (hojatrabajo.getHt_fechaplaneadacierre() != null) {
					if (hojatrabajo.getHt_fechaplaneadacierre().compareTo(ahora) >= 0) {
						hojatrabajo.setEr_oportunidad_id(2);
					} else {
						hojatrabajo.setEr_oportunidad_id(1);
					}
				}else {
					hojatrabajo.setEr_oportunidad_id(2);
				}
			}
			Er_HojaTrabajoRepository.saveAll(HojaTrabajoList);

		} else {
			userRepository.deleteAll(usuariosTemporales);
			ErParticipantesRepository.deleteAll(ParticipantesList);
		}
		return Error;
	}

	// GetErrores
	public List<String> getErrores(Row row, Integer num, Integer limite, String NomCol, Integer rowcount) {
		List<String> Error = new ArrayList<String>();
		CellType Tipo = row.getCell(num).getCellType();
		
		if (Tipo == CellType.NUMERIC) {
			/*	Date temporalDate = row.getCell(num).getDateCellValue();
				if (temporalDate == null) {
				Error.add("Formato de " + NomCol + " incorrecto o está vacío FILA(" + (rowcount + 1) + ")");
			}*/
		} else if (Tipo == CellType.STRING) {
			String Temp = row.getCell(num).getStringCellValue();
			if (Temp == null || Temp.equals("") || Temp.equals(" ")) {
				Error.add(NomCol + " está vacío FILA(" + (rowcount + 1) + ")");
			}
			if (Temp.length() > limite) {
				Error.add(NomCol + " excede el límite de caracteres permitidos [" + limite + " FILA(" + (rowcount + 1)
						+ ")");
			}

		} else if (Tipo == CellType.FORMULA) {

		} else {
			Error.add("Formato de " + NomCol + " incorrecto o está vacío FILA(" + (rowcount + 1) + ")");
		}

		return Error;
	}

	public boolean esEmail(String email) {
		if (email == null) {
			email = "";
		}
		if (!email.equals("")) {
			Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
			Matcher mat = pattern.matcher(email);
			if (mat.matches()) {
				return true;
			}
		}
		return false;
	}
}

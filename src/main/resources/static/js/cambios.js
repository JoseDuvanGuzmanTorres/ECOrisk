$(document).ready(function() {
//	$('#a').val('');
//	$('#aDue').val('');
	$('.customselect').select2();
	$('.customselectf2').select2();

	
	//Hoja Trabajo
	$('#hojatrabajo-table thead tr').clone(true).appendTo('#hojatrabajo-table thead');
	$('#hojatrabajo-table thead tr:eq(1) th').each(function(i) {
		var title = $(this).text();
		$(this).html('<input type="text" placeholder="Buscar por ' + title + '" />');

		$('input', this).on('keyup change', function() {
			if (Mastertable.column(i).search() !== this.value) {
				Mastertable
					.column(i)
					.search(this.value)
					.draw();
			}
		});
	});

	var Mastertable = $('#hojatrabajo-table').DataTable({
		orderCellsTop: true,
		language: {
			"lengthMenu": "Mostrar _MENU_ registros",
			"zeroRecords": "No se encontraron resultados",
			"info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
			"infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
			"infoFiltered": "(filtrado de un total de _MAX_ registros)",
			"sSearch": "Búsqueda global en filtro actual:",
			"oPaginate": {
				"sFirst": "Primero",
				"sLast": "Último",
				"sNext": "Siguiente",
				"sPrevious": "Anterior"
			},
			"sProcessing": "Procesando...",
		},
		//para usar los botones  
		"scrollX": true,
		dom: 'Bfrtilp',
		columnDefs: [
			{
				targets: 1,
				className: 'noVis'
			}
		],
		buttons: [
			{
				extend: 'colvis',
				columns: ':not(.noVis)',
				postfixButtons: ['colvisRestore']
			},
			{
				extend: 'excelHtml5',
				text: '<i class="fas fa-file-excel"></i> ',
				titleAttr: 'Exportar a Excel',
				className: 'btn btn-success',
				exportOptions: {
					columns: ':visible'
				}
			},
			{
				extend: 'print',
				text: '<i class="fa fa-print"></i> ',
				titleAttr: 'Imprimir',
				className: 'btn btn-info'
			},
		]
	});

	
	//Fin Hoja Design Review
	//Crea Tabla de solicitud cambios usando AJAX
	$('#hojatrabajo-table').on('click', '.add-cambios', function(event) {
		event.preventDefault();

		var href = $(this).attr('href');
		var idhoja = $(this).attr('id');
		$('#open_hojatrabajo').val(idhoja);
		var table = $('#hojacambios-table').DataTable({
			"searching": true,
			language: {
				"lengthMenu": "Mostrar _MENU_ registros",
				"zeroRecords": "No se encontraron cambios registrados",
				"info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
				"infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
				"infoFiltered": "(filtrado de un total de _MAX_ registros)",
				"sSearch": "Buscar:",
				"oPaginate": {
					"sFirst": "Primero",
					"sLast": "Último",
					"sNext": "Siguiente",
					"sPrevious": "Anterior"
				},
				"sProcessing": "Procesando...",
			},
			columnDefs: [
				{
					targets: 4, render: function(data) {
						if (data != null) {
							return moment(data).format('YYYY-MM-DD <br> hh:mm');
						} else {
							return "";
						}
						
					}
				},
				{
					targets: 5, render: function(data) {
						if (data != null) {
							return moment(data).format('YYYY-MM-DD <br> hh:mm');
						} else {
							return "";
						}

					}
				}, 
			],
			clear: true,
			destroy: true,
			"ajax": {
				"method": "POST",
				"url": href,
				"dataSrc": ""
			},
			"columns": [
				{ data: "comentario" },
				{ data: "viejo" },
				{ data: "nuevo" },
				{ data: "vieja" },
				{ data: "nueva" },
				{data: "ruta",
                render: function(data, type, row, meta){
		           	if(type === 'display'){
		            		data = '';
		            		if(row.archi1 != null){
		            			data = data + '<a href="' + (row.ruta+row.archi1) + '"download >' + row.archi1 + '</a>'
		            		}
		            		if(row.archi2 != null){
		            			data = data + '<br><a href="' + (row.ruta+row.archi2) + '"download >' + row.archi2 + '</a>'
		            		}
		            		if(row.archi3 != null){
		            			data = data + '<br><a href="' + (row.ruta+row.archi3) + '"download >' + row.archi3 + '</a>'
		            		}
		            }
		            if(row.ruta !== ""){
		            	return data;
		            }
		            
		         }
		         ,defaultContent: ""
			    },
			    { data: "responsable" },
			    
			]
		});
		$('#hojacambios-table').wrap('<div class="dataTables_scroll" style="overflow:auto;" />');

		$('#editModal').modal();
	});
	
		$('#addChange').submit(function(e){                         
	    e.preventDefault();
	    comentario = $.trim($('#comentario').val());
	    if(comentario == ""){
	    	$(".insertar_alertas" ).append( '<div class="alert alert-danger insertados"><strong>No puede agregar un comentario vacío</strong></div>');
	    }else{
	    	files = $('#archivo').get(0).files;
	    	if(files.length <4){
			    user = $.trim($('#responsable_nuevo').val());
			    hojatra = $.trim($('#ht_id').val());
			    var formData = new FormData(this);
			        $.ajax({
			          type: "POST",
			          url: "/hojatrabajo/update",
			          enctype: "multizpart/form-data",
			          datatype:"json",
			          data: formData,
			          success: function (data) {
				            alert(data)
				      },
				      cache: false,
				      contentType: false,
				      processData: false
		        });
		         $('#comentario').val("");
			     $('#archivo').val("");
			     $('#editModal').modal('toggle');
		     }else{
		     	$(".insertar_alertas" ).append( '<div class="alert alert-danger insertados"><strong>No se pueden agregar más de 3 archivos</strong></div>');
		     	$('#archivo').val("");
		     }
	     }     
	    //$('#hojatrabajo-table').DataTable().ajax.reload();							     			
	});
	
	$('#cambiosrf').show();
	
	$('#archivo').change(function (){
      	var fileName = $('#archivo').val();
      	var files = $('#archivo').get(0).files;
      	if(fileName) {
      		var ext = $('#archivo').val().split('.').pop().toLowerCase();
			if($.inArray(ext, ['xlsx','xls','doc','docx','ppt','pptx','mpp','pdf']) == -1) {
			    alert('Extensión de archivo no permitida');
			    if(files.length > 3){
			    	alert ("No se pueden agregar más de 3 archivos");
			    }
			    $(this).val("");
			//    $('#cambiosrf').hide();
	      	//	$('#cambiosdes').val("1");
			}else{
			
	      		if(files.length < 4){
	      	//		$('#cambiosrf').show();
	      		}else{
	      			alert ("No se pueden agregar más de 3 archivos");
	      			$(this).val("");
	      	//		$('#cambiosrf').hide();
	      			$('#cambiosdes').val("1");
	      		}
	      	}
      	}else{
      	//	$('#cambiosrf').hide();
      	//	$('#cambiosdes').val("1");
      		
      	}
      	
      	
     });
     
     var tipocambio = $('#cambiosdes').val();
     if(tipocambio == 1){
     	$('#cambiofe').show();
     	$('#cambiore').hide();
     	document.getElementById("responsable_nuevo").disabled = true;
     }
     
     $('#cambiosdes').change(function (){
     	var tipocambio = $('#cambiosdes').val();
     	if(tipocambio == 1){
	    	$('#cambiofe').show();
			document.getElementById("fecha_nueva").disabled = false;
			document.getElementById("responsable_nuevo").disabled = true;
	    	$('#cambiore').hide();
	    }else if (tipocambio == 2){
	    	$('#cambiofe').hide();
	   		document.getElementById("fecha_nueva").disabled = true;
	    	document.getElementById("responsable_nuevo").disabled = false;
	    	$('#cambiore').show();
	    }else if (tipocambio == 3){
	    	$('#cambiofe').show();
	    	$('#cambiore').show();
	    	document.getElementById("responsable_nuevo").disabled = false;
	    	document.getElementById("responsable_nuevo").disabled = false;
	    }
     	
     });
     



//	$('#hojacambios-table').on('click', '.select-changes', function(event) {
	//	$('#id-solicitud').val($(this).attr('id'));
		//$('#id-solicitud').val
		//$('#id-solicitud').show();
		//$('#id-solicitud-t').show();
	//});
	
	
	//ajax fuera de las funciones
	var user_id = $('#de').val();
    $.ajax({
          type: "GET",
          url: "/projection/encabezadoResponsableT?responsable="+user_id,
          datatype:"json",
          success: function (result) {
	            var s = '<option value="0">Todos los talleres</option>';
	            for(var i=0; i<result.length; i++){
	            	s += '<option value="' + result[i].id + '"> [' + result[i].tipo + '] (' + result[i].coddoc + ') ' + result[i].nompro + '</option>';
	            }
	           $('#cuales').html(s);           
	      }
    });
    	var user_id = $('#de').val();
    		    $.ajax({
		          type: "GET",
		          url: "/projection/encabezadoDue?dueno="+user_id,
		          datatype:"json",
		          success: function (result) {
			            var s = '<option value="0">Todos los talleres</option>';
			            for(var i=0; i<result.length; i++){
			            	s += '<option value="' + result[i].id + '"> [' + result[i].tipo + '] (' + result[i].coddoc + ') ' + result[i].nompro + '</option>';
			            }
			           $('#cualesDue').html(s);
			           
			      }
	        });
    

	var soli = "Solicitud de cambio de responsable de ";
    var de = $('#de').select2('data')[0].text;
    var a = '';
    //var a = $('#a').select2('data')[0].text;
   	$('#desc').val(soli + de +" a "+ a);
    
    var soliDue = "Solicitud de cambio de lider/dueño taller de ";
    var deDue = $('#deDue').select2('data')[0].text;
    var aDue = '';
    //var aDue = $('#aDue').select2('data')[0].text;
    $('#descDue').val(soliDue + deDue +" a "+ aDue);
    
	$('#de').change(function(){ 
		var deVal = $('#de').val();
		var aVal = $('#a').val();
		if(deVal == aVal){
			alert("No se puede seleccionar al mismo usuario para el cambio");
	//		$('#de').val('');
			$('#de').select2();
		}else{
		                      
		    var user_id = $(this).val();
		    de = $('#de').select2('data')[0].text;
	        a = $('#a').select2('data')[0].text;
	        
	        $('#desc').val(soli + de +" a "+ a); 
		    $.ajax({
		          type: "GET",
		          url: "/projection/encabezadoResponsableT?responsable="+user_id,
		          datatype:"json",
		          success: function (result) {
			            var s = '<option value="0">Todos los talleres</option>';
			            for(var i=0; i<result.length; i++){
			            	s += '<option value="' + result[i].id + '"> [' + result[i].tipo + '] (' + result[i].coddoc + ') ' + result[i].nompro + '</option>';
			            }
			           $('#cuales').html(s);
			           
			      }
	        });
	    }
        
	   						     			
	});
	
	$('#a').change(function(){
	
		var deVal = $('#de').val();
		var aVal = $('#a').val();
		if(deVal == aVal){
			alert("No se puede seleccionar al mismo usuario para el cambio");
		//	$('#a').val('');
			$('#a').select2();
		}else{ 
			de = $('#de').select2('data')[0].text;
	        a = $('#a').select2('data')[0].text;
	        
	        $('#desc').val(soli + de +" a "+ a); 
		}
	});
	
	$('#cuales').on('select2:select', function (e) {
		 var todos =  $(this).val();
		 if( $.inArray("0", todos) !== -1 ) {
			$(this).val("0").trigger("change");
			if (todos.length > 1){
				alert("Usted seleccionó la opción de todos los talleres");
			}
			
		 }
	});
	
	$('#deDue').change(function(){
	
		var deVal = $('#deDue').val();
		var aVal = $('#aDue').val();
		if(deVal == aVal){
			alert("No se puede seleccionar al mismo usuario para el cambio");
		//	$('#deDue').val('');
			$('#deDue').select2();
		}else{
		                         
		    var user_id = $(this).val();
		    deDue = $('#deDue').select2('data')[0].text;
	        aDue = $('#aDue').select2('data')[0].text;
	        
	        $('#descDue').val(soliDue + deDue +" a "+ aDue); 
		    $.ajax({
		          type: "GET",
		          url: "/projection/encabezadoDue?dueno="+user_id,
		          datatype:"json",
		          success: function (result) {
			            var s = '<option value="0">Todos los talleres</option>';
			            for(var i=0; i<result.length; i++){
			            	s += '<option value="' + result[i].id + '"> [' + result[i].tipo + '] (' + result[i].coddoc + ') ' + result[i].nompro + '</option>';
			            }
			           $('#cualesDue').html(s);
			           
			      }
	        });
        }
	   						     			
	});
	
	$('#cualesDue').on('select2:select', function (e) {
		 var todos =  $(this).val();
		 if( $.inArray("0", todos) !== -1 ) {
			$(this).val("0").trigger("change");
			if (todos.length > 1){
				alert("Usted seleccionó la opción de todos los talleres");
			}
			
		 }
	});
	
	$('#aDue').change(function(){
	
		var deVal = $('#deDue').val();
		var aVal = $('#aDue').val();
		if(deVal == aVal){
			alert("No se puede seleccionar al mismo usuario para el cambio");
		//	$('#aDue').val('');
			$('#aDue').select2();
		}else{
			deDue = $('#deDue').select2('data')[0].text;
	        aDue = $('#aDue').select2('data')[0].text;
	        
	        $('#descDue').val(soli + deDue +" a "+ aDue); 
		}
	});
	
});
$(document).ready(function() {
	$('#a').val('');
	$('#aDue').val('');
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
		$('#open_hojatrabajo2').val(idhoja);
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
					targets: 3, render: function(data) {
						if (data != null) {
							return moment(data).format('YYYY-MM-DD <br> hh:mm');
						} else {
							return "";
						}
						
					}
				},
				{
					targets: 4, render: function(data) {
						if (data != null) {
							return moment(data).format('YYYY-MM-DD <br> hh:mm');
						} else {
							return "";
						}

					}
				}
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
			    $('#cambiosrf').hide();
	      		$('#cambiosdes').val("1");
			}else{
			
	      		if(files.length < 4){
	      			$('#cambiosrf').show();
	      		}else{
	      			alert ("No se pueden agregar más de 3 archivos");
	      			$(this).val("");
	      			$('#cambiosrf').hide();
	      			$('#cambiosdes').val("1");
	      		}
	      	}
      	}else{
      		$('#cambiosrf').hide();
      		$('#cambiosdes').val("1");
      		
      	}
      	     	
     });
     var tipocambio = $('#cambiosdes').val();
     if(tipocambio == 1){
     	$('#cambiofe').show();
     	$('#cambiore').hide();
     }
     $('#cambiosdes').change(function (){
     	var tipocambio = $('#cambiosdes').val();
     	if(tipocambio == 1){
	    	$('#cambiofe').show();
	    	$('#cambiore').hide();
	    }else if (tipocambio == 2){
	    	$('#cambiofe').hide();
	    	$('#cambiore').show();
	    }
     	
     });
     

	$('#addChanges').submit(function(e) {
		e.preventDefault();
		console.log($('#razon').val());
		console.log($('#aprobacion').val());
		console.log($('#id-solicitud').val());

		razon = $.trim($('#razon').val());
		if (razon == "") {
			alert("No puede agregar una razón vacía");
		} else {
			var form_data = $(this).serialize();
			$.ajax({
				url: "/hojacambios/modify",
				type: "POST",
				data: form_data
			}).done(function(response) { //
				alert(response);
			});
			$('#razon').val("");
			$('#changesModal').modal('toggle');
			$('#id-solicitud').show();
			$('#id-solicitud-t').hide();
			$('#id-solicitud').hide();
		}

		//$('#hojatrabajo-table').DataTable().ajax.reload();							     			
	});

	$('#hojacambios-table').on('click', '.select-changes', function(event) {
		$('#id-solicitud').val($(this).attr('id'));
		$('#id-solicitud').val
		$('#id-solicitud').show();
		$('#id-solicitud-t').show();
	});
	
	var valor = $('#de').val();
    $.ajax({
          type: "GET",
          url: "/projection/encabezado?proyecto="+valor,
          datatype:"json",
          success: function (result) {
	            var s = '<option value="0">Todos los talleres</option>';
	            
	            
	            for(var i=0; i<result.length; i++){
	            	s += '<option value="' + result[i].id + '"> [' + result[i].tipo + '] (' + result[i].coddoc + ') ' + result[i].nompro + '</option>';
	            }
	           $('#cuales').html(s);
	           
	      }
    });
	
	var soli = "Cierre automático  de controles en el proyecto : ";
    var de = $('#de').select2('data')[0].text;
    var a = '';
   	$('#desc').val(soli + de);
    
	$('#de').change(function(){ 
		           
	    var valor = $(this).val();
	    de = $('#de').select2('data')[0].text;
        
        $('#desc').val(soli + de); 
	    $.ajax({
	          type: "GET",
	          url: "/projection/encabezado?proyecto="+valor,
	          datatype:"json",
	          success: function (result) {
		            var s = '<option value="0">Todos los talleres</option>';
		            for(var i=0; i<result.length; i++){
		            	s += '<option value="' + result[i].id + '"> [' + result[i].tipo + '] (' + result[i].coddoc + ') ' + result[i].nompro + '</option>';
		            }
		           $('#cuales').html(s);
		           
		      }
        });
	    
        
	   						     			
	});
	
	$('#a').change(function(){
	
		var deVal = $('#de').val();
		var aVal = $('#a').val();
		if(deVal == aVal){
			alert("No se puede seleccionar al mismo usuario para el cambio");
			$('#a').val('');
			$('#a').select2();
		}else{ 
			de = $('#de').select2('data')[0].text;
	        //a = $('#a').select2('data')[0].text;
	        
	        //$('#desc').val(soli + de +" a "+ a); 
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
	
	var dsoli = "Descongelamiento de proyecto : ";
    var dde = $('#dde').select2('data')[0].text;
    var da = '';
   	$('#ddesc').val(dsoli + dde);
    
	$('#dde').change(function(){ 
		           
	    var dvalor = $(this).val();
	    dde = $('#dde').select2('data')[0].text;
        
        $('#ddesc').val(dsoli + dde); 
	    $.ajax({
	          type: "GET",
	          url: "/projection/encabezado?proyecto="+dvalor,
	          datatype:"json",
	          success: function (result) {
		            var s = '<option value="0">Todos los talleres</option>';
		            for(var i=0; i<result.length; i++){
		            	s += '<option value="' + result[i].id + '"> [' + result[i].tipo + '] (' + result[i].coddoc + ') ' + result[i].nompro + '</option>';
		            }
		           $('#dcuales').html(s);
		           
		      }
        });
	    
        
	   						     			
	});
	
	
	$('#dcuales').on('select2:select', function (e) {
		 var todos =  $(this).val();
		 if( $.inArray("0", todos) !== -1 ) {
			$(this).val("0").trigger("change");
			if (todos.length > 1){
				alert("Usted seleccionó la opción de todos los talleres");
			}
			
		 }
	});
	
	
});
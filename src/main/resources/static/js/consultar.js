$(document).ready( function () {

	$('.customselect').select2();
    $('#encabezado-table').DataTable({        
        language: {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron resultados",
                "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                "infoFiltered": "(filtrado de un total de _MAX_ registros)",
                "sSearch": "Búsqueda global en filtro actual:",
                "oPaginate": {
                    "sFirst": "Primero",
                    "sLast":"Último",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
            },
        //para usar los botones   
        "scrollX": true,
        dom: 'Bfrtilp',       
        buttons:[ 
			{
				extend:    'excelHtml5',
				text:      '<i class="fas fa-file-excel"></i> ',
				titleAttr: 'Exportar a Excel',
				className: 'btn btn-success'
			},
			{
				extend:    'print',
				text:      '<i class="fa fa-print"></i> ',
				titleAttr: 'Imprimir',
				className: 'btn btn-info'
			},
		]	        
    });
    
    //Crea Tabla de seguimiento usando AJAX
    $('#encabezado-table').on('click','.ver-participantes',function(event){		
		event.preventDefault();
		var href= $(this).attr('href');
		var table = $('#participantes-table').DataTable( {
			"searching": true,
			language: {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron participantes",
                "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                "infoFiltered": "(filtrado de un total de _MAX_ registros)",
                "sSearch": "Buscar:",
                "oPaginate": {
                    "sFirst": "Primero",
                    "sLast":"Último",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
            },
		    clear: true,
            destroy: true,
            "ajax":{
            	"method" : "POST",
                "url": href,
                "dataSrc": ""
            },
            "columns":[
                {data: "nombre"},
                {data: "rol"},
			    {data: "dependencia"},
			    {data: "correo"}
            ]  
          });
          $('#participantes-table').wrap('<div class="dataTables_scroll" style="overflow:auto;" />');
          
		$('#participantesModal').modal();		
	});
    
    //Hoja Trabajo MASTER
    $('#masterhojatrabajo-table thead tr').clone(true).appendTo( '#masterhojatrabajo-table thead' );
    $('#masterhojatrabajo-table thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        var exacto = false;
        if(title == 'Riesgo Valoracion Original RAM'){
        	$(this).html( '<input id="filter_riesgo" type="text" placeholder="Buscar por '+title+'" />' );
        	exacto = true;
        }else if (title == 'Riesgo Residual Valoracion RAM') {
        	$(this).html( '<input id="filter_residual" type="text" placeholder="Buscar por '+title+'" />' );
        	exacto = true;
        }else if (title == 'Estado') {
        	$(this).html( '<input id="filter_estado" type="text" placeholder="Buscar por '+title+'" />' );
        	exacto = false;
        }else if (title == 'Instancia de cierre') {
        	$(this).html( '<input id="filter_cierre" type="text" placeholder="Buscar por '+title+'" />' );
        	exacto = false;
        }
        else{       
        	$(this).html( '<input type="text" placeholder="Buscar por '+title+'" />' );
        	exacto = false;
 		}
 		if(exacto == false){
 			$( 'input', this ).on( 'keyup change', function () {
	            if ( table.column(i).search() !== this.value ) {
	                table
	                    .column(i)
	                    .search( this.value )
	                    .draw();
	            }
        	} );
 		}else{
 			$( 'input', this ).on( 'keyup change', function () {
 				var searchTerm = this.value.toLowerCase(),
       			regex = '\\b' + searchTerm + '\\b';
	            if ( table.column(i).search() !== this.value ) {
	                table
	                    .column(i)
	                    .search(regex, true, false)
	                    .draw();
	            }
        	} );
 		}
        
    } );
 
    var table = $('#masterhojatrabajo-table').DataTable({
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
                    "sLast":"Último",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
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
        buttons:[
        	{
                extend: 'colvis',
                columns: ':not(.noVis)',
                postfixButtons: [ 'colvisRestore' ]
            }, 
			{
				extend:    'excelHtml5',
				text:      '<i class="fas fa-file-excel"></i> ',
				titleAttr: 'Exportar a Excel',
				className: 'btn btn-success',
				exportOptions: {
		            columns: ':visible'
		        }
			},
			{
				extend:    'print',
				text:      '<i class="fa fa-print"></i> ',
				titleAttr: 'Imprimir',
				className: 'btn btn-info'
			},
		]	        
    });
    
    //Fin Hoja Trabajo MASTER
    
     //Hoja Trabajo Lecciones Aprendidas
    $('#masterhojatrabajo-table2 thead tr').clone(true).appendTo( '#masterhojatrabajo-table2 thead' );
    $('#masterhojatrabajo-table2 thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        if (title == 'Estado') {
        	$(this).html( '<input id="filter_estado_la" type="text" placeholder="Buscar por '+title+'" />' );
        }else{       
        	$(this).html( '<input type="text" placeholder="Buscar por '+title+'" />' );
 		}
 		
        $( 'input', this ).on( 'keyup change', function () {
            if ( table2.column(i).search() !== this.value ) {
                table2
                    .column(i)
                    .search( this.value )
                    .draw();
            }
        } );
    } );
 
    var table2 = $('#masterhojatrabajo-table2').DataTable({
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
                    "sLast":"Último",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
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
        buttons:[
        	{
                extend: 'colvis',
                columns: ':not(.noVis)',
                postfixButtons: [ 'colvisRestore' ]
            }, 
			{
				extend:    'excelHtml5',
				text:      '<i class="fas fa-file-excel"></i> ',
				titleAttr: 'Exportar a Excel',
				className: 'btn btn-success',
				exportOptions: {
		            columns: ':visible'
		        }
			},
			{
				extend:    'print',
				text:      '<i class="fa fa-print"></i> ',
				titleAttr: 'Imprimir',
				className: 'btn btn-info'
			},
		]	        
    });
    
    //Fin Hoja Trabajo Lecciones Aprendidas
    
    //Hoja Trabajo Riesgos Materializados
    $('#masterhojatrabajo-table3 thead tr').clone(true).appendTo( '#masterhojatrabajo-table3 thead' );
    $('#masterhojatrabajo-table3 thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        if (title == 'Estado') {
        	$(this).html( '<input id="filter_estado_rm" type="text" placeholder="Buscar por '+title+'" />' );
        }else{       
        	$(this).html( '<input type="text" placeholder="Buscar por '+title+'" />' );
 		}
 		
        $( 'input', this ).on( 'keyup change', function () {
            if ( table3.column(i).search() !== this.value ) {
                table3
                    .column(i)
                    .search( this.value )
                    .draw();
            }
        } );
    } );
 
    var table3 = $('#masterhojatrabajo-table3').DataTable({
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
                    "sLast":"Último",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
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
        buttons:[
        	{
                extend: 'colvis',
                columns: ':not(.noVis)',
                postfixButtons: [ 'colvisRestore' ]
            }, 
			{
				extend:    'excelHtml5',
				text:      '<i class="fas fa-file-excel"></i> ',
				titleAttr: 'Exportar a Excel',
				className: 'btn btn-success',
				exportOptions: {
		            columns: ':visible'
		        }
			},
			{
				extend:    'print',
				text:      '<i class="fa fa-print"></i> ',
				titleAttr: 'Imprimir',
				className: 'btn btn-info'
			},
		]	        
    });
    
    //Fin Hoja Trabajo Riesgos Materializados
    
    //Hoja Trabajo Riesgos Emergentes
    $('#masterhojatrabajo-table4 thead tr').clone(true).appendTo( '#masterhojatrabajo-table4 thead' );
    $('#masterhojatrabajo-table4 thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        if (title == 'Estado') {
        	$(this).html( '<input id="filter_estado_rm" type="text" placeholder="Buscar por '+title+'" />' );
        }else{       
        	$(this).html( '<input type="text" placeholder="Buscar por '+title+'" />' );
 		}
 		
        $( 'input', this ).on( 'keyup change', function () {
            if ( table4.column(i).search() !== this.value ) {
                table4
                    .column(i)
                    .search( this.value )
                    .draw();
            }
        } );
    } );
 
    var table4 = $('#masterhojatrabajo-table4').DataTable({
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
                    "sLast":"Último",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
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
        buttons:[
        	{
                extend: 'colvis',
                columns: ':not(.noVis)',
                postfixButtons: [ 'colvisRestore' ]
            }, 
			{
				extend:    'excelHtml5',
				text:      '<i class="fas fa-file-excel"></i> ',
				titleAttr: 'Exportar a Excel',
				className: 'btn btn-success',
				exportOptions: {
		            columns: ':visible'
		        }
			},
			{
				extend:    'print',
				text:      '<i class="fa fa-print"></i> ',
				titleAttr: 'Imprimir',
				className: 'btn btn-info'
			},
		]	        
    });
    
    //Fin Hoja Trabajo Riesgos Emergentes
      
    //Crea Tabla de seguimiento usando AJAX
    $('#masterhojatrabajo-table, #masterhojatrabajo-table2, #masterhojatrabajo-table3, #masterhojatrabajo-table4 ').on('click','.add-seguimiento',function(event){		
		event.preventDefault();
		$('#cierreplan').hide();
		$('#archivo').val("");
		$('#comentario').val("");
		$('#cambioestado').val("1");
		$('.insertar_alertas' ).empty();
		
		var href= $(this).attr('href');
		var idhoja = $(this).attr('id');
		$('#open_hojatrabajo').val(idhoja);
		var table = $('#hojacomentarios-table').DataTable( {
			"searching": true,
			language: {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontró seguimiento",
                "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                "infoFiltered": "(filtrado de un total de _MAX_ registros)",
                "sSearch": "Buscar:",
                "oPaginate": {
                    "sFirst": "Primero",
                    "sLast":"Último",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
            },
            columnDefs: [
            {targets:0, render:function(data){
		      return moment(data).format('YYYY-MM-DD <br> HH:mm');
		    }}
		    ],
		    clear: true,
            destroy: true,
            "ajax":{
            	"method" : "POST",
                "url": href,
                "dataSrc": ""
            },
            "columns":[
                {data: "fecha"},
                {data: "comentario"},
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
			    {data: "usuario"},
            ]  
          });
          $('#hojacomentarios-table').wrap('<div class="dataTables_scroll" style="overflow:auto;" />');
          
		$('#editModal').modal();
		
	});
	
	$('#archivo').change(function (){
      	var fileName = $('#archivo').val();
      	var files = $('#archivo').get(0).files;
      	if(fileName) {
      		var ext = $('#archivo').val().split('.').pop().toLowerCase();
			if($.inArray(ext, ['xlsx','xls','doc','docx','ppt','pptx','mpp','pdf']) == -1) {
			    $(".insertar_alertas" ).append( '<div class="alert alert-danger insertados"><strong>Extensión de archivo no permitida</strong></div>');
			    if(files.length > 3){
			    	$(".insertar_alertas" ).append( '<div class="alert alert-danger insertados"><strong>No se pueden agregar más de 3 archivos</strong></div>');
			    }
			    $(this).val("");
			    $('#cierreplan').hide();
	      		$('#cambioestado').val("1");
			}else{
			
	      		if(files.length < 4){
	      			$('#cierreplan').show();
	      		}else{
	      			$(".insertar_alertas" ).append( '<div class="alert alert-danger insertados"><strong>No se pueden agregar más de 3 archivos</strong></div>');
	      			$(this).val("");
	      			$('#cierreplan').hide();
	      			$('#cambioestado').val("1");
	      		}
	      	}
      	}else{
      		$('#cierreplan').hide();
      		$('#cambioestado').val("1");
      	}
      	
      	
     });
	
	$('#addComment').submit(function(e){                         
	    e.preventDefault();
	    comentario = $.trim($('#comentario').val());
	    if(comentario == ""){
	    	$(".insertar_alertas" ).append( '<div class="alert alert-danger insertados"><strong>No puede agregar un comentario vacío</strong></div>');
	    }else{
	    	files = $('#archivo').get(0).files;
	    	if(files.length <4){
			    user = $.trim($('#au_user').val());
			    hojatra = $.trim($('#open_hojatrabajo').val());
			    var formData = new FormData(this);
			        $.ajax({
			          type: "POST",
			          url: "/hojacomentarios/addNew",
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
	

	var getUrlParameter = function getUrlParameter(sParam) {
	    var sPageURL = window.location.search.substring(1),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;
	
	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');
	
	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
	        }
	    }
	};
	
	var riesgo = getUrlParameter('riesgo');
	if(riesgo != null && riesgo != ''){
		$('#filter_riesgo').val(riesgo);
		$('#filter_riesgo').keyup();
	}
	
	var residual = getUrlParameter('residual');
	if(residual != null && residual != ''){
		$('#filter_residual').val(residual);
		$('#filter_residual').keyup();
	}
	
	var estado = getUrlParameter('estado');
	if(estado != null && estado != ''){
		$('#filter_estado').val(estado);
		$('#filter_estado').keyup();
	}
	
	var cierre = getUrlParameter('cierre');
	if(cierre != null && cierre != ''){
		$('#filter_cierre').val(cierre);
		$('#filter_cierre').keyup();
	}
	
    
   
} );
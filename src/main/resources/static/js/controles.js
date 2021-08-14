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
        responsive: "false",
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
    
    //Hoja Trabajo
    $('#hojatrabajo-table thead tr').clone(true).appendTo( '#hojatrabajo-table thead' );
    $('#hojatrabajo-table thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        $(this).html( '<input type="text" placeholder="Buscar por '+title+'" />' );
 
        $( 'input', this ).on( 'keyup change', function () {
            if ( Mastertable.column(i).search() !== this.value ) {
                Mastertable
                    .column(i)
                    .search( this.value )
                    .draw();
            }
        } );
    } );
 
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
    
    //Fin Hoja Trabajo What-If
    
    //Modificar Control usando AJAX
    $('#hojatrabajo-table').on('click','.modify-control',function(event){		
		event.preventDefault();
		var href = $(this).attr('href');
		$('#modifyModal').modal();
		$.get(href, function(control, status) {
			
		});		
	});
	
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
						return moment(data).format('YYYY-MM-DD <br> hh:mm');
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

		$('#changesModal').modal();
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
			    $('#cierreplan').hide();
	      		$('#cambioestado').val("1");
			}else{
			
	      		if(files.length < 4){
	      			$('#cierreplan').show();
	      		}else{
	      			alert ("No se pueden agregar más de 3 archivos");
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
	    	alert ("No puede agregar un comentario vacío"); 
	    }else{
	    	files = $('#archivo').get(0).files;
	    	if(files.length <4){
			    user = $.trim($('#au_user').val());
			    hojatra = $.trim($('#open_hojatrabajo').val());
			    var formData = new FormData(this);
			        $.ajax({
			          type: "POST",
			          url: "/hojacomentarios/addNew",
			          enctype: "multipart/form-data",
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
		     	alert ("No se pueden agregar más de 2 archivos"); 
		     	$('#archivo').val("");
		     }
	     }
	     
	    //$('#hojatrabajo-table').DataTable().ajax.reload();							     			
	});
	
	 //Hoja Trabajo Constructabilidad
    $('#conhojatrabajo-table thead tr').clone(true).appendTo( '#conhojatrabajo-table thead' );
    $('#conhojatrabajo-table thead tr:eq(1) th').each( function (i) {
        var title2 = $(this).text();
        $(this).html( '<input type="text" placeholder="Buscar por '+title2+'" />' );
 
        $( 'input', this ).on( 'keyup change', function () {
            if ( construc.column(i).search() !== this.value ) {
                construc
                    .column(i)
                    .search( this.value )
                    .draw();
            }
        } );
    } );
 
    var construc = $('#conhojatrabajo-table').DataTable({
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
    
    //Fin Hoja Trabajo Constructabilidad
    
     //Hoja Trabajo Design
    $('#deshojatrabajo-table thead tr').clone(true).appendTo( '#deshojatrabajo-table thead' );
    $('#deshojatrabajo-table thead tr:eq(1) th').each( function (i) {
        var title2 = $(this).text();
        $(this).html( '<input type="text" placeholder="Buscar por '+title2+'" />' );
 
        $( 'input', this ).on( 'keyup change', function () {
            if ( design.column(i).search() !== this.value ) {
                design
                    .column(i)
                    .search( this.value )
                    .draw();
            }
        } );
    } );
 
    var design = $('#deshojatrabajo-table').DataTable({
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
    
    //Fin Hoja Design Review
    
    //Crea Tabla de solicitud cambios usando AJAX
    $('#deshojatrabajo-table, #hojatrabajo-table, #conhojatrabajo-table, #masterhojatrabajo-table').on('click','.add-cambios',function(event){		
		event.preventDefault();
		
		var href= $(this).attr('href');
		var idhoja = $(this).attr('id');
		$('#open_hojatrabajo2').val(idhoja);
		var table = $('#hojacambios-table').DataTable( {
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
                    "sLast":"Último",
                    "sNext":"Siguiente",
                    "sPrevious": "Anterior"
			     },
			     "sProcessing":"Procesando...",
            },
            columnDefs: [
            {targets:0, render:function(data){
		      return moment(data).format('YYYY-MM-DD <br> hh:mm');
		    }},
		    {targets:5, render:function(data){
		    	if(data != null){
		    		return moment(data).format('YYYY-MM-DD <br> hh:mm');
		    	}else{
		    		return "";
		    	}
		      
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
                {data: "htcam_fechasolicitud"},
                {data: "er_estado_id",
                render: function(data, type, row, meta){
		           	if(type === 'display'){
		            		data = '';
		            		if(row.er_estado_id == 3){
		            			data = 'En Proceso'
		            		}else if(row.er_estado_id == 4){
		            			data = 'Aceptado'
		            		}else if(row.er_estado_id == 5){
		            			data = 'Rechazado'
		            		}
		            }
		            return data;
		         }
		         ,defaultContent: ""
			    },
                {data: "er_tipocambios_id",
                render: function(data, type, row, meta){
		           	if(type === 'display'){
		            		data = '';
		            		if(row.er_tipocambios_id == 1){
		            			data = 'Cambio de fecha comprometida'
		            		}else if(row.er_tipocambios_id == 2){
		            			data = 'Cambio de responsable'
		            		}else if(row.er_tipocambios_id == 3){
		            			data = 'Cambio de fecha comprometida y responsable'
		            		}
		            }
		            return data;
		         }
		         ,defaultContent: ""
			    },
                {data: "htcam_motivo"},
                {data: "usuario.fullname"},
                {data: "htcam_fecharesolucion"},
                {data: "htcam_razon"},
                {data: "usuario2.fullname",
                render: function(data, type, row, meta){
		           	if(type === 'display'){
		            		data = '';
		            		if(data != null){
		            			data = row["usuario2.fullname"]
		            		}
		            }
		            return data;
		         }
		         ,defaultContent: ""
			    },
            ]  
          });
          $('#hojacambios-table').wrap('<div class="dataTables_scroll" style="overflow:auto;" />');
          
		$('#changesModal').modal();		
	});
	
	$('#addChanges').submit(function(e){                         
	    e.preventDefault();
	    motivo = $.trim($('#motivocambio').val());
	    if(motivo == ""){
	    	alert ("No puede agregar un motivo vacío"); 
	    }else{
			hojatra = $.trim($('#open_hojatrabajo2').val());
			var formData = new FormData(this);
		        $.ajax({
		          type: "POST",
		          url: "/hojacambios/addNew",
		          datatype:"json",
		          data: formData,
		          success: function (data) {
			            alert(data)
			      },
			      cache: false,
			      contentType: false,
			      processData: false
	        });
		         $('#motivo').val("");
			     $('#changesModal').modal('toggle');
		     
	     }
	     
	    //$('#hojatrabajo-table').DataTable().ajax.reload();							     			
	});
	
	var user_i = $('#de').val();
    $.ajax({
          type: "GET",
          url: "/projection/encabezado?user_id="+user_i,
          datatype:"json",
          success: function (result) {
	            var s = '<option value="0">Todos los talleres</option>';
	            for(var i=0; i<result.length; i++){
	            	s += '<option value="' + result[i].id + '"> [' + result[i].tipo + '] (' + result[i].coddoc + ') ' + result[i].nompro + '</option>';
	            }
	           $('#cuales').html(s);
	           
	      }
    });
	
	$('#de').change(function(){                         
	    var user_id = $(this).val();
	    $.ajax({
	          type: "GET",
	          url: "/projection/encabezado?user_id="+user_id,
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
	
	$('#cuales').on('select2:select', function (e) {
		 var todos =  $(this).val();
		 if( $.inArray("0", todos) !== -1 ) {
			$(this).val("0").trigger("change");
			if (todos.length > 1){
				alert("Usted seleccionó la opción de todos los talleres");
			}
			
		 }
	});
    
    
} );
$('document').ready(function() {

	 var mascaraEdit = $('#mascaraEdit').val();
	if (mascaraEdit == "IC-9102" || "IC-9000" || "IC-9101"){
	 document.getElementById("checkboxedit").checked=true;
	}else{
		document.getElementById("checkboxedit").checked=false;
	};
	
	
    var tipocambio = $('#estadoprEdit').val();
    if(tipocambio == 1){
        $('#faseEdit').show();
        $('#estadoprEdit').show();
    }
    $('#estadoprEdit').change(function (){
        var tipocambio = $('#estadoprEdit').val();
        if(tipocambio == "En Desarrollo"){
           $('#estadoprEdit').show();
           $("#faseEdit").show();
       }else if (tipocambio == "Finalizado"){
           $("#faseEdit").hide();
           $("#fasetexto").hide();
           $('#estadoprEdit').show();
       }else if (tipocambio == "Cancelado"){
       
        $("#faseEdit").hide();
        $("#fasetexto").hide();
        $("#estadoprEditEdit").show();
        $('#estadoprEdit').show();
       }  
    });    
    
    var tipocambio2 = $('#faseEdit').val();
    if(tipocambio2 == "Formulacion"){
        $('#faseEdit').show();
        $('#estadoprEdit').show();
    }
    $('#faseEdit').change(function (){
        var tipocambio2 = $('#faseEdit').val();
        if(tipocambio2 == "Formulacion"){        
       $("#estadoprEdit").show();
       $("#faseEdit").show();
       }else if (tipocambio2 == "Planeacion"){
           $('#faseEdit').show();
           $("#estadotexto").hide();
           $("#estadoprEdit").hide();           
       }else if (tipocambio2 == "ejecucion"){
        $('#faseEdit').show();
        $("estadoprEdit").hide();  
        $("#estadotexto").hide();    
       } else if (tipocambio2 == "Cierre"){
        $('#faseEdit').show();
        $("#estadoprEdit").hide(); 
        $("#estadotexto").hide();       
       }  
    });


	$('#portafolio-table thead tr').clone(true).appendTo( '#portafolio-table thead' );
    $('#portafolio-table thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        if(title != 'Acciones'){
        	$(this).html( '<input type="text" placeholder="Buscar por '+title+'" />' );
        }
 		
        $( 'input', this ).on( 'keyup change', function () {
            if ( table.column(i).search() !== this.value ) {
                table
                    .column(i)
                    .search( this.value )
                    .draw();
            }
        } );
    } );
    
    var table = $('#portafolio-table').DataTable({
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
        responsive: true,
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
    
    
 /*   $('#addComment').on('click', '.btn-primary', function(event) {                         
	    event.preventDefault();
	    comentario = $.trim($('#comentario').val());
	    
	    if(comentario == ""){
	    	$(".alertas1" ).append( '<div class="alert alert-danger insertados"><strong>No puede agregar un comentario vacío</strong></div>');
	    }else{
		
		
		
		
	    	files = $('#archivo').get(0).files;
	    	if(files.length <4){
		         $('#comentario').val("");
			     $('#archivo').val("");
			     $('#editModal').modal('toggle');
		     }else{
		     	$(".alertas1" ).append( '<div class="alert alert-danger insertados"><strong>No se pueden agregar más de 3 archivos</strong></div>');
		     	$('#archivo').val("");
		     }
	     }
	     
	    comentario = $.trim($('#comentario').val());
	    //$('#hojatrabajo-table').DataTable().ajax.reload();    							     			
	});
*/
    
  /*  	if(comentario == ""){
	    	$(".alertas1" ).append( '<div class="alert alert-danger insertados"><strong>No puede agregar un comentario vacío</strong></div>');
	    }
	    if(archivo == ""){
	    	$(".alertas2" ).append( '<div class="alert alert-danger insertados"><strong>No puede agregar un seguimiento sin evidencia.</strong></div>');
	    }*/


	$('#portafolio-table').on('click', '.btn-primary', function(event) {
		event.preventDefault();
		$('.alertas2' ).empty();
		$('.alertas1' ).empty();
		$('#archivo').val("");
		$('#comentario').val("");
		$('#portafolio-table').on('click', '.btn-primary', function(event) {
		event.preventDefault();
		$('.alertas2' ).empty();
		$('.alertas1' ).empty();
		$('#archivo').val("");
		$('#comentario').val("");
		var href = $(this).attr('href');
		$.get(href, function(er_portafolio, status) {
			$('#idEdit').val(er_portafolio.er_portafolio_id);
			$('#mascaraEdit').val(er_portafolio.er_mascara);
			$('#nomshortEdit').val(er_portafolio.er_nombre_corto);
			$('#nomproEdit').val(er_portafolio.er_nombre_proyecto);
			//$('#procesoEdit').val(er_portafolio.er_proceso);
			$('#estadoprEdit').val(er_portafolio.estadop);
			$('#faseEdit').val(er_portafolio.fasep);
			$('#editModal').modal();
			$('#procesoEdit').val("1");
		});

		$('#portafolio-table').on('click','.deleteButton',function(event) {
			event.preventDefault();
			var href = $(this).attr('href');
			$('#deleteModal #delRef').attr('href', href);
			$('#deleteModal').modal();
		});
	});
		
		var href = $(this).attr('href');
		$.get(href, function(er_portafolio, status) {
			$('#idEdit').val(er_portafolio.er_portafolio_id);
			$('#mascaraEdit').val(er_portafolio.er_mascara);
			$('#nomshortEdit').val(er_portafolio.er_nombre_corto);
			$('#nomproEdit').val(er_portafolio.er_nombre_proyecto);
			//$('#procesoEdit').val(er_portafolio.er_proceso);
			$('#estadoprEdit').val(er_portafolio.estadop);
			$('#faseEdit').val(er_portafolio.fasep);
			$('#editModal').modal();
			$('#procesoEdit').val("1");

		});

		$('#portafolio-table').on('click','.deleteButton',function(event) {
			event.preventDefault();
			var href = $(this).attr('href');
			$('#deleteModal #delRef').attr('href', href);
			$('#deleteModal').modal();
		});
	});
});
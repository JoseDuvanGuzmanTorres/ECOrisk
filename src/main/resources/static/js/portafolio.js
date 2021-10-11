$('document').ready(function() {

    var tipocambio = $('#estadopr').val();
    if(tipocambio == 1){
        $('#fasepr').show();
        $('#estadopr').show();
    }
    $('#estadopr').change(function (){
        var tipocambio = $('#estadopr').val();
        if(tipocambio == 1){
           $('#estadopr').show();
           document.getElementById("fasepr").disabled = false;
       }else if (tipocambio == 2){
         
           document.getElementById("fasepr").disabled = true;
           document.getElementById("estadopr").disabled = false;
           $('#estadopr').show();
       }else if (tipocambio == 3){
       
        document.getElementById("fasepr").disabled = true;
        document.getElementById("estadopr").disabled = false;
        $('#estadopr').show();
       }  
    });    
    var tipocambio2 = $('#fasepr').val();
    if(tipocambio2 == 1){
        $('#fasepr').show();
        $('#estadopr').show();
    }
    $('#fasepr').change(function (){
        var tipocambio2 = $('#fasepr').val();
        if(tipocambio2 == 1){        
           document.getElementById("estadopr").disabled = false;
           document.getElementById("fasepr").disabled = false;
       }else if (tipocambio2 == 2){
           $('#fasepr').show();
           document.getElementById("fasepr").disabled = false;
           document.getElementById("estadopr").disabled = true;           
       }else if (tipocambio2 == 3){
        $('#fasepr').show();
        document.getElementById("fasepr").disabled = false;
        document.getElementById("estadopr").disabled = true;      
       } else if (tipocambio2 == 4){
        $('#fasepr').show();
        document.getElementById("fasepr").disabled = false;
        document.getElementById("estadopr").disabled = true;        
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
    
    $('.insertar_alertas' ).empty();
    $(".insertar_alertas" ).append( '<div class="alert alert-danger alert-dismissible fade show"><strong>Tiene que agregar seguimiento</strong></div>');

    $('#Editar').submit(function(e){ 
        var deVal = $('#fasepr').val();        
        if(deVal == aVal){
			alert("No se puede seleccionar al mismo usuario para el cambio");
		//	$('#a').val('');
		//	$('#a').select2();
		}                
					     			
	});	
	$('#portafolio-table').on('click', '.btn-primary', function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$.get(href, function(er_portafolio, status) {
			$('#idEdit').val(er_portafolio.er_portafolio_id);
			$('#mascaraEdit').val(er_portafolio.er_mascara);
            $('#codproEdit').val(er_portafolio.estadop);
			$('#nomshortEdit').val(er_portafolio.er_nombre_corto);
			$('#nomproEdit').val(er_portafolio.er_nombre_proyecto);
			$('#procesoEdit').val(er_portafolio.er_proceso);
			$('#editModal').modal();
		});

		$('#portafolio-table').on('click','.deleteButton',function(event) {
			event.preventDefault();
			var href = $(this).attr('href');
			$('#deleteModal #delRef').attr('href', href);
			$('#deleteModal').modal();
		});
	});
});
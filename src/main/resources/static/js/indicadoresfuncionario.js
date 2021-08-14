$(document).ready( function () {
    
    	$('#hojatrabajo3-table thead tr').clone(true).appendTo( '#hojatrabajo3-table thead' );
    $('#hojatrabajo3-table thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        
          if (title == 'WHAT-IF') {
        	$(this).html( '<input id="filter_Proceso_ind" type="text" placeholder="Buscar por '+title+'" />' );
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
    

    var table3 = $('#hojatrabajo3-table').DataTable({
    	orderCellsTop: true,     
    	searching: false,
        language: {
                "lengthMenu": "Mostrar _MENU_ registros",
                "zeroRecords": "No se encontraron resultados",
                "info": "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
                "infoEmpty": "Mostrando registros del 0 al 0 de un total de 0 registros",
                "infoFiltered": "(filtrado de un total de _MAX_ registros)",
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
            },
            {width: "20%", targets: [0,1,2,3,4]}
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
 
     	$('#hojatrabajo4-table thead tr').clone(true).appendTo( '#hojatrabajo4-table thead' );
    $('#hojatrabajo4-table thead tr:eq(1) th').each( function (i) {
        var title = $(this).text();
        
          if (title == 'WHAT-IF') {
        	$(this).html( '<input id="filter_Proceso_ind" type="text" placeholder="Buscar por '+title+'" />' );
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
    
    
    var table4 = $('#hojatrabajo4-table').DataTable({
    	orderCellsTop: true,
    	searching: false,   
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
            },
            {width: "20%", targets: [0,1,2,3,4]}
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
    
    var table5 = $('#hojatrabajo5-table').DataTable({
    	orderCellsTop: true,
    	searching: false,      
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
            },
            {width: "20%", targets: [0,1,2,3,4]}
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
    
    var table6 = $('#hojatrabajo6-table').DataTable({
    	orderCellsTop: true,
    	searching: false,      
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
    //,{width: "20%", targets: [0,1,2,3,4]}
} );
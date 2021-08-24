$('document').ready(function() {
	$('.customselect').select2();
	$('#users-table thead tr').clone(true).appendTo( '#users-table thead' );
    $('#users-table thead tr:eq(1) th').each( function (i) {
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
    
    var table = $('#users-table').DataTable({
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
    
	$('#users-table').on('click', '.btn-primary', function(event) {
		event.preventDefault();
		var href = $(this).attr('href');
		$.get(href, function(user, status) {
			$('#idEdit').val(user.id);
			$('#fullnameEdit').val(user.fullname);
			$('#usernameEdit').val(user.username);
			$('#passwordEdit').val(user.password);
			$('#rolEdit').val(user.roles_id);
			$('#editModal').modal();
		});

		$('.table').on('click','.detailsButton',function(event) {
			event.preventDefault();
			var href = $(this).attr('href');
			$.get(href, function(user, status) {
				$('#idDetails').val(user.id);
				$('#firstnameDetails').val(user.firstname);
				$('#lastnameDetails').val(user.lastname);
				$('#usernameDetails').val(user.username);
				$('#lastModifiedByDetails').val(user.lastModifiedBy);
				$('#lastModifiedDateDetails').val(user.lastModifiedDate.substr(0, 19).replace("T", " "));
			});
			$('#detailsModal').modal();
		});

		$('#users-table').on('click','.deleteButton',function(event) {
			event.preventDefault();
			var href = $(this).attr('href');
			$('#deleteModal #delRef').attr('href', href);
			$('#deleteModal').modal();
		});
	});
});
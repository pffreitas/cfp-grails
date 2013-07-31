function PlanejamentoController(){
	
	var 
		$novoOrcamentoForm = $("#novoOrcamentoForm"),
		
		self = {
				novoOrcamento: function(){
					$("#btn-save").button('loading');
					
					$.ajax({
						url: $novoOrcamentoForm.attr('action'),
						type: "POST",
						data: $novoOrcamentoForm.serialize()
					}).complete(function (data){
						$("#btn-save").button('reset');
					}).success(function(data){
						$('#novoOrcamentoModal').modal('hide');
					}).error(function(data){
						
					});
				}
		}
	
	
	return self;
}
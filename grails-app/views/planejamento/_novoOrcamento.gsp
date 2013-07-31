<div class="modal fade" id="novoOrcamentoModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title">Novo or&ccedil;amento</h4>
			</div>
			
			<div class="modal-body">
				<g:form name="novoOrcamentoForm" controller="planejamento" action="novoOrcamento">
				  <fieldset>
				    <div class="form-group">
				      <label for="nome">Nome</label>
				      <input type="text" class="form-control" id="nome" name="nome">
				    </div>
				    <div class="form-group">
				      <label for="tipo">Tipo</label>
				      <select id="tipo" name="tipo" class="form-control">
						  <option value="ENTRADA">Entrada</option>
						  <option value="SAIDA">Saida</option>
						</select>
				    </div>
				  </fieldset>
				</g:form>
			</div>
			
			<div class="modal-footer">
				<a href="#" class="btn btn-default" data-dismiss="modal">Cancelar</a>
				<button id="btn-save" type="button" class="btn btn-primary" data-loading-text="Salvando..." onclick="planejamentoController.novoOrcamento();">Salvar</button>
			</div>
		</div>
	</div>
</div>

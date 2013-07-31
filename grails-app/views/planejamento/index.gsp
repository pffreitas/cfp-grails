<html>
	<head>
		<meta name="layout" content="main" />
		<r:require module="m-planejamento"/>
	</head>

	<body>
		<div id="page-header">
			<h1>Or&ccedilamento</h1>
			
			<ul class="toolbar">
				<li>
					<a data-toggle="modal" href="#novoOrcamentoModal" class="shadowed dark" title="+">+</a>
				</li>
			</ul>
		</div>
		
		
		<g:render template="novoOrcamento" />
		
		<r:script>
			window.planejamentoController = new PlanejamentoController(); 
		</r:script>
	</body>

</html>
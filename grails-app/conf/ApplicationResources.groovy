modules = {
	
    basic {
		dependsOn 'bootstrap'
		
		resource url:'/css/base.css'
    }
	
	jQuery {
		resource url:'http://code.jquery.com/jquery.js'
	}
	
	bootstrap {
		dependsOn 'jQuery'
		resource url: 'http://netdna.bootstrapcdn.com/bootstrap/3.0.0-rc1/js/bootstrap.min.js'
		resource url: 'http://netdna.bootstrapcdn.com/bootstrap/3.0.0-rc1/css/bootstrap.min.css'
		 
	}
	
	"m-toolbar" {
		resource url:'css/modules/toolbar.css'
	}
	
	"m-planejamento" {
		dependsOn 'm-toolbar'
		
		resource url:'js/modules/planejamentoController.js'
	}
} 
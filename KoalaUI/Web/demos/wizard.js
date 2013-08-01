$().ready(function(){
	
	var Wizard = Backbone.Model.extend({
		urlRoot: '/server',
		validate: function(attrs){
			for(var key in attrs){
				if(attrs[key].length == 0){
					return key+'不能为空';
				}
					
			}
		}
	});
	
	var WizardView = Backbone.View.extend({
		el: $('.wizard'),
		model : new Wizard(),
		
		initialize : function(){
			this.listenTo(this.model, 'invalid', this.error)
		},
		error : function(model, error){
			alert(error);
		},
		events : {
			'click .prev' : 'prev',
			'click .next' : 'next',
			'click .complete' : 'complete'
		},
		
		prev : function(e){
			var target = $(e.currentTarget);
			var page = target.parent().parent();
			var index = page.data('index') - 1;
			var ul = $(this.el).find('ul');
			var n = {left : page.outerWidth() - page.position().left}
			page.parent().animate(n , 400, "swing", function() {
				ul.find('li').eq(page.index() - 1).find('a').tab('show');
            });
		},
		
		next : function(e){
			var target = $(e.currentTarget);
			var page = target.parent().parent();
			var attr = {};
			page.find('input[type != "button"]').each(function(){
				var input =  $(this);
				attr[input.attr('name')] = input.val();
			});
			
			if(!this.model.set(attr, {validate:true})){
				return;
			}
			var ul = $(this.el).find('ul');
			var n = {left : - (page.position().left + page.outerWidth()) }
			page.parent().animate(n, 400, 'swing', function() {
				ul.find('li').eq(page.index() + 1).find('a').tab('show');
            });
		},
		
		complete : function(e){
			var target = $(e.currentTarget);
			var page = target.parent().parent();
			var attr = {};
			page.find('input[type != "button"]').each(function(){
				var input =  $(this);
				attr[input.attr('name')] = input.val();
			});
			this.model.save();
		}
		
	});
	
	Backbone.sync = function(method,model){
		console.info(method);
		console.info(JSON.stringify(model));
	};
	
	var view = new WizardView();
});
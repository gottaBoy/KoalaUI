$().ready(function(){
	window.People = Backbone.Model.extend({
		validate : function(attrs){
		
		}
	});
	
	window.PeopleList = Backbone.Collection.extend({
		model:People,
		localStorage: new Store('peoples')
	});
	
	window.peoples = new PeopleList();
	
	window.PeopleView = Backbone.View.extend({
		tagName: 'tr',
		template: _.template($('#template').html()),
		events: {
			'dblclick td' : 'edit',
			'blur input,select' : 'close',
			'click .del' : 'clear'
		},
		initialize : function(){
			this.model.bind('change', this.render, this);
			this.model.bind('destroy', this.remove, this);
		},
		
		render : function(){
			$(this.el).html(this.template(this.model.toJSON()));
			this.setText();
			return this;
		},

		setText : function(){
			var model = this.model;
			$(this.el).find('input select').each(function(){
				var input = $(this);
				input.val(model.get(input.attr('name')));
			})
		},
		
		edit : function(e){
			$(e.currentTarget).addClass('editing').find('input,select').focus();
		},

		close : function(e){
			var input = $(e.currentTarget);
			var obj = {};
			obj[input.attr('name')] = input.val();
			this.model.save(obj);
			input.parent().parent().removeClass('editing')
		},

		clear : function(){
			this.model.destroy();
		},
		
		remove : function(){
			$(this.el).remove();
		}

	});
	
	window.AppView = Backbone.View.extend({
		el: $('#app'),
		initialize : function(){
			peoples.bind("add", this.addOne , this);
			peoples.bind('reset', this.addAll , this);
			peoples.fetch();
		},
		events : {
			'click  #add-btn' : 'createOnEnter' 
		},
		createOnEnter : function(){
			var people = new People();
			var attrs = {};
			$('#emp-form input,#emp-form select').each(function(){
				var input = $(this);
				attrs[input.attr('name')] = input.val();
			});
			people.bind('error',function(model,error){
				alert(error);
			});
			if(people.set(attrs)){
				peoples.create(people);
			}
		},
		
		addOne : function(people){
			people.set({'cid' : people.get('cid') || peoples.length });
			people.bind('error', function(model,error){
				alert(error);
			});
			var view = new PeopleView({model:people});
			$('.emp-table tbody').append(view.render().el);
		},

		addAll : function(){
			peoples.each(this.addOne);
		}

	});
	
	window.app = new AppView();
})
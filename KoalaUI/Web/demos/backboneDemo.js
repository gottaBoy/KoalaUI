$().ready(function(){
	var People = Backbone.Model.extend({
	
	});
	
	PeopleList = Backbone.Collection.extend({
		model:People,
		localStorage: new Backbone.LocalStorage("todos-backbone"),
	});
	
	var Peoples = new PeopleList();
	
	var PeopleView = Backbone.View.extend({
		tagName: 'tr',
		template: _.template($('#template').html()),
		events: {
			'dblclick td' : 'edit',
			'blur input,select' : 'close',
			'click .del' : 'clear'
		},
		initialize : function(){
			this.listenTo(this.model,"change", this.render);
			this.listenTo(this.model,"destroy", this.remove);
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
	
	var AppView = Backbone.View.extend({
		el: $('#app'),
		initialize : function(){
			this.listenTo(Peoples,"add", this.addOne);
			this.listenTo(Peoples,"reset", this.addAll);
			Peoples.fetch();
		},
		events : {
			'click  #add-btn' : 'createOnEnter' 
		},
		createOnEnter : function(){
			var people = new People();
			var attrs = {};
			$('#emp-form input[type="text"],#emp-form select').each(function(){
				var input = $(this);
				attrs[input.attr('name')] = input.val();
				input.val("");
			});
			people.bind('error',function(model,error){
				alert(error);
			});
			Peoples.create(attrs);
		},
		
		addOne : function(people){
			people.bind('error', function(model,error){
				alert(error);
			});
			var view = new PeopleView({model:people});
			$('.emp-table tbody').append(view.render().el);
		},

		addAll : function(){
			Peoples.each(this.addOne, this);
		}

	});
	
	var app = new AppView();
})
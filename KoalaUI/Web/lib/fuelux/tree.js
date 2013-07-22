/*
 * Fuel UX Tree
 * https://github.com/ExactTarget/fuelux
 *
 * Copyright (c) 2012 ExactTarget
 * Licensed under the MIT license.
 */

define(function(require) {

	"use strict";
	
	var $ = require('jquery');


	// TREE CONSTRUCTOR AND PROTOTYPE

	var Tree = function (element, options) {
		this.$element = $(element);
		this.options = $.extend({}, $.fn.tree.defaults, options);
		this.render();
		this.$element.on('click', '.tree-item', $.proxy( function(ev) { this.selectChildren(ev.currentTarget); } ,this));
		this.$element.on('click', '.tree-folder-header', $.proxy( function(ev) { this.selectParent(ev.currentTarget); }, this));

	};

	Tree.prototype = {
		constructor: Tree,

		render: function () {
			this.populate(this.options.dataSource.data, this.$element);
			this.$element.find('.tree-folder').show();
			this.$element.find('.tree-item').show();
		},
		populate: function (data, $el) {
			var self = this;
			$.each(data, function(index, value) {
				var $entity ;
				if(!self.isEmpty(value.children)){
					$entity = self.createParent();
					$entity.find('.tree-folder-name').html(value.menu.title);
					$entity.find('.tree-folder-header').data(value.menu);
					$entity.attr("id",value.menu.id);
					self.populate(value.children, $entity.find(".tree-folder-content:eq(0)"));
					$entity.find(".tree-folder-content:eq(0)").hide();
				}else{
					$entity = self.createChildren();
					$entity.find('.tree-item-name').html(value.menu.title);
					$entity.attr("href",value.menu.href);
					$entity.attr("id",value.menu.id);
					$entity.data(value.menu);
				}
				$el.append($entity);
			});
		},
		isEmpty: function(obj){
			if(!obj){
				return true; 
			}
			for (var i in obj ) { 
				if(obj.hasOwnProperty(i)){
			          return false;
			     }
			} 
			return true; 
		},
		createParent: function(){
			return $('<div class="tree-folder" style="display:none;">'+
				'<div class="tree-folder-header"><i class="icon-folder-close"></i>'+
				'<div class="tree-folder-name"></div></div><div class="tree-folder-content"></div>'+
			'<div class="tree-loader" style="display:none"></div></div>');
		},
		createChildren:function(){
			return $('<div class="tree-item" style="display:none;">'+
			'<i class="tree-dot"></i><div class="tree-item-name"></div></div>');
		},
		selectChildren: function (el) {
			var $el = $(el);
			var $all = this.$element.find('.tree-selected');
			var data = [];

			if (this.options.multiSelect) {
				$.each($all, function(index, value) {
					var $val = $(value);
					if($val[0] !== $el[0]) {
						data.push( $(value).data() );
					}
				});
			} else if ($all[0] !== $el[0]) {
				$all.removeClass('tree-selected')
					.find('i').removeClass('icon-ok').addClass('tree-dot');
				data.push($el.data());
			}

			if($el.hasClass('tree-selected')) {
				$el.removeClass('tree-selected');
				$el.find('i').removeClass('icon-ok').addClass('tree-dot');
			} else {
				$el.addClass ('tree-selected');
				$el.find('i').removeClass('tree-dot').addClass('icon-ok');
				if (this.options.multiSelect) {
					data.push( $el.data() );
				}
			}

			if(data.length) {
				this.$element.trigger('selected', {info: data});
			}

		},

		selectParent: function (el) {
			var $el = $(el);
			var $par = $el.parent();

			if($el.find('.icon-folder-close').length) {
				if ($par.find('.tree-folder-content').children().length) {
					$par.find('.tree-folder-content:eq(0)').show();
				} 

				$par.find('.icon-folder-close:eq(0)')
					.removeClass('icon-folder-close')
					.addClass('icon-folder-open');

				this.$element.trigger('opened', $el.data());
			} else {
				if(this.options.cacheItems) {
					$par.find('.tree-folder-content:eq(0)').hide();
				} else {
					$par.find('.tree-folder-content:eq(0)').empty();
				}

				$par.find('.icon-folder-open:eq(0)')
					.removeClass('icon-folder-open')
					.addClass('icon-folder-close');

				this.$element.trigger('closed', $el.data());
			}
		},

		selectedItems: function () {
			var $sel = this.$element.find('.tree-selected');
			var data = [];

			$.each($sel, function (index, value) {
				data.push($(value).data());
			});
			return data;
		}
	};


	// TREE PLUGIN DEFINITION

	$.fn.tree = function (option, value) {
		var methodReturn;

		var $set = this.each(function () {
			var $this = $(this);
			var data = $this.data('tree');
			var options = typeof option === 'object' && option;

			if (!data) $this.data('tree', (data = new Tree(this, options)));
			if (typeof option === 'string') methodReturn = data[option](value);
		});

		return (methodReturn === undefined) ? $set : methodReturn;
	};

	$.fn.tree.defaults = {
		multiSelect: false,
		loadingHTML: '<div>Loading...</div>',
		cacheItems: true
	};

	$.fn.tree.Constructor = Tree;

});

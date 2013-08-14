+function($){
	
	"use strict";
	// KoalaUI 节点向导类定义
	// ==============================	

	var NodeWizard = function(element, options){
		this.$element = $(element);
		this.options = options;
		this.$nodes = this.$element.find('.nodes > ul');
		this.$items = this.$element.find('.items');
		this.resizeNodes();
		this.$nodes.find('li:first>div').addClass('active');
		this.$items.find('.item:first').addClass('active');
		this.$items.find('.item').css('width', this.$element.width())
		this.$element.find(this.options.prevSelector).on('click', $.proxy(this.prevStep, this));
		this.$element.find(this.options.nextSelector).on('click', $.proxy(this.nextStep, this));
	};
	
	//属性列表
	NodeWizard.DEFAULTS = {
		animate: 400,			//动画速度
		activeIndex: 0,			//当前索引
		easing: 'swing', //动画方式
		prevSelector: '.nav_btn > img:first',
		nextSelector: '.nav_btn > img:last',
	};
	
	//各个Prototype
	//在后面追加节点
	NodeWizard.prototype.appendNode = function(option){
		var that = this;
		this.$element.one('addNode', function(){
			var node = $('<li><div class="node"/><p>newNode</p></li>');
			var title = typeof option == 'string' ?  option : option && option.title;
			title && node.find('p').text(title);
			that.$nodes.append(node);
			that.resizeNodes();
		})
		var item = $('<div class="item">');
		item.css('width', this.$element.width());
		this.$items.append(item) && this.$element.trigger('addNode');
	};
	
	//调整节点宽度
	NodeWizard.prototype.resizeNodes = function(option){
		var nodeWidth = 97.2/(this.$nodes.children().length-1);
		this.$nodes.find('li:not(:last)').css('width', nodeWidth+'%');
		this.$nodes.find('li:last').css('width', '2.8%');
	}
	
	//获得当前处理步骤的索引值
	NodeWizard.prototype.getActiveIndex = function(){
		this.$active = this.$items.find('.active');
		return this.$items.children().index(this.$active);
	};
	
	//下一步
	NodeWizard.prototype.nextStep = function(e){
		this.activeIndex = this.getActiveIndex();
		if(this.sliding || this.activeIndex == this.$nodes.children().length - 1){
			return;
		}
		return this.slide('next');
	};
	
	//上一步
	NodeWizard.prototype.prevStep = function(e){
		this.activeIndex = this.getActiveIndex();
		if(this.sliding || this.activeIndex == 0){
			return ;
		}
		return this.slide('prev');
	};
	
	//滑动
	NodeWizard.prototype.slide = function(type, next){
		var $active = this.$items.find('.active');
		var $next = next || $active[type]();
		var that = this;
		this.sliding = true;
		if($next.hasClass('active')){
			return;
		}
		if(this.$nodes.children().length){
			this.$element.one('slid', function(){
				var $node = $(that.$nodes.children()[that.activeIndex]);
				$node && type == 'next' ? $node.next().find('div').removeClass('node').addClass('active') 
						: $node.find('div').removeClass('active').addClass('node');
			})
		}
		var position = type == 'next' ? $active.position().left + $active.outerWidth() : $active.position().left - $active.outerWidth();
		this.$items.animate({left : -position }, this.options.animate, this.options.swing, function() {
			$next.addClass('active');
	        $active.removeClass('active');
	        that.sliding = false;
	        that.$element.trigger('slid') 
		});
		return this;
	}
	
	//节点插件定义
	var old = $.fn.nodeWizard;
	
	$.fn.nodeWizard = function(option){
		 return this.each(function(){
		 	  var $this = $(this);
			  var data = $this.data('bs.NodeWizard');
			  var options = $.extend({}, NodeWizard.DEFAULTS, $this.data(), typeof option == 'object' && option);
			  if(!data){
				  $this.data('bs.NodeWizard', (data = new NodeWizard(this,options)));
			  }
			  
		  })
	};
	
	$.fn.nodeWizard.Constructor = NodeWizard;
	
	//NODEWIZARD NO CONFLICT
	// ====================

	$.fn.nodeWizard.noConflict = function () {
		$.fn.nodeWizard = old;
		return this;
	};
	
	//NODEWIZARD DATA API
	$(window).on('load',function(e){
		$('[data-toggle="node_wizard"]').each(function(){
			var $nodeWizard = $(this);
			$nodeWizard.nodeWizard();
		})
	});
	
}(window.jQuery);

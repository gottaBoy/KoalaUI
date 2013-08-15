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
		this.$nodes.find('li:first').addClass('active');
		this.$items.find('.item:first iframe').attr('src', this.$nodes.find('li:first').data('target') || this.$nodes.find('li:first').attr('src'));
		this.$element.find('.nav_btn > img:first').on('click', $.proxy(this.prevStep, this));
		this.$element.find('.nav_btn > img:last').on('click', $.proxy(this.nextStep, this));
	};
	
	//属性列表
	NodeWizard.DEFAULTS = {
		animate: 600,			//动画速度
		activeIndex: 0,			//当前索引
		easing: 'swing' //动画方式
	};
	
	//各个Prototype
	//在后面追加节点
	NodeWizard.prototype.appendNode = function(option){
		var node = $('<li><div class="node"/><p>newNode</p></li>');
		if(option){
			option.title && node.find('p').text(option.title);
			option.target && node.data('target', option.target);
		}	
		this.$nodes.append(node);
		this.resizeNodes();
	};
	
	//调整节点宽度
	NodeWizard.prototype.resizeNodes = function(option){
		var nodeWidth = 99/(this.$nodes.children().length-1);
		this.$nodes.find('li:not(:last)').width(nodeWidth+'%');
		this.$nodes.find('li:last').width('1%');
	}
	
	//获得当前处理步骤的索引值
	NodeWizard.prototype.getActiveIndex = function(){
		this.$active = this.$nodes.find('li.active:last');
		return this.$nodes.children().index(this.$active);
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
		var $active = this.$items.find('.item:first');
		var item = $('<div class="item"><iframe></iframe></div>');
		type == 'next' ? item.appendTo(this.$items) : item.prependTo(this.$items) && this.$items.css('left', '-100%');
		var $next = $active[type]();
		this.$items.width('200%') && $active.width('50%') && $next.width('50%');
		var $node = this.$nodes.find('li.active:last');
		var that = this;
		this.sliding = true;
		if(this.$nodes.children().length){
			this.$element.one('slid', function(){
				type == 'next' ? $node.addClass('libg').next().addClass('active') 
						: $node.removeClass('active').prev().removeClass('libg');
			})
		}
		this.$element.one('animate', function(){
			that.$items.animate(type == 'next' ? {left:'-100%'} : {left: 0}, that.options.animate, that.options.swing, function(){
				$active.remove();
				that.$items.css('left', 0);
				that.$items.width('100%') && $next.width('100%');
				that.$element.trigger('slid');
				that.sliding = false;
			});
		})
		var target = $node[type]().data('target') || $node[type]().attr('href');
		target && $next.find('iframe').attr('src', target);	
		$next.find('iframe').on('load', function(){
			that.$element.trigger('animate');
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

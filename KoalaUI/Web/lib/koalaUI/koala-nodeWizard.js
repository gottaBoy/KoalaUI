+function($){
	
	"use strict";
	// KoalaUI 节点向导类定义
	// ==============================	

	var NodeWizard = function(element, options){
		this.$element = $(element);
		this.options = options;
		this.$node = this.$element.find(this.options.nodeSelector);
		this.$items = this.$element.find(this.options.itemSelector);
		this.$node.find(this.options.firstNodeSelector).addClass(this.options.activeClass);
		this.$items.find(this.options.firstItemSelector).addClass(this.options.activeClass);
		this.$element.find(this.options.prevSelector).on('click', $.proxy(this.prevStep, this));
		this.$element.find(this.options.nextSelector).on('click', $.proxy(this.nextStep, this));
	};
	
	//属性列表
	NodeWizard.DEFAULTS = {
		animate: 0,			//动画速度
		activeIndex: 0,			//当前索引
		easing: 'swing', //动画方式
		prevSelector: '.nav_btn > img:first',
		nextSelector: '.nav_btn > img:last',
		nodeSelector: '.node > ul',
		itemSelector: '.items',
		activeClass: 'active',
		firstNodeSelector: 'li:first>div',
		firstItemSelector: '.item:first'
	};
	
	//各个Prototype
	//在后面追加节点
	NodeWizard.prototype.appendNode = function(node){
		
	};
	
	//获得当前处理步骤的索引值
	NodeWizard.prototype.getActiveIndex = function(){
		this.$active = this.$ul.find('.active');
		return this.$active ? this.$node.children().index(this.$active) : this.activeIndex;
	};
	
	//下一步
	NodeWizard.prototype.nextStep = function(e){
		e && e.preventDefault();
		this.activeIndex = this.getActiveIndex();
		
	};
	
	//上一步
	NodeWizard.prototype.prevStep = function(e){
		
	};
	
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

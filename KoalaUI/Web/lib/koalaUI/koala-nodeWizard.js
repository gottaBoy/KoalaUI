+function($){"use strict";
	// KoalaUI 节点向导类定义
	// ==============================	

	var NodeWizard = function(){
		
	};
	
	//属性列表
	NodeWizard.DEFAULTS = {
		animate:0,			//动画速度
		stepIndex:0			//当前索引
	};
	
	//各个Prototype
	//在后面追加节点
	NodeWizard.prototype.appendNode = function(node){
		
	};
	
	//获得当前处理步骤的索引值
	NodeWizard.prototype.getStepIndex = function(){
		
	};
	
	//下一步
	NodeWizard.prototype.nextStep = function(){
		
	};
	
	//上一步
	NodeWizard.prototype.prevStep = function(){
		
	};
	
	//节点插件定义
	var old = $.fn.nodeWizard;
	
	$.fn.nodeWizard = function(option){
		
	};
	
	$.fn.nodeWizard.Constructor = NodeWizard;
	
	//NODEWIZARD NO CONFLICT
	// ====================

	$.fn.nodeWizard.noConflict = function () {
		$.fn.nodeWizard = old;
		return this;
	};
	
	//NODEWIZARD DATA API
	$(document).on("","",function(e){
		
	});
	
}(window.jQuery);
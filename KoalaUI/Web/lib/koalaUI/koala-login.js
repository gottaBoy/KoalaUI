+function($){
	"use strict";

	//Koala Login组件类定义
	var Login = function(element, options){
		this.$element = $(element);
		this.options = options;
		this.$element.on('submit', $.proxy(this.doSubmit, this));
	};

	//属性列表
	Login.DEFAULTS = {
		checkCodeUrl:"",	//验证码地址
		loginUrl:"",		//登陆地址
		registerUrl:""		//注册地址
	}

	//替换验证码
	Login.prototype.changeCheckCode = function(){
		
	};

	//登陆方法
	Login.prototype.doLogin = function(){
		
	};

	//注册方法
	Login.prototype.doRegister = function(){

	}

	//提交事件的方法
	Login.prototype.doSubmit = function(){
		
		return false;
	}

	//节点插件定义
	var old = $.fn.login

	$.fn.login = function(option){
		 return this.each(function(){
		 	  var $this = $(this);
			  var data = $this.data('koala.Login');
			  var options = $.extend({}, Login.DEFAULTS, $this.data(), typeof option == 'object' && option);
			  if(!data){
				  $this.data('koala.Login', (data = new Login(this,options)));
			  }
			  
		  })
	};

	//LOGIN NO CONFLICT
	$.fn.login.noConflict = function () {
		$.fn.login = old;
		return this;
	};

	$.fn.login.Constructor = Login;
	
}(window.jQuery);
+function($){
	"use strict";

	//Koala Login组件类定义
	var Login = function(element, options){
		this.$element = $(element);
		this.options = options;
		$('<img>').attr('src', this.options.checkCodeUrl).appendTo($('.checkCode'));
		$('.checkCode').on('click', $.proxy(this.changeCheckCode, this));
		this.$element.on('submit', $.proxy(this.doLogin, this));
	};

	//属性列表
	Login.DEFAULTS = {
		checkCodeUrl:"",	//验证码地址
		loginUrl:"",		//登陆地址
		registerUrl:""		//注册地址
	}

	//替换验证码
	Login.prototype.changeCheckCode = function(e){
		var src = this.options.checkCodeUrl + '?random='+new Date().getTime();
		$(e.currentTarget).find('img').attr('src', src);
	};

	//注册方法
	Login.prototype.doRegister = function(){

	}

	//验证参数
	Login.prototype.validate = function(){
		var that = this;
		var flag = true;
		this.$element.find('input').each(function(index, element){
			var $element = $(element);
			if($element.val().length == 0){
				//that.showMessage($element);
				flag = false;
				return false;
			}
		});
		return flag;
	}
	
	//显示提示信息
	Login.prototype.showMessage = function($element, content){
		$element.popover({
			trigger: 'manual'
		}).focus().popover('show').on('click', function(){
			$element.popover('destroy');
		});
	}
	
	//登陆
	Login.prototype.doLogin = function(){
		if(!this.validate()){
			return false;
		}
		this.options.loginUrl && this.$element.attr('action', this.options.loginUrl);
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
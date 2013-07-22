requirejs.config({
	paths: {
		'jquery': '../lib/jquery',
		'bootstrap': '../lib/bootstrap/js',
		'fuelux': '../lib/fuelux'
	}
});

require(['jquery','../lib/fuelux/all'], function ($) {
	// SEARCH CONTROL
	$('#MySearch').on('searched', function (e, text) {
		alert('Searched: ' + text);
	});
	$('#search-disable').on('click', function () {
		$('#MySearch').search('disable');
	});
	$('#search-enable').on('click', function () {
		$('#MySearch').search('enable');
	});
	
	//SPINNER
	$("#spinner-disable").on("click",function(){
		$("#MySpinner").spinner("disable");
	});
	
	$("#spinner-enable").on("click",function(){
		$("#MySpinner").spinner("enable");
	});
	
	//tooltip
	$('.tooltip-demo').tooltip({
		selector: "a[data-toggle=tooltip]"
    });
	
     // popover demo
    $("a[data-toggle=popover]")
      .popover()
      .click(function(e) {
        e.preventDefault()
      });
    
    //ComboBox

	$('#btnComboboxSelectByValue').on('click', function () {
		$('#MyCombobox').combobox('selectByValue', '3');
	});
	
	$('#combobox-selectByValue').on('click', function () {
		$('#MyCombobox1').combobox('selectByValue', '3');
	});
});
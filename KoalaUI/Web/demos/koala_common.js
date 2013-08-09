requirejs.config({
	paths: {
		'jquery': '../lib/jquery',
		'fuelux': '../lib/fuelux'
	}
});

require(['jquery','fuelux/all',"datetimepicker"], function ($) {
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
	
    //ComboBox

	$('#btnComboboxSelectByValue').on('click', function () {
		$('#MyCombobox').combobox('selectByValue', '3');
	});
	
	$('#combobox-selectByValue').on('click', function () {
		$('#MyCombobox1').combobox('selectByValue', '3');
	});
	
});
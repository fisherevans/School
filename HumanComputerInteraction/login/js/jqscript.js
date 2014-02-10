$(document).ready(function(){
	$(".inputRowTooltip").fadeOut(0);
	$(".inputRowTooltipArrow").fadeOut(0);
	
	$(document).on("focus", "input.error", function(){
		$(this).parent().find(".inputRowTooltip").fadeIn("fast", "swing");
		$(this).parent().find(".inputRowTooltipArrow").fadeIn("fast", "swing");
	});
	
	$(document).on("mouseover", "input:not(.error, .hideHover)", function(){
		$(this).parent().find(".inputRowTooltip").fadeIn("fast", "swing");
		$(this).parent().find(".inputRowTooltipArrow").fadeIn("fast", "swing");
	});
	
	$(document).on("mouseout", "input:not(.error, .hideHover)", function(){
		$(this).parent().find(".inputRowTooltip").fadeOut("fast", "swing");
		$(this).parent().find(".inputRowTooltipArrow").fadeOut("fast", "swing");
	});

	$(document).on("focusout", "input:not(.error, .hideHover)", function(){
		$(this).parent().find(".inputRowTooltip").fadeOut("fast", "swing");
		$(this).parent().find(".inputRowTooltipArrow").fadeOut("fast", "swing");
	});
});
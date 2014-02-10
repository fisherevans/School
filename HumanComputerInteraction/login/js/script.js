function goToLogin()
{
	document.title = "Axiom Login";
	document.getElementsByName("loginUsername")[0].focus();
    document.getElementById("login").className = "inputForm";
    document.getElementById("register").className = "inputFormHidden";
    document.getElementById("loading").className = "inputFormHidden";
}

function goToRegister()
{
	document.title = "Axiom Registration";
	document.getElementsByName("registerUsername")[0].focus();
    document.getElementById("login").className = "inputFormHidden";
    document.getElementById("register").className = "inputForm";
    document.getElementById("loading").className = "inputFormHidden";
}

function goToLoading()
{
	document.title = "Loading...";
    document.getElementById("login").className = "inputFormHidden";
    document.getElementById("register").className = "inputFormHidden";
    document.getElementById("loading").className = "inputForm";
	
	if(document.getElementsByName("loginUsername")[0].value == "SuperAdmin" && 
		document.getElementsByName("loginPassword")[0].value == "G0D-1234")
	{
		setInterval(function(){window.location.href = "granted.html";},3000);
	}
}

function restart()
{
    window.location.href = "index.html";
}
var anim = true;
function checkLogin()
{
	document.getElementById("dummy").focus();
	var submit = validateLogin();
	
	if(document.getElementsByName("loginUsername")[0].value == "Fisher" && 
		document.getElementsByName("loginPassword")[0].value == "password")
	{
		submit = false;
		
		$("#loginButton").addClass("error");
		
		if(anim)
		{
			$("#loginButton").removeClass("animated2");
			$("#loginButton").removeClass("shake2");
			$("#loginButton").addClass("animated1");
			$("#loginButton").addClass("shake1");
		}
		else
		{
			$("#loginButton").removeClass("animated1");
			$("#loginButton").removeClass("shake1");
			$("#loginButton").addClass("animated2");
			$("#loginButton").addClass("shake2");
		}
		
		$("#loginButton").parent().find(".inputRowTooltip").fadeIn("fast", "swing");
		$("#loginButton").parent().find(".inputRowTooltipArrow").fadeIn("fast", "swing");
		
		$("#login").find(".inputText").addClass("red");
		
		$("#loginPassword").select();
	}
	else
	{
		$("#loginButton").removeClass("error");
		
		if(anim)
		{
			$("#loginButton").removeClass("animated1");
			$("#loginButton").removeClass("shake1");
			anim = false;
		}
		else
		{
			$("#loginButton").removeClass("animated2");
			$("#loginButton").removeClass("shake2");
			anim = true;
		}
		
		$("#loginButton").parent().find(".inputRowTooltip").fadeOut("fast", "swing");
		$("#loginButton").parent().find(".inputRowTooltipArrow").fadeOut("fast", "swing");
		$("#login").find(".inputText").removeClass("red");
	}
	
	$(".inputText:not(.error)").parent().find(".inputRowTooltip").fadeOut("slow", "swing");
	$(".inputText:not(.error)").parent().find(".inputRowTooltipArrow").fadeOut("slow", "swing");
	
	if(submit)
		goToLoading();
		
    return false;
}

function checkRegister()
{
	document.getElementById("dummy").focus();
	var submit = validateRegister();
	
	$(".inputText:not(.error)").parent().find(".inputRowTooltip").fadeOut("slow", "swing");
	$(".inputText:not(.error)").parent().find(".inputRowTooltipArrow").fadeOut("slow", "swing");
	
	if(submit)
		goToLoading();
    
    return false;
}

function validateLogin()
{
    var submit = true;
    
    if(!validatePassword("loginPassword"))
        submit = false;
    
    if(!validateUsername("loginUsername"))
        submit = false;
		
	return submit;
}

function validateRegister()
{
    var submit = true;
    
    if(!validatePhone("registerPhone"))
        submit = false;
    
    if(!validateEmail("registerEmail"))
        submit = false;
    
    if(!validatePasswordConfirm("registerPassword", "registerConfirmPassword"))
        submit = false;
		
    if(!validateUsername("registerUsername"))
        submit = false;
		
	return submit;
}

function setCorrect(element)
{
	var base = "inputText";
	if(element.type === "password")
		base = base + " inputPassword";
		
    element.className = base + " correct";
    return true;
}

function setError(element)
{
	var base = "inputText";
	if(element.type === "password")
		base = base + " inputPassword";
	
	if(element.className.indexOf("shake1") == -1)
		element.className = base + " error animated1 shake1";
	else
		element.className = base + " error animated2 shake2";
		
	element.focus();
	
	$(element).parent().find(".inputRowTooltip").fadeIn("fast", "swing");
	$(element).parent().find(".inputRowTooltipArrow").fadeIn("fast", "swing");
	
    return false;
}

function validateUsername(id)
{
    var input = document.getElementsByName(id)[0];
    var text = input.value;
    if(text === null || text.length < 6 || text.length > 14 || /[^a-zA-Z0-9]/.test(text))
        return setError(input);
    else
        return setCorrect(input);
}

function validatePassword(id)
{
    var input = document.getElementsByName(id)[0];
    var text = input.value;
    if(text === null || text.length < 8)
        return setError(input);
    else
        return setCorrect(input);
}

function validatePasswordConfirm(id1, id2)
{
    var input1 = document.getElementsByName(id1)[0];
    var input2 = document.getElementsByName(id2)[0];
    var text1 = input1.value;
    var text2 = input2.value;
	
	var valid = true;
	
	if(text1 != text2)
	{
        setError(input2);
		valid = false;
	}
	else
	{
		setCorrect(input2);
	}	
	
	if(!validatePassword(id1))
	{
		setError(input2);
		setError(input1);
		valid = false;
	}
	
	return valid;
}

function validateEmail(id)
{
    var input = document.getElementsByName(id)[0];
    var text = input.value;
    if(text === null || /\S+@\S+\.\S+/.test(text) == false)
        return setError(input);
    else
        return setCorrect(input);
}

function validatePhone(id)
{
    var input = document.getElementsByName(id)[0];
    var text = input.value;
    if(text === null || text.replace(/[^0-9]/g,'').length != 10 || /[^0-9\-\(\) ]/.test(text))
        return setError(input);
    else
        return setCorrect(input);
}
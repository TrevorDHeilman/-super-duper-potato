window.onload = function () {
    getEmployee(1);
    
    var button = document.getElementById("submitbutton");
	button.addEventListener('click', function(event){
		event.preventDefault();
		submitRequestComment(3);
	});
}

var employeeids = [];
var requestid = window.location.href.split("=")[1];
function getEmployee(i) {
	var baseURL="/Project1/";
		
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = displayRequest;
    xhttp.open("POST", baseURL + "comments");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    console.log(requestid);
    xhttp.send("type="+i+"&requestid="+requestid);
    function displayRequest() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            request = JSON.parse(xhttp.responseText);
            document.getElementById("requestlabel").innerHTML = request.requestId;
            console.log(request);
            employeeids.push(request.employeeId);
            getRequestHistory(2);
            
        }
    }
}

function getRequestHistory(i){
	
	var baseURL="/Project1/";
	var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = displayRequest;
    xhttp.open("POST", baseURL + "comments");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("type="+i+"&requestid="+requestid);
    function displayRequest() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            request = JSON.parse(xhttp.responseText);
            if(request){
            	
            	request.forEach(function (request) {
            		employeeids.push(request.employeeId);
            	});
            	console.log(employeeids);
            	var selectEmployee = document.getElementById("requestidselect");
	    		var i;
	    		for(i=0; i<employeeids.length; i++){
			        var newOption = employeeids[i];
			        var employeeid = document.createElement("option");
			        employeeid.textContent = newOption;
			        employeeid.value = newOption;
			        selectEmployee.appendChild(employeeid);
	    		}
            }
        }
    }
}

function submitRequestComment(i){
	var baseURL="/Project1/";
	let selectEmployee = document.getElementById("requestidselect").value;
	var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = displayComment;
    xhttp.open("POST", baseURL + "comments");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("type="+i+"&requestid="+requestid+"&selectedEmployee="+selectEmployee);
    
    function displayComment() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            console.log("Success");
            window.location.href="http://localhost:8080/Project1/static/home.html";
            
        }
    }
}
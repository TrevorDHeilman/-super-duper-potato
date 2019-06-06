var requestIds = [];

window.onload = function () {
    getRequests(0);
    
    var button = document.getElementById("newrequestbutton");
	button.addEventListener('click', function(event){
		event.preventDefault();
		addRequestForm(3);
	});
	
	getRequests(1)
}

function getRequests(i) {
	var baseURL="/Project1/";
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = displayRequests;
    xhttp.open("POST", baseURL + "requests");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("type="+i);
    function displayRequests() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            requests = JSON.parse(xhttp.responseText);
            console.log(requests);
            requests.forEach(function (request) {
                if(i==0){
                	addRequestsToTable(request);	
                }
                else if(i==1){
                	addRequestsToTable2(request);
                }
            });
        }
    }
}


function addRequestsToTable(request) {
    var table = document.getElementById("requests");
    var tr = document.createElement("tr");
    let td;
    //Request ID
    addTableDef(request.requestId, tr);
    requestIds.push(request.requestId)
    //Employee ID
    addTableDef(request.employeeId, tr);
    //Event Date
    addTableDef(request.eventDate, tr);
    //Applied Reimbursement
    addTableDef(request.appliedreimbursement, tr);
    //Status
    addTableDef(request.status, tr);
    // actually append the row to the table. 
    table.appendChild(tr);
}
function addRequestsToTable(request) {
    var table = document.getElementById("requests2");
    var tr = document.createElement("tr");
    let td;
    //Request ID
    addTableDef(request.requestId, tr);
    requestIds.push(request.requestId)
    //Employee ID
    addTableDef(request.employeeId, tr);
    //Event Date
    addTableDef(request.eventDate, tr);
    //Applied Reimbursement
    addTableDef(request.appliedreimbursement, tr);
    //Status
    addTableDef(request.status, tr);
    // actually append the row to the table. 
    table.appendChild(tr);
    
    var select = document.getElementById("requestidselect");
    var i;
    for(i = 0; i < requestId.length; i++){
    	var opt = requestIds[i];
    	var el = document.createElement("option");
    	el.textContent = opt;
    	el.value = opt;
    	select.appendChild(el);
    }
}

function addTableDef(value, tr) {
    let td = document.createElement("td");
    td.innerHTML = value;
    tr.appendChild(td);
}

function addListToTable(tr, list, parser){
    td = document.createElement("td");
    let ul = document.createElement("ul");
    for (let i = 0; i< list.length; i++){
        let li = document.createElement("li");
        li.innerHTML=parser(list[i]);
        ul.appendChild(li);
    }
    td.appendChild(ul);
    tr.appendChild(td);
}

function addRequestForm(i){
	let div = document.createElement("div");
	div.innerHTML=newRequestForm;
	let body = document.getElementsByTagName("body")[0];
	if(document.getElementById("form")){
	
		var requestJSON = {"appliedreimbursement":document.getElementById("reimbursementamount").value, 
						   "eventDate":document.getElementById("eventdate").value, 
						   "eventType":document.getElementById("eventtype").value};
		var baseURL="/Project1/";
		var xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = createRequestSuccess;
	    xhttp.open("POST", baseURL + "requests"); 
	    console.log(JSON.stringify(requestJSON));
	    xhttp.send(JSON.stringify(requestJSON));
	    
		function createRequestSuccess() {
			if(xhttp.readyState===4 && xhttp.status===200) {
				var data = JSON.parse(xhttp.responseText);
				if(data){
					console.log("Request Successful");
					window.location.href=baseURL +"static/home.html";
				}
			}
		}
	}
	else{
		body.appendChild(div);
		document.getElementById("newrequestbutton").innerHTML = "submit";
	}
}

var newRequestForm = `
	<form id = "form">
     	Value of Reimbursement: <input type = "number" name = "reimbursementamount" id="reimbursementamount" />
     	<br>
     	Event Date: <input type = "date" name = "eventdate" id="eventdate"/>
     	<br>
     	Event Type: <select name="eventtype" id="eventtype">
     					<option value=1>Not Selected</option>
							<option value=2>University Course</option>
							<option value=3>Seminar</option>
							<option value=4>Certification Preparation Course</option>
							<option value=5>Certification</option>
							<option value=6>Technical Training</option>
							<option value=7>Other</option>
					</select>
		<br>
		<button id ="cancelbutton" class="w3-button w3-round-large">Cancel</button>
		
		<script>
			document.getElementById("cancelbutton").addEventListener("click", function(event){
				event.preventDefault();
				console.log("Makes it here");
				document.removeElementById("form").
			});
		</script>
  	</form>
`;

var submitRequestUpdateForm = `
	<form id = "form">
     	Value of Reimbursement: <input type = "number" name = "reimbursementamount" id="reimbursementamount" />
     	<br>
     	Request ID: <select name="requestidselect" id="requestidselect">
     					<option value=1>None</option>
					</select>
     	<br>
     	Actione: <select name="eventtype" id="eventtype">
     					<option value=1>Accept</option>
						<option value=2>Decline</option>
						<option value=3>Request Comment</option>
					</select>
		<br>
		<button id ="submitbutton" class="w3-button w3-round-large">Cancel</button>
		
		<script>
			document.getElementById("cancelbutton").addEventListener("click", function(event){
				event.preventDefault();
				console.log("Makes it here");
				document.removeElementById("form").
			});
		</script>
  	</form>
`;


/*
var select = document.getElementById("requestidselect");
var i;
for(i = 0; i < requestId.length; i++){
	var opt = requestIds[i];
	var el = document.createElement("option");
	el.textContent = opt;
	el.value = opt;
	select.appendChild(el);
}
*/
window.onload = function () {
    getRequests(0);
    
    var button = document.getElementById("newrequestbutton");
	button.addEventListener('click', function(event){
		event.preventDefault();
		addRequestForm(3);
	});
	
	var updatebutton = document.getElementById("submitupdatebutton");
	updatebutton.addEventListener('click', function(event){
		event.preventDefault();
		updateRequest(4);
	})
	
	var cancelbutton = document.getElementById("logoutbutton");
	cancelbutton.addEventListener('click', function(event){
		event.preventDefault();
		logout();
	})
	
	getRequests(1)
	
	getRequests2(9);	
}

var commentids = [];

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
            //console.log(requests);
            //console.log(i);
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

function getRequests2(i) {
	var baseURL="/Project1/";
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = FindRequestSuccess;
    xhttp.open("POST", baseURL + "requests");
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("type="+i);
	function FindRequestSuccess() {
		if(xhttp.readyState===4 && xhttp.status===200) {
			var data = JSON.parse(xhttp.responseText);
			console.log(data);
			if(data){
				console.log("retrieving request Successful");
		        data.forEach(function (request) {

		        commentids.push(request.requestId);
		        });
		        
		    	if(commentids.length >0){
		    		addNewCommentForm();
		    		var selectComment = document.getElementById("requestidcomment");
		    		
		    		var i;
		    		for(i=0; i<commentids.length; i++){
				        var newOption = commentids[i];
				        var commentId = document.createElement("option");
				    	commentId.textContent = newOption;
				    	commentId.value = newOption;
				    	selectComment.appendChild(commentId);
		    		}
		    		
		    		var savebutton = document.getElementById("savecomment");
		    		savebutton.addEventListener('click', function(event){
		    			event.preventDefault();
		    			saveComment(7);
		    		})
		    	}
			}
		}
	}
}

function addRequestsToTable(request) {
    var table = document.getElementById("requests");
    var tr = document.createElement("tr");
    let td;
    //Request ID
    console.log(request);
    addTableDef(request.requestId, tr);
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
function addRequestsToTable2(request) {
	let requestIds = [];
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
    let uniqueIds = requestIds//.filter(distinct);
    //console.log(uniqueIds);
    for(i = 0; i < uniqueIds.length; i++){
    	var opt = uniqueIds[i];
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

function updateRequest(i){
	console.log(document.getElementById("eventtype").value);
	let strRequestId = document.getElementById("requestidselect").value;
	
	if(document.getElementById("eventtype").value == 'Request Comment'){
		window.location.href="http://localhost:8080/Project1/static/comment.html?requestid="+strRequestId;
	}
	
	var baseURL="/Project1/";
    let requestId = document.getElementById("requestidselect").value;
    let eventtype = document.getElementById("eventtype").value;
    console.log(requestId);
    console.log(eventtype);
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = createRequestSuccess;
    xhttp.open("POST", baseURL + "requests"); 
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("type="+i+"&requestid="+requestId+"&eventtype="+eventtype);
    
	function createRequestSuccess() {
		if(xhttp.readyState===4 && xhttp.status===200) {
			//var data = JSON.parse(xhttp.responseText);
			//if(data){
				console.log("Update Successful");
				window.location.href=baseURL +"static/home.html";
			//}
		}
	}
}

function logout() {
	console.log("logging out");
	var baseURL="/Project1/";
	var xhttp=new XMLHttpRequest();
	xhttp.onreadystatechange=finish;
	xhttp.open("DELETE", baseURL+"login");
	xhttp.send();
	
	function finish(){
		if(xhttp.readyState===4){
			console.log("logged out");
			window.location.href="http://localhost:8080/Project1/static/login.html";
		}
	}
}

function addNewCommentForm(){
	
	document.getElementById("requestforcommentsdiv").innerHTML = newCommentForm;
	
}

function saveComment(i){
	
	var baseURL="/Project1/";
    let requestId = document.getElementById("requestidcomment").value;
    let comment = document.getElementById("requestcomment").value;
    console.log(requestId);
    console.log(comment);
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = createRequestSuccess;
    xhttp.open("POST", baseURL + "requests"); 
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("type="+i+"&requestid="+requestId+"&comment="+comment);
    
	function createRequestSuccess() {
		if(xhttp.readyState===4 && xhttp.status===200) {
			
			console.log("Comment Successful");
			window.location.href=baseURL +"static/home.html";
		}
	}
}

function addRequestForm(i){

	let div = document.createElement("div");
	div.innerHTML=newRequestForm;
	let body = document.getElementsByTagName("body")[0];
	console.log(document.getElementById("form"));
	if(document.getElementById("form")){
	
		console.log(document.getElementById("reimbursementamount"));
		var requestJSON = {"appliedreimbursement":document.getElementById("reimbursementamount").value, 
						   "eventDate":document.getElementById("eventdate").value, 
						   "eventType":document.getElementById("eventtypeselector").value};
		var baseURL="/Project1/";
		var xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = createRequestSuccess;
	    xhttp.open("POST", baseURL + "requests"); 
	    //console.log(JSON.stringify(requestJSON));
	    xhttp.send(JSON.stringify(requestJSON));
	    
		function createRequestSuccess() {
			if(xhttp.readyState===4 && xhttp.status===200) {
				var data = JSON.parse(xhttp.responseText);
				if(data){
					//console.log("Request Successful");
					window.location.href=baseURL +"static/home.html";
				}
			}
		}
	}
	else{
		body.appendChild(div);
		var button = document.getElementById("newrequestbutton");
		button.innerHTML = "submit";
	}
}

var newRequestForm = `
	<form id = "form">
     	Value of Reimbursement: <input type = "number" name = "reimbursementamount" id="reimbursementamount" />
     	<br>
     	Event Date: <input type = "date" name = "eventdate" id="eventdate"/>
     	<br>
     	Event Type: <select name="eventtypeselector" id="eventtypeselector">
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

var newCommentForm = `
	<form id = "commentform">
		<div class="row">
			<div class="col">
				Request ID: <select name="requestidcomment" id="requestidcomment">
		     				<option value=0> None</option>
							</select>
			</div>
			
			<div class="col">
				<button id ="savecomment" class="w3-button w3-round-large">Save</button>
			</div>
		</div>
		<br>			
     	Comment: <input type = "text" height = "50px" width = "100px"  name = "requestcomment" id="requestcomment" />

  	</form>
`;
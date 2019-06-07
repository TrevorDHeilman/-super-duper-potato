window.onload = function(){
	var baseURL="/Project1/";
	var employee = null;
	var button = document.getElementById("loginbutton");
	
	button.addEventListener('click', function(event){
		event.preventDefault();
		logout();
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange=loginSuccess;
		xhttp.open("POST", baseURL+"login");
		xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		let user = document.getElementById("username").value;
		let pass = document.getElementById("password").value;
		xhttp.send("user="+user+"&pass="+pass);
		
		function loginSuccess() {
			if(xhttp.readyState===4 && xhttp.status===200) {
				var data = JSON.parse(xhttp.responseText);
				if(data){
					window.location.href=baseURL +"static/home.html";
				}
			}
		}
	});
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
		}
	}
}



/*



function authenticate() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange=loginSuccess;
	xhttp.open("POST", baseURL+"login");
	xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	let user = document.getElementById("username").value;
	//console.log(user);
	let pass = document.getElementById("password").value;
	xhttp.send("user="+user+"&pass="+pass);
	function loginSuccess() {
		if(xhttp.readyState===4 && xhttp.status===200) {
			var data = JSON.parse(xhttp.responseText);
			employee=data.employee;
			customer=data.customer;
			document.getElementById("authent").innerHTML=authenticated;
			document.getElementById("logout").addEventListener("click",logout);
			if(customer) {
				document.getElementById("authUserName").innerHTML=customer.first+" "+customer.last;
			}
			if(employee) {
				document.getElementById("authUserName").innerHTML=employee.first+" "+employee.last+", "+employee.title;
				let btns = document.getElementsByClassName("emp-btn");
				for(let i=0; i<btns.length; i++){
					btns[i].disabled=false;
				}
			}
		}
	}
}
function logout() {
	console.log("logging out");
	var xhttp=new XMLHttpRequest();
	xhttp.onreadystatechange=finish;
	xhttp.open("DELETE", baseURL+"login");
	xhttp.send();
	
	function finish(){
		if(xhttp.readyState===4){
			document.getElementById("authent").innerHTML=unauthenticated;
			let btns = document.getElementsByClassName("emp-btn");
			for(let i = 0; i<btns.length; i++){
				btns[i].disabled=true;
			}
			document.getElementById("login").addEventListener("click",authenticate);
			document.getElementById("password").onkeydown=checkPasswordEnter;
		}
	}
}
*/
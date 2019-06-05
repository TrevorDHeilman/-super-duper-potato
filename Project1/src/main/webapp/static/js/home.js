window.onload = function () {
    getRequests();
}

function getRequests() {
	var baseURL="/Project1/";
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = displayBooks;
    xhttp.open("GET", baseURL + "requests");
    xhttp.send();
    function displayBooks() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            requests = JSON.parse(xhttp.responseText);
            console.log(requests);
            requests.forEach(function (request) {
                addRequestsToTable(request);
            });
        }
    }
}
function addRequestsToTable(request) {
    var table = document.getElementById("requests");
    var tr = document.createElement("tr");
    let td;
    //Event Date
    addTableDef(request.eventDate, tr);
    //Applied Reimbursement
    addTableDef(request.appliedreimbursement, tr);
    //Status
    addTableDef(request.status, tr);
    // actually append the row to the table.
    table.appendChild(tr);
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
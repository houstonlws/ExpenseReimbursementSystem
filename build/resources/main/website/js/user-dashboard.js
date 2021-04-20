window.onload = function(){
    getUser(); //populates user information immediately
    document.getElementById("file-upload").addEventListener("change" ,getFileName);
    document.getElementById("search").addEventListener("keyup", search);
    document.getElementById("all").addEventListener('click', getAll);
    document.getElementById("approved").addEventListener('click', getApproved);
    document.getElementById("denied").addEventListener('click', getDenied);
    document.getElementById("pending").addEventListener('click', getPending);
    document.getElementById("add").addEventListener('click', addReimb);
    document.getElementById("toggle").addEventListener('click', switchTheme);
}

/* 
    Gets all reimbursements for account and makes table visible
*/
let getAll = async function getAll(){
    changeSection(all);
    const result = await fetch("http://localhost:9001/api/user/reimbursements/all");
    let myJSON = await result.json();
    changeTable(myJSON, "allReimb");
}

/* 
    Gets all approved reimbursements for account and makes table visible
*/
let getApproved = async function getApproved(){
    changeSection(approved);
    const result = await fetch("http://localhost:9001/api/user/reimbursements/approved");
    let myJSON = await result.json();
    changeTable(myJSON, "approvedReimb");
}

/* 
    Gets all denied reimbursements for account and makes table visible
*/
let getDenied = async function getDenied(){
    changeSection(denied);
    const result = await fetch("http://localhost:9001/api/user/reimbursements/denied");
    let myJSON = await result.json();
    changeTable(myJSON, "deniedReimb");
}

/* 
    Gets all pending reimbursements for account and makes table visible
*/
let getPending = async function getPending(){
    changeSection(pending);
    const result = await fetch("http://localhost:9001/api/user/reimbursements/pending");
    let myJSON = await result.json();
    changeTable(myJSON, "pendingReimb");
}

/* 
    Makes Reimbursement submission form visible
*/
let addReimb = function addReimb(){
    changeSection(submit);
}

/* 
    Gets file extension of uploaded file
*/
let getFileName = function getFileName(){
    let fileName = document.getElementById("file-name") ;
    fileName = document.getElementById("file-upload").value.split(".").pop();
}

/* 
    Searches reimbursement tables for matching results
*/
let search = function search() {

    let query = document.getElementById('search').value;
    let allTables = document.getElementsByTagName('tbody');

    for(let table of allTables){
        let row = table.getElementsByTagName('tr');
        for (let i = 0; i < row.length; i++) {
            let cell = row[i].getElementsByTagName("td")[0];
            let txtValue = cell.textContent || cell.innerText;
            if (txtValue.toUpperCase().indexOf(query) > -1) {
              row[i].style.display = "";
            } else {
              row[i].style.display = "none";
            }
        }
    }
    
}

/* 
    Populates table with a list of reimbursements
*/
function changeTable(object, id){
    let txt = "";
    for (let x = 0; x<object.length; x++) {
        txt += "<tr><td>" +  object[x].reimbId + "</td>";
        txt += "<td>$" + object[x].reimbAmount + "</td>";
        txt += "<td>" + object[x].reimbSubmitted + "</td>";
        txt += "<td>" + object[x].reimbResolved + "</td>";
        txt += "<td>" + object[x].reimbDescription + "</td>";
        txt += `<td><a href="` +"/html/api/receipts/"+ object[x].reimbId +`.jpg">Receipt</td>`;
        txt += "<td>" + object[x].reimbAuthor + "</td>";
        txt += "<td>" + object[x].reimbResolver + "</td>";
        txt += "<td>" + object[x].reimbStatus + "</td>";
        txt += "<td>" + object[x].reimbType + "</td></tr>";
    }
    document.getElementById(id).innerHTML = txt;
}

/* 
    gets account details of current user
*/
async function getUser(){
    const result = await fetch("http://localhost:9001/api/user/get");
    let myJSON = await result.json();
    let uid = myJSON.userId;
    let uname = myJSON.username;
    let ufname = myJSON.firstName;
    let ulname = myJSON.lastName;
    let uemail = myJSON.email;
    let urole = myJSON.userRole;
    let txt = `
    <h1>Welcome ${ufname}!</h1>
    <p><b>User Id: </b><span id="userid">${uid}</span></p>
    <p><b id="username">Username: </b>${uname}</p>
    <p><b>Full Name: </b>${ufname}, ${ulname}</p>
    <p><b>Email: </b>${uemail}</P>
    <p><b>User Role: </b>${urole}</p>`  
    document.getElementById("welcome").innerHTML = txt; 
}

//Id names of all sections 
let all = "all-reimbursements";
let approved = "approved-reimbursements";
let denied = "denied-reimbursements";
let pending = "pending-reimbursements";
let submit = "new-reimbursement";

//Array containing id's of all sections
let sections = [all, approved, denied, pending, submit];

/* 
    Hides all sections except section in parameter
*/
function changeSection(section){
    revealSection(section);
    for (var i = 0; i < sections.length; i++) {
        if(sections[i]!=section){
            hideSection(sections[i]);
        }
    }
}
/* 
    Sets section to display none
*/
function hideSection(section){
    document.getElementById(section).classList.add("d-none");
}

/* 
    Removes display none from section
*/
function revealSection(section){
    document.getElementById(section).classList.remove("d-none");
}

/* 
    Changes theme attribute from light to dark
*/
let switchTheme = function switchTheme(){
    let mainContent = document.getElementById("main-content");
    if(mainContent.getAttribute("theme")=="dark"){
        mainContent.setAttribute("theme", "light");
    }else if(mainContent.getAttribute("theme")=="light"){
        mainContent.setAttribute("theme", "dark");
    }
}


window.onload = function(){
    getUser(); 
    getAllReimb();
    document.getElementById("allAcc").addEventListener("click", getAllAccounts);
    document.getElementById("search").addEventListener("keyup", search);
    document.getElementById("toggle").addEventListener('click', switchTheme);
    document.getElementById("all").addEventListener('click', getAllReimb);
    document.getElementById("approved").addEventListener('click', getApprovedReimb);
    document.getElementById("denied").addEventListener('click', getDeniedReimb);
    document.getElementById("pending").addEventListener('click', getPendingReimb);
}

/* 
    Retrieves all reimbursements for current account
*/
let getAllReimb = async function getAllReimb(){
    changeSection(allReimb);
    const result = await fetch("http://localhost:9001/api/admin/reimbursements/all");
    let myJSON = await result.json();
    changeTable(myJSON, "allReimb");
}

/* 
    Retrieves all registered accounts
*/
let getAllAccounts = async function getAllAccounts(){
    changeSection(allAccounts);
    const result = await fetch("http://localhost:9001/api/admin/getall");
    let myJSON = await result.json();
    changeAccountsTable(myJSON, "allAccounts");
}

/* 
    Gets all approved reimbursements for current account
*/
let getApprovedReimb = async function getApprovedReimb(){
    changeSection(approvedReimb);
    const result = await fetch("http://localhost:9001/api/admin/reimbursements/approved");
    let myJSON = await result.json();
    changeTable(myJSON, "approvedReimb");
}

/* 
    Retrieves all pending reimbursements for current account
*/
let getDeniedReimb = async function getDeniedReimb(){
    changeSection(deniedReimb);
    const result = await fetch("http://localhost:9001/api/admin/reimbursements/denied");
    let myJSON = await result.json();
    changeTable(myJSON, "deniedReimb");
}

/* 
    Retrieves all pending reimbursemetns for current account
*/
let getPendingReimb = async function getPendingReimb(){
    changeSection(pendingReimb);
    const result = await fetch("http://localhost:9001/api/admin/reimbursements/pending");
    let myJSON = await result.json();
    changeTable(myJSON, "pendingReimb");
}

/* 
    Inputs account information into the accounts table
*/
function changeAccountsTable(object, id){
    let txt = "";
    for (let x = 0; x < object.length; x++) {
        txt += `<tr><td id="tr${object[x].userId}">` +  object[x].userId + "</td>";
        txt += "<td>" + object[x].username + "</td>";
        txt += "<td>" + object[x].firstName + "</td>";
        txt += "<td>" + object[x].lastName + "</td>";
        txt += "<td>" + object[x].email + "</td>";
        if(object[x].userRole=="Employee"){
            txt += "<td>" + `<input id="rid${object[x].userId}" type="checkbox" value="Finance Manager"><label"> Admin</label>` + "</td>";
        }else if(object[x].userRole=="Finance Manager"){
            txt += "<td>" + `<input id="rid${object[x].userId}" type="checkbox" checked value="Finance Manager"><label"> Admin</label>` + "</td>";
        }
        txt += "<td>" + `<h2 class="accordion-header" id="headingOne">
        <button class="accordion-button" type="button" data-bs-toggle="collapse" data-bs-target="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
          Accordion Item #1
        </button>
      </h2>` + "</td>";
    }
    document.getElementById(id).innerHTML = txt;
}

/* 
    Updates the user role of the specified account
*/
async function updateAccount(account){
    let id = document.getElementById(account).textContent;
    let value = "";
    if(document.getElementById(`rid${id}`).checked){
        value = "Finance Manager";
    }else{
        value = "Employee";
    }
    console.log(value);
    let userid = JSON.stringify({
        "userId" : id,
        "userRole" : value
    });
    let response = await fetch("http://localhost:9001/api/user/update/role", {
        method: "PUT",
        body: userid
    })
    let result = await response.json();
    if(result==true){
        alert("Account updated!");
        getAllAccounts(); //refreshes the table
    }else{
        alert("Account not updated!")
        getAllAccounts();//refreshes the table
    }
}

/* 
    Inputs reimbursemnt information into appropriate table
*/
function changeTable(object, id){
    let txt = "";
    for (let x = 0; x<object.length; x++) {
        if(id=="pendingReimb"){
            txt += `<tr><td id="p${object[x].reimbId}">` +  object[x].reimbId + "</td>";
        }else{
            txt += "<tr><td>" +  object[x].reimbId + "</td>";
        }
        txt += "<td>$" + object[x].reimbAmount + "</td>";
        txt += "<td>" + object[x].reimbSubmitted + "</td>";
        txt += "<td>" + object[x].reimbResolved + "</td>";
        txt += "<td>" + object[x].reimbDescription + "</td>";
        if(object[x].receipt!=null){
            txt += `<td><a href="` +"/html/api/receipts/"+ object[x].reimbId +`.jpg">Receipt</td>`;
        }else{
            txt += `<td>No Receipt</td>`;
        }
        txt += "<td>" + object[x].reimbAuthor + "</td>";
        txt += "<td>" + object[x].reimbResolver + "</td>";
        txt += "<td>" + object[x].reimbStatus + "</td>";
        if(id=="pendingReimb"){
            txt += "<td>" + object[x].reimbType + "</td>";
            txt += "<td>" + `<select id="sel${object[x].reimbId}"><option>Approve</option><option>Deny</option></select>` + "</td>";
            txt += "<td>" + `<button onclick="submit('p${object[x].reimbId}')">Submit</button>` + "</td></tr>";
        }else{
            txt += "<td>" + object[x].reimbType + "</td></tr>";
        }
        
    }
    document.getElementById(id).innerHTML = txt;
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
    Submits decision to either approve or deny a request based on the user selection.
*/
async function submit(reimb){
    let id = document.getElementById(reimb).innerHTML;
    let value = "";
    if(document.getElementById(`sel${id}`).value=="Approve"){
        value="Approved";
    }else{
        value = "Denied";
    }
    let status = JSON.stringify({
        "reimbId" : id,
        "reimbStatus" : value
    });
    let response = await fetch("http://localhost:9001/api/admin/reimbursements/update", {
        method: "PUT",
        body: status
    })
    let result = await response.json();
    if(result==true){
        alert("Account updated!");
        getPendingReimb(); //refreshes the table
    }else{
        alert("Account not updated!");
        getPendingReimb(); //refreshes the table
    }
        
}

/* 
    Toggles light theme and dark theme
*/
let switchTheme = function switchTheme(){
    let mainContent = document.getElementById("main-content");
    if(mainContent.getAttribute("theme")=="dark"){
        mainContent.setAttribute("theme", "light");
    }else if(mainContent.getAttribute("theme")=="light"){
        mainContent.setAttribute("theme", "dark");
    }
}
/* 
    Prints a welcome message for current user
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
    <p><b id="userid">User Id: </b>${uid}</p>
    <p><b id="username">Username: </b>${uname}</p>
    <p><b>Full Name: </b>${ufname}, ${ulname}</p>
    <p><b>Email: </b>${uemail}</P>
    <p><b>User Role: </b>${urole}</p>`  
    document.getElementById("welcome").innerHTML = txt; 
}

//The names of all id tags that I want to hide or unhide
let allReimb = "all-reimbursements";
let approvedReimb = "approved-reimbursements";
let deniedReimb = "denied-reimbursements";
let pendingReimb = "pending-reimbursements";
let allAccounts = "all-accounts";
//An array containing all the names of id's I want to hide or unhide
let sections = [allReimb, approvedReimb, deniedReimb, pendingReimb, allAccounts];

/* 
    Hides all sections except for section in parameter
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
    Function to hide section
*/
function hideSection(section){
    document.getElementById(section).classList.add("d-none");//"d-none" is the bootstrap class for "display: none;"
}

/* 
    Function to unhide section
*/
function revealSection(section){
    document.getElementById(section).classList.remove("d-none");//"d-none" is the bootstrap class for "display: none;"
}

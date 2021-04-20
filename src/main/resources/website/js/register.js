window.onload = function(){
    document.documentElement.setAttribute("theme", "dark");
    document.getElementById('register').addEventListener('click', register);
    document.getElementById('password2').addEventListener('keyup', matches);
}

//-----------Shows whether or not passwords match-----------//

let matches = function matches(){
let firstValue = document.getElementById('password').value;
let secondValue = document.getElementById('password2').value;
  if (firstValue==secondValue || firstValue=="" || secondValue == ""){
    removeInvalidMessage();
    passwordsMAtch = true;
  }else { 
      if(messageAdded==false){
      addInvalidMessage();
      passwordsMAtch = false;
    }
  }
}

let passwordsMAtch = false;
let messageAdded = false;
let loginDiv = document.getElementById('login-box'); //Going to add message if passwords don't match
let passwordDiv = document.getElementById('password2'); //Going to change to red if passwords don't match
let message = document.createTextNode('Passwords dont match!');
let div = document.createElement('div');
let final = div.appendChild(message);

function addInvalidMessage() {
  div.setAttribute("id","invalid-pass");
  passwordDiv.classList.add("invalid");
  loginDiv.appendChild(final);
  messageAdded = true;
}

function removeInvalidMessage(){
  passwordDiv.classList.remove("invalid");
  final.remove();
  messageAdded = false;
}

//-----------Attempts to register user on server-----------//

let register = function register(event){

  event.preventDefault();
  
  let username = document.getElementById('username').value;
  let password = document.getElementById('password').value;
  let firstname = document.getElementById('firstname').value;
  let lastname = document.getElementById('lastname').value;
  let email = document.getElementById('email').value;

  let input = JSON.stringify({
    "username"  : username, 
    "password"  : password,
    "firstName" : firstname,
    "lastName"  : lastname,
    "email"     : email
  })
  
  let request = new XMLHttpRequest();

  request.onreadystatechange = function(){

    if(request.readyState==4 && request.status==200){
      
      const response = request.responseText;

      if(response=="username taken"){
          usernameTaken();
      }else if(response=="user added"){
          window.alert("User added!");
          window.location = "/";
      }else if(response=="user not added"){
        window.alert("User not added!");
      }else{
        window.alert("Error");
      }
    }
  }
  
  //------------Prevents user from sending form if passwords dont match or a field is empty-----------------------//

  if(username == "" || password == "" || firstname == "" || lastname == "" || email == ""){
    window.alert("All fields are required!");
    return;
  }else if (passwordsMAtch == false){
    window.alert("Passwords dont match!");
    return;
  }

  request.open("POST", "http://localhost:9001/register");
  request.setRequestHeader("Content-Type", "application/json");
  request.send(input);
}

function usernameTaken(){
    window.alert("Username taken!")
}






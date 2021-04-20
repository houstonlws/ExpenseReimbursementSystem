window.onload = function(){
    document.documentElement.setAttribute("theme", "dark");
    document.getElementById('login').addEventListener('click', login);
}

let login = function login(event){

  event.preventDefault();
  
  let username = document.getElementById('username').value;
  let password = document.getElementById('password').value;
  
  let input = JSON.stringify({
    "username" : username, 
    "password" : password
  })
  
  let request = new XMLHttpRequest();

  request.onreadystatechange = function(){

    if(request.readyState==4 && request.status==200){
      
      const response = request.responseText;

      if(response=="admin"){
        window.location = "/api/admin"
      }else if(response=="user"){
        window.location = "api/user"
      }else if(response=="false"){
        invalidResponse();
      }
    }
  }
  
  request.open("POST", "http://localhost:9001/login");
  request.setRequestHeader("Content-Type", "application/json");
  request.send(input);
}

let loginDiv = document.getElementById('login-box');
let message = document.createTextNode('Invalid username or password!');
let div = document.createElement('div');
let final = div.appendChild(message);

function invalidResponse(){
  div.setAttribute("id","invalid-pass");
  loginDiv.insertBefore(final, loginDiv.firstChild);
}






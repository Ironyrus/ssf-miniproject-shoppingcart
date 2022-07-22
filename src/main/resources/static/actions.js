function changeColor(newColor) {
    const elem = document.getElementById('para');
    elem.style.color = newColor;
  }

var count = 1;
function deleteItem() {
    var item = document.getElementById("item").innerHTML;
    console.log("Hello World");
    console.log(item);
    console.log(count);
    count++;
  }

  function sortItem() {
    var item = document.getElementById("item").innerText;
    console.log(item);

    const newInput = document.createElement("input");
    const newContent = document.createTextNode("Hi");
    newInput.appendChild(newContent);
    const currentInput = document.getElementById("jss");

    currentInput.setAttribute('type', 'text');

    document.body.insertBefore(newInput, currentInput);
  }

  function loadDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {
       document.getElementById("demo").innerHTML = this.responseText;
      }
    };
    xhttp.open("GET", "ajax_info.txt", true);
    xhttp.send();
  }
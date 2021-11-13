
function add(f){
//    alert("files.files[0].value " + f.files[0].name);
//    alert("files.value " + f.value);
//    alert("f.length " + f.files.length);
//    alert("f.length " + f.files);



    sendFiles(f);
    addTableRowsLoop(getFiles());

}

function sendFiles(f){
    var xhr = new XMLHttpRequest();
    var formData = new FormData();

    for(var i = 0; i < f.files.length; i++){
            formData.append("files", f.files[i]);
        }
    xhr.open( "POST", "/restuploadFile", false);
    xhr.send(formData);
}

function getFiles(){
    var xhr = new XMLHttpRequest();

    xhr.open( "GET", "/resthome", false);
    xhr.send(null);
    var res = xhr.responseText;
    var fil = JSON.parse(res);

    return fil;
}

function addTableRowsLoop(fil){
    var pdfFileTableRows = document.getElementById("pdfFileTable").rows;

        if(pdfFileTableRows.length == 1){
            for(var i = 0; i < fil.length; i++){
    //            alert(fil[i])
                addTableRows(fil[i]);
            }
        }else{
            top:
            for(var i = 0; i < fil.length; i++){
                var count = 0;
                for(var j = 1; j < pdfFileTableRows.length; j++){
//                    alert("pdfFileTableRows[" + j + "].cells.item(0) = " + pdfFileTableRows[j].cells.item(0).innerHTML);
                    if(fil[i].substring(fil[i].lastIndexOf('\\')+1) == pdfFileTableRows[j].cells.item(0).innerHTML){
                        count++;
                        continue top;
                    }
                }
//                alert("cokolwiek");
//                alert(count);
                if(count == 0){
                    addTableRows(fil[i]);
                }
            }
        }
}

function addTableRows(fName){
    var table = document.getElementById("pdfFileTable");
    var row = table.insertRow(table.rows.length);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);

    var fileGetName = fName.substring(fName.lastIndexOf('\\')+1);
    cell1.innerHTML = fileGetName;
//    cell2.innerHTML = "<form action='/deleteFile?filename=" + fileGetName + "' method='post'><button type='submit' title='delete this file'>delete</button></form>";
//    var fileGetName2 = "'" + fileGetName + "'";
//    cell2.innerHTML = "<button onclick='te(\"" + fileGetName + "\")'>button</button>";
    cell2.innerHTML = "<button onclick='deleteFileName(\"" + fileGetName + "\")'>delete</button>"
}

function deleteFileName(fName){
    var xhr = new XMLHttpRequest();

    alert("start");

    xhr.open( "POST", "/restdeleteFile?filename=" + fName, false);
    xhr.send(null);

    alert("stop");
}

function te(fileGetName){
    alert(fileGetName);
}



function test(){

var sss =  "/deleteFile?filename=testMill.pdf";

    alert("hello");
    var table = document.getElementById("myTable");
    var row = table.insertRow(table.rows.length);
    var cell1 = row.insertCell(0);
    var cell2 = row.insertCell(1);

    cell1.innerHTML = "dd";
    cell2.innerHTML = "<form th:action='@{/deleteFile(filename=${file.getName()})}' method='post'><button type='submit' title='delete this file'>delete</button></form>";



}

function ddd(){
row.appendChild(col);
    col.innerHTML = "test";
    row.appendChild(col);
    col.innerHTML = "test2";
    table.appendChild(row);
var s = "<form th:action='@{/deleteFile(filename=${file.getName()})}' method='post'><button type='submit' title='delete this file'>delete</button></form>";

    var row = document.createElement('tr');
        var col = document.createElement('td');






        row.innerHTML = "<td>test1</td><td>test2</td>";
        table.appendChild(row);
}
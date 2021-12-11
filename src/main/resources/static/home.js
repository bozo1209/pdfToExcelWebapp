
function add(f){
    sendFiles(f);
    addTableRowsLoop(getFiles());
    clearFileInput();
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
                addTableRows(fil[i]);
            }
        }else{
            top:
            for(var i = 0; i < fil.length; i++){
                var count = 0;
                for(var j = 1; j < pdfFileTableRows.length; j++){
                    if(fil[i].substring(fil[i].lastIndexOf('\\')+1) == pdfFileTableRows[j].cells.item(0).innerText){
                        count++;
                        continue top;
                    }
                }
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
    cell2.innerHTML = "<button class='buttonStyle textStyle' onclick='deleteFileName(\"" + fileGetName + "\")'>delete</button>"
}

function deleteFileName(fName){
    var xhr = new XMLHttpRequest();

    xhr.open( "POST", "/restdeleteFile?filename=" + fName, false);
    xhr.send(null);

    deleteRowFromTable(fName);
}

function deleteRowFromTable(fName){
    var table = document.getElementById("pdfFileTable");
    var tableRows = table.rows;

    for(var i = tableRows.length - 1; i > 0; i--){
        if(tableRows[i].cells.item(0).innerText == fName){
            table.deleteRow(i);
        }
    }
}

function deleteAllRows(){
    var table = document.getElementById("pdfFileTable");
    var tableRows = table.rows;

    var xhr = new XMLHttpRequest();
    xhr.open( "POST", "/restdeleteSessionFolder", false);
    xhr.send(null);

    for(var i = tableRows.length - 1; i > 0; i--){
        table.deleteRow(i);
    }
}

function clearFileInput(){
    document.getElementById("filesId").value = null;
}

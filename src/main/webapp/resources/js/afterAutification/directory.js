//showing loader while loading page
$.ajaxSetup({
    beforeSend: function () {
        // show gif here, eg:
        $("#loader").css("display", "block");
        $("#container").css("display", "none");

    },
    complete: function () {
        // hide gif here, eg:
        $("#loader").css("display", "none");
        $("#container").css("display", "block");
    }
});
jQuery(document).ready(function () {
    let url=window.location.href;
    if(url.indexOf("noFile=true")>=0){
        alert("File doesn't exist! You will redirect to root folder!");
        window.location.replace("/explorer");
    }
    ajax();
});
//sending default request to server and display response by function display
function ajax() {
    let fileStructure = {};
    $.ajax
    ({
        type: "POST",
        url: "explorer",
        data: JSON.stringify(fileStructure),
        dataType:'json',
        success: function (data) {
            console.log(data);
            display(data);
        },
        error: function (e) {
            console.log("ERROR: ", e);
            display(e);
        },

    });
}


//displaying response data
function display(data) {
    //if div exist allready - delete him and write new
    let container = document.getElementById("container");
    if (container) {
        container.parentNode.removeChild(container);
    }
    //checking is folder empty
    if (data['code'] === "200") {
        let container = document.createElement('div');
        container.className = "container";
        container.id = "container";
        let a1 = document.createElement("a");
        //creating refer to parent folder "Back"
        a1.innerHTML = "Back&nbsp;&nbsp;";
        //adding function to send json to server to go to up folder? and display files in it
        a1.addEventListener("click", function () {
            let id = this.id;
            let fileStructure = {};
            fileStructure["parent"] = data['backButton'];
            $.ajax
            ({
                type: "POST",
                url: "explorer",
                data: JSON.stringify(fileStructure),
                dataType:'json',
                success: function (data) {
                    console.log(data);
                    display(data);
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                    display(e);
                },
            });
        }, false);
        //creating refer for Logout
        let a2 = document.createElement("a");
        a2.id = 'logout';
        a2.innerHTML = "Logout";
        a2.addEventListener("click", function () {
            window.location.replace("logout");
        }, false);
        let br = document.createElement("br");
        //write message
        let p = document.createElement("p");
        p.innerHTML = data['msg'];
        container.appendChild(a1);
        container.appendChild(a2);
        container.appendChild(br);
        container.appendChild(p);
        document.body.appendChild(container);
    } else {
        let fileSeparator;
        let container = document.createElement('div');
        container.className = "container";
        container.id = "container";
        let a1 = document.createElement("a");
        //creating refer to parent folder "Back"
        a1.innerHTML = "Back&nbsp;&nbsp;";
        //adding function to send json to server to go to up folder? and display files in it
        let fileStructure = {};
        fileStructure["parent"] =data['backButton'];
        a1.addEventListener("click", function () {
            $.ajax
            ({
                type: "POST",
                url: "explorer",
                data: JSON.stringify(fileStructure),
                dataType:'json',
                success: function (data) {
                    console.log(data);
                    display(data);
                },
                error: function (e) {
                    console.log("ERROR: ", e);
                    display(e);
                },
            });
        }, false);
        //creating refer for Logout
        let a2 = document.createElement("a");
        a2.id = 'logout';
        a2.innerHTML = "Logout";
        a2.addEventListener("click", function () {
            window.location.replace("logout");
        }, false);
        let br = document.createElement("br");
        container.appendChild(a1);
        container.appendChild(a2);
        container.appendChild(br);
        let table=document.createElement("table");
        table.className="table";
        container.appendChild(table);
        let thead=document.createElement("thead");
        table.appendChild(thead);
        let tr=document.createElement("tr");
        thead.appendChild(tr);
        let th1=document.createElement("th");
        th1.innerHTML="File kind";
        tr.appendChild(th1);
        let th2=document.createElement("th");
        th2.innerHTML="File name";
        tr.appendChild(th2);
        let th3=document.createElement("th");
        th3.innerHTML="File size";
        tr.appendChild(th3);
        //write list of files in folder by folders and files and by names
        for (let i = 0; i < data['files'].length; i++) {
            let tbody=document.createElement("tbody");
            table.appendChild(tbody);
            let tr1=document.createElement("tr");
            tbody.appendChild(tr1);
            let td1=document.createElement("td");
            tr1.appendChild(td1);
            let td2=document.createElement("td");
            tr1.appendChild(td2);
            let td3=document.createElement("td");
            tr1.appendChild(td3);
            let f = document.createElement("i");
            f.setAttribute("aria-hidden", "true");
            //adding function to send json to server to go to up folder? and display files in it
            if (data['files'][i]['directory']) {
                f.className = "fa fa-folder";
                let a3 = document.createElement("a");
                a3.innerHTML = "&nbsp;" + data['files'][i]["fileName"];
                a3.id = i;
                let fileStructure = {};
                fileStructure["path"] =   data['files'][i]["fileName"];
                fileStructure["parent"] =   data['parent'];
                console.log(fileStructure["path"]);
                a3.addEventListener("click", function () {
                    $.ajax
                    ({
                        type: "POST",
                        url: "explorer",
                        data: JSON.stringify(fileStructure),
                        dataType:'json',
                        success: function (data) {
                            console.log(data);
                            display(data);
                        },
                        error: function (e) {
                            console.log("ERROR: ", e);
                            display(e);
                        },
                    });
                }, false);
                let br1 = document.createElement("br");
                td1.appendChild(f);
                td2.appendChild(a3);
                td3.innerHTML= data['files'][i]["size"]+" bytes";
            } else {
                f.className = "fa fa-file";
                let a4 = document.createElement("a");
                a4.innerHTML = "&nbsp;" + data['files'][i]["fileName"];
                a4.id = i;
                let url = "download?fullPath=" + encodeURIComponent(data['parent']) + "&fileName=" + data['files'][i]["fileName"];
                //adding function to send json to server to download the file
                a4.addEventListener("click", function () {
                    console.log(url);
                    window.location.replace(url);
                }, false);
                let br1 = document.createElement("br");
                td1.appendChild(f);
                td2.appendChild(a4);
                td3.innerHTML= data['files'][i]["size"]+" bytes";
            }
        }
        document.body.appendChild(container);
    }
}
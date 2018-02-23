jQuery(document).ready(function($) {
    $("#login-form").submit(function(event) {
        // Prevent the form from submitting via the browser.
        event.preventDefault();
        loginViaAjax();
    });

});

function loginViaAjax() {
    //creating user hash and sending it via ajax, in success section call function display - if user is invalid? else redirect to default root
    let user = {};
    user["userEmail"] = $("#userEmail").val();
    user["userPassword"] = $("#userPassword").val();
    $.ajax({
        type: "POST",
        url: "login",
        data: JSON.stringify(user),
        dataType:'json',
        success : function(data) {
            if(data['code']==="200"){
                location.href = "explorer";
            }else{
                display(data['msg']);
            }
        },
        error : function(e) {
            console.log("ERROR: ", e);
            display(e);
        },

    });
}


function display(data) {
    let json = "<pre>" + data + "</pre>";
    $('#feedback').html(json);
}
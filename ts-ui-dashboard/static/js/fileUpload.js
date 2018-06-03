$("#upload").click(function() {

    $.ajax({
        url: "/adminuser/upload",
        type: 'POST',
        cache: false,
        data: new FormData($("#uploadForm")[0]),
        processData: false,
        contentType: false,
        success: function (result) {
            alert("Success");
        },
        error: function (err) {
            alert("Upload User List Fail.");
        }
    });

});

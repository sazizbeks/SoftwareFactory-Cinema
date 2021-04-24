$( document ).ready(function() {

    $('#btnFav').click(function (){

        var movieId = $('#movieId').val();
        var userId = $('#userId').val();
        var data0 = {userId: userId, movieId: movieId};

        console.log(movieId,userId);
        $.ajax({
            type: "POST",
            contentType: "application/json",
            dataType: "json",
            url: "/update-favorite",
            data: JSON.stringify(data0),
            success: function (data){
                console.log(data)
                if (data.added)
                    $('#favLogo').attr('src','/img/fav-pressed.png');
                else
                    $('#favLogo').attr('src','/img/fav.png');
            }
        });
    });
});
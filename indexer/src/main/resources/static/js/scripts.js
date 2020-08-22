function redirectTo()
{
    $.ajax({
        url: '/loading',
        type: 'GET',
        success: function(response){
            $(".loading-icon").fadeOut("slow", function(){
                window.location = response.redirect_url;
            });
        }
    });
}


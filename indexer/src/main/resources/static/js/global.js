function loaded()
{
    $.ajax({
        url: '/loaded',
        type: 'GET',
        success: function(response)
        {
            if(!response.loaded)
            {
                window.location = "/";
            }
        }
    });
}

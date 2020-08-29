$(document).ready(function() 
{
    $("#search-input").keyup(function(event)
    {
        if (event.keyCode === 13) 
        {
            event.preventDefault();
            search();
        }
    });
});

function search()
{
    window.location.href = "/result?q=" + $("#search-input").val().replaceAll(" ", ",");
}
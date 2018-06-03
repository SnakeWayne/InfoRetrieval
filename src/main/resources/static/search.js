function search() {
    var str = $("#search-input").val();
    var url = "/InfoRetrieval/lyric";
    var json_data = {"keywords": str};
    $.ajax({
        type:'post',
        url:url,
        contentType:'application/json;charset=utf-8',
        data: JSON.stringify(json_data),
        success:function (data) {
            showLyric(data)
        }
    });

}

function showLyric(data) {
    var lastName
    $("#result").remove()
    var addf = '<div id = "result">';
    for (var i = 0; i < data.length; i ++) {
        var fileName = data[i].fileName;
        var words = data[i].word;
        addf += '<h4>' +words+ ":\t "+fileName +'</h4>';
    }
    addf += '</div>'
    $("#baseline").after(addf);
}
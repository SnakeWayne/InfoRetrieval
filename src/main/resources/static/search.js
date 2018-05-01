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
            window.location.href = "index.html#lyric";
            showLyric(data)
        }
    });

}

function showLyric(data) {
    var lastName
    for (var i = 0; i < data.size; i++) {
        var temp = data[i];

    }
}
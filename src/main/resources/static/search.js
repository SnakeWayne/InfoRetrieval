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

function docSearch() {
    var str = $("#search-input").val();
    var url = "/InfoRetrieval/doc";
    var json_data = {"keywords": str};
    $.ajax({
        type:'post',
        url:url,
        contentType:'application/json;charset=utf-8',
        data: JSON.stringify(json_data),
        success:function (data) {
            showDocRank(data)
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

function showDocRank(data) {

    $("#result").remove();
    var addf = '<div id="result">';
    for (var i = 0; i < data.length; i += 3) {
        addf += '<div class="row">';
        for(var j = i; j < data.length && j < i + 3; j++) {
            addf += '<div class="col-lg-4">\n' +
                '            <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">\n' +
                '<div class="features-icons-icon d-flex">\n' +
                '                <i class="icon-layers m-auto text-primary"></i>\n' +
                '              </div>'+
                '              <h3>'+data[j].doc+'</h3>\n' +
                '              <p class="lead mb-0">'+data[j].rank+'</p>\n' +
                '            </div>\n' +
                '          </div>'
        }
        addf += '</div>';
    }
    addf += '</div>';
    $("#resultsLine").before();
}
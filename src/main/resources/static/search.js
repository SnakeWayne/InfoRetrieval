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
    var url = "/InfoRetrieval/plmSearch";
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

function docSimilarity() {
    var str1 = $("#search-input1").val();
    var str2 = $("#search-input2").val();
    var json_data = {"doc1": str1,
    "doc2": str2};
    var url  = "/InfoRetrieval/similarity";
    $.ajax({
        type:'post',
        url:url,
        contentType:'application/json;charset=utf-8',
        data: JSON.stringify(json_data),
        success:function (data) {
            var addf = '<div class="row">\n' +
                '          <div class="col-lg-6">\n' +
                '            <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">\n' +
                '              <div class="features-icons-icon d-flex">\n' +
                '                <i class="icon-screen-desktop m-auto text-primary"></i>\n' +
                '              </div>\n' +
                '              <h3>采用w(i,j)计算文档相似度</h3>\n' +
                '              <p class="lead mb-0">'+data[0]+'</p>\n' +
                '            </div>\n' +
                '          </div>\n' +
                '          <div class="col-lg-6">\n' +
                '            <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">\n' +
                '              <div class="features-icons-icon d-flex">\n' +
                '                <i class="icon-layers m-auto text-primary"></i>\n' +
                '              </div>\n' +
                '              <h3>采用wf计算文档相似度</h3>\n' +
                '              <p class="lead mb-0">'+data[1]+'</p>\n' +
                '            </div>\n' +
                '          </div>' +
                '</div>'
            $("#baseline").after(addf);
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

            var lyric = data[j].lyric;
            if (lyric.length > 200) {
                lyric = lyric.substring(0, 100) +"...";
            }
            addf += '<div class="col-lg-4">\n' +
                '            <div class="features-icons-item mx-auto mb-5 mb-lg-0 mb-lg-3">\n' +
                '<div class="features-icons-icon d-flex">\n' +
                '                <i class="icon-layers m-auto text-primary"></i>\n' +
                '              </div>'+
                '              <h3>'+data[j].doc+'</h3>\n' +
                '              <p class="lead mb-0">'+lyric+'</p>\n' +
                '            </div>\n' +
                '          </div>'
        }
        addf += '</div>';
    }
    addf += '</div>';
    $("#resultsLine").before(addf);
}
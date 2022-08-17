/*
게시글 등록 화면(/posts/save)은있는데 등록 버튼이 없다.
그래서 JS로 API를 호출한다.

var main
- index라는 변수의 속성으로 function을 추가한 이유 : index.js만의 유효범위를 만들기 위해.
- 함수의 중복으로 전혀 다른 함수를 사용하는 현상 방지
- 이렇게 하면 index 객체 안에서만 function이 유효 → 다른 js와 겹칠 위험 사라짐.
*/

var main = {
    init : function() {
        var _this = this;
        $('#btn-save').on('click', function() {
            _this.save();
        });
    },
    save : function() {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';     // 글 등록 성공 시 메인 페이지(/)로 이동
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }

};

main.init();
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
        /*
        $('#btn-update').on('click')
        - btn-update란 id를 가진 HTML 엘리먼트에 click 이벤트가 발생할 때
          update function을 실행하도록 이벤트 등록

        */
        $('#btn-update').on('click', function() {
            _this.update();
        })

        $('#btn-delete').on('click', function() {
            _this.delete();
        })
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
    },
    /*
    update : function()
    - posts-update.mustache에서 btn-update 버튼 클릭 시 실행

    type: 'PUT'
    - HTTP Method에서 PUT 메소드
    - PostsApiController에 있는 API에서 이미 @PutMapping으로 선언했기 때문에 PUT을 사용해야 한다.
      (REST 규약에 맞게 설정됨)
    - REST에서 CRUD는 아래와 같이 HTTP Method에 매핑됨
      생성(Create) - POST
      읽기(Read) - GET
      수정(Update) - PUT
      삭제(Delete) - DELETE
    */
    update : function () {
        var data = {
            title: $('#title').val(),
            content: $('#content').val()
        };
        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/'+id,   // 어느 게시글을 수정할지 URL Path로 구분하기 위해 Path에 id 추가
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },

    /*
    delete: function()
    - posts-update.mustache에서 btn-delete 버튼 클릭 시 실행
    */
    delete : function () {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }


};

main.init();
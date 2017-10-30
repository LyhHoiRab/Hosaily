app.controller('postEditController', function($scope, $state, $stateParams, FileUploader){
    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/post/upload',
        queueLimit: 1,
        method: 'POST',
        removeAfterUpload: true
    });

    uploader.filters.push({
        name: 'imageFilter',
        fn: function(item){
            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
            return 'jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    });

    uploader.onSuccessItem = function(item, response, status, headers){
        $scope.course.cover = response.result;

        if(!$scope.$$phase){
            $scope.$apply();
        }
    };

    $('.selectpicker').selectpicker({
        title: '请选择'
    });

    var editor = CKEDITOR.replace('editor', {
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    });

    $scope.id = $stateParams.id;
    $scope.post = {
        id           : $scope.id,
        title        : '',
        type         : 0,
        kind         : 1,
        summary      : '',
        introduction : '',
        state        : '',
        cover        : '/commons/img/level_default.jpg',
        price        : 0,
        likes        : 0,
        view         : 0,
        sort         : 0,
        advisor      : {id : ''},
        //children     : [],
        media        : []
    };

    $scope.reset = function(){
        $scope.post.title         = '';
        $scope.post.summary       = '';
        $scope.post.introduction  = '';
        $scope.post.state         = '';
        $scope.post.price         = 0;
        $scope.post.likes         = 0;
        $scope.post.view          = 0;
        $scope.post.sort          = 0;
        $scope.post.advisor.id    = '';
        //$scope.post.children      = [];
        $scope.post.media         = [];

        $('.selectpicker').selectpicker('deselectAll');
        editor.setData('');
    };

    $scope.getById = function(){
        $.ajax({
            url: '/api/1.0/post/' + $scope.id,
            dataType: 'JSON',
            type: 'GET',
            success: function(res){
                if(res.success){
                    utils.copyOf(res.result, $scope.post);
                    editor.setData($scope.post.introduction);

                    var medias = [];
                    angular.forEach($scope.post.media, function(data){
                        medias.push(data.id);
                    });
                    $('#medias').selectpicker('val', medias);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.submit = function(){
        //推荐课程
        //var children = $('#courses').val();
        //$scope.post.children = [];
        //if(children !== null && children.length > 0){
        //    angular.forEach(children, function(data){
        //        $scope.post.children.push({id:data});
        //    });
        //}

        var medias = $('#medias').val();
        $scope.post.media = [];
        if(medias !== null && medias.length > 0){
            angular.forEach(medias, function(data){
                $scope.post.media.push({id:data});
            });
        };

        $scope.post.introduction = editor.getData();

        $.ajax({
            url: '/api/1.0/post',
            type: 'PUT',
            data: JSON.stringify($scope.post),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('post');
                }
            }
        });
    };

    $scope.getById();
});
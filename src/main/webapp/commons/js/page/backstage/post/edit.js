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

    editorInit();
    var ue = UE.getEditor('editor', {
        initialFrameHeight: 450,
        serverUrl: ''
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
        children     : []
    };

    $scope.reset = function(){
        $scope.post.title         = '';
        $scope.post.summary       = '',
        $scope.post.introduction  = '';
        $scope.post.state         = '';
        $scope.post.price         = 0;
        $scope.post.likes         = 0;
        $scope.post.view          = 0;
        $scope.post.sort          = 0;
        $scope.post.advisor.id    = '';
        $scope.post.children      = [];

        $('.selectpicker').selectpicker('deselectAll');
        ue.setContent('');
    };

    $scope.getById = function(){
        $.ajax({
            url: '/api/1.0/post/' + $scope.id,
            dataType: 'JSON',
            type: 'GET',
            success: function(res){
                if(res.success){
                    utils.copyOf(res.result, $scope.post);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.submit = function(){
        var children = $('#courses').val();
        //推荐课程
        $scope.post.children = [];
        if(children !== null && children.length > 0){
            angular.forEach(children, function(data){
                $scope.post.children.push({id:data});
            });
        }
        $scope.post.introduction = ue.getContent();

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

     ue.addListener('ready', function(){
         ue.setContent($scope.post.introduction);
     });
});
app.controller('postEditController', function($scope, $state, $stateParams, FileUploader){
    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/course/upload',
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
        weight       : 0,
        comments     : 0,
        advisor      : {},
        tag          : []
    };

    $scope.reset = function(){
        $scope.post.title         = '';
        $scope.post.summary       = '',
        $scope.post.introduction  = '';
        $scope.post.state         = '';
        $scope.post.price         = 0;
        $scope.post.likes         = 0;
        $scope.post.view          = 0;
        $scope.post.weight        = 0;
        $scope.post.comments      = 0;
        $scope.post.advisor       = {};
        $scope.post.tag           = [];

        $('.selectpicker').selectpicker('deselectAll');
        ue.setContent('');
    };

    $scope.getPostById = function(){
        $.ajax({
            url: '/api/1.0/course/post/' + $scope.id,
            dataType: 'JSON',
            type: 'GET',
            success: function(res){
                if(res.success){
                    utils.copyOf(res.result, $scope.post);

                    var tags = [];
                    angular.forEach($scope.post.tag, function(data){
                        tags.push(data.id);
                    });

                    $('#tags').selectpicker('val', tags);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.submit = function(){
        var tags = $('#tags').val();
        var advisor = $('#advisor').val();

        if(tags !== null && tags.length > 0){
            angular.forEach(tags, function(data){
                $scope.post.tag.push({id:data});
            });
        };
        $scope.post.introduction = ue.getContent();

        $.ajax({
            url: '/api/1.0/course',
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

    $scope.getPostById();

    ue.addListener('ready', function(){
        ue.setContent($scope.post.introduction);
    });
});
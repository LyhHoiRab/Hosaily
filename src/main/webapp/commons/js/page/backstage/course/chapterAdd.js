app.controller('chapterAddController', function($scope, $state, $stateParams, FileUploader){
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

    $scope.parentId = $stateParams.parentId;

    $scope.course = {
        parentId     : $scope.parentId,
        title        : '',
        type         : 1,
        kind         : 0,
        summary      : '',
        introduction : '',
        state        : '',
        cover        : '/commons/img/level_default.jpg',
        likes        : 0,
        view         : 0,
        weight       : 0,
        comments     : 0,
        price        : 0
    };

    $scope.reset = function(){
        $scope.course.title         = '';
        $scope.course.summary       = '',
        $scope.course.introduction  = '';
        $scope.course.state         = '';
        $scope.course.likes         = 0;
        $scope.course.view          = 0;
        $scope.course.weight        = 0;
        $scope.course.comments      = 0;

        $('.selectpicker').selectpicker('deselectAll');
    };

    $scope.submit = function(){
        $scope.course.introduction = ue.getContent();

        $.ajax({
            url: '/api/1.0/course',
            type: 'POST',
            data: JSON.stringify($scope.course),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('courseEdit', {id : $scope.parentId});
                }
            }
        });
    };
});
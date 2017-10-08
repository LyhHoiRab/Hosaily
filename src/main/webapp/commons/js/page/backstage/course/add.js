app.controller('courseAddController', function($scope, $state, FileUploader){
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
            return 'jpeg|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    });

    uploader.onSuccessItem = function(item, response, status, headers){
        $scope.course.cover = response.result;

        if(!$scope.$$phase){
            $scope.$apply();
        }
    };

    $scope.course = {
        title        : '',
        type         : 0,
        introduction : '',
        state        : '',
        cover        : basePath + '/commons/img/level_default.jpg',
        price        : '',
        likes        : '',
        view         : '',
        weight       : '',
        tag          : [],
        advisor      : {},
        media        : []
    };


    $scope.reset = function(){
        $scope.source.title         = '';
        $scope.source.introduction  = '';
        $scope.source.state         = '';
        $scope.source.price         = '';
        $scope.source.likes         = '';
        $scope.source.view          = '';
        $scope.source.weight        = '';
        $scope.source.tag           = [];
        $scope.source.advisor       = {};
        $scope.source.media         = [];
    };

    $scope.submit = function(){
        $.ajax({
            url: '/api/1.0/course',
            type: 'POST',
            data: JSON.stringify($scope.course),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('course');
                }
            }
        });
    };
});
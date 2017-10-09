app.controller('courseAddChapterController', function($scope, $state, $stateParams, FileUploader){
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

    $scope.parentId = $stateParams.parentId;
    $scope.course = {
        parentId     : $scope.parentId,
        title        : '',
        type         : 1,
        introduction : '',
        state        : '',
        cover        : basePath + '/commons/img/level_default.jpg',
        price        : '',
        likes        : '',
        view         : '',
        weight       : '',
        advisor      : {},
        tag          : [],
        media        : [],
        level        : []
    };

    $scope.reset = function(){
        $scope.course.title         = '';
        $scope.course.introduction  = '';
        $scope.course.state         = '';
        $scope.course.price         = '';
        $scope.course.likes         = '';
        $scope.course.view          = '';
        $scope.course.weight        = '';
        $scope.course.advisor       = {};
        $scope.course.tag           = [];
        $scope.course.media         = [];
        $scope.course.level         = [];

        $('.selectpicker').selectpicker('deselectAll');
    };

    $scope.submit = function(){
        var levels = $('#levels').val();
        var tags = $('#tags').val();
        var medias = $('#medias').val();
        var advisor = $('#advisor').val();

        if(levels !== null && levels.length > 0){
            angular.forEach(levels, function(data){
                $scope.course.level.push({id:data});
            });
        };
        if(tags !== null && tags.length > 0){
            angular.forEach(tags, function(data){
                $scope.course.tag.push({id:data});
            });
        };
        if(medias !== null && medias.length > 0){
            angular.forEach(medias, function(data){
                $scope.course.media.push({id:data});
            });
        };
        if(advisor !== null && advisor !== ''){
            $scope.course.advisor.id = advisor;
        };

        $.ajax({
            url: '/api/1.0/course',
            type: 'POST',
            data: JSON.stringify($scope.course),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('courseEdit', {id:$scope.parentId});
                }
            }
        });
    };

    $('.selectpicker').selectpicker({
        title: '请选择'
    });
});
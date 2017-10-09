app.controller('courseEditController', function($scope, $state, $stateParams, FileUploader){
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

    $scope.id = $stateParams.id;
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
        advisor      : {},
        tag          : [],
        media        : [],
        level        : []
    };

    $scope.getById = function(){
        $.ajax({
            url: '/api/1.0/course/' + $scope.id,
            type: 'GET',
            dataType: 'JSON',
            success: function(res){
                if(res.success){
                    $scope.course = res.result;
                }

                var medias = [];
                var tags = [];
                var levels = [];

                angular.forEach($scope.course.media, function(data){
                    medias.push(data.id);
                });
                angular.forEach($scope.course.tag, function(data){
                    tags.push(data.id);
                });
                angular.forEach($scope.course.level, function(data){
                    levels.push(data.id);
                });

                $('#medias').selectpicker('val', medias);
                $('#tags').selectpicker('val', tags);
                $('#levels').selectpicker('val', levels);
                $('#advisor').val($scope.course.advisor.id);

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
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
            $scope.course.level = [];
            angular.forEach(levels, function(data){
                $scope.course.level.push({id:data});
            });
        };
        if(tags !== null && tags.length > 0){
            $scope.course.tag = [];
            angular.forEach(tags, function(data){
                $scope.course.tag.push({id:data});
            });
        };
        if(medias !== null && medias.length > 0){
            $scope.course.media = [];
            angular.forEach(medias, function(data){
                $scope.course.media.push({id:data});
            });
        };
        if(advisor !== null && advisor !== ''){
            $scope.course.advisor.id = advisor;
        };

        $.ajax({
            url: '/api/1.0/course',
            type: 'PUT',
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

    $scope.addChildren = function(){
        $state.go('courseAddChapter', {parentId : $scope.course.id});
    };

    $('.selectpicker').selectpicker({
        title: '请选择'
    });

    $scope.getById();
});
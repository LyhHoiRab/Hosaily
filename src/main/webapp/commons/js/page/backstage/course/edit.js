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
    $scope.course = {
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
        advisor      : {id: ''},
        tag          : [],
        level        : [],
        children     : [],
    };

    $scope.chapterAdd = function(){
        $state.go('chapterAdd', {parentId : $scope.id});
    };

    $scope.reset = function(){
        $scope.course.title         = '';
        $scope.course.summary       = '',
        $scope.course.introduction  = '';
        $scope.course.state         = '';
        $scope.course.price         = 0;
        $scope.course.likes         = 0;
        $scope.course.view          = 0;
        $scope.course.sort          = 0;
        $scope.course.advisor.id    = '';
        $scope.course.tag           = [];

        $('.selectpicker').selectpicker('deselectAll');
        ue.setContent('');
    };

    $scope.getCourseById = function(){
        $.ajax({
            url: '/api/1.0/course/' + $scope.id,
            dataType: 'JSON',
            type: 'GET',
            success: function(res){
                if(res.success){
                    utils.copyOf(res.result, $scope.course);

                    var tags = [];
                    angular.forEach($scope.course.tag, function(data){
                        tags.push(data.id);
                    });
                    $('#tags').selectpicker('val', tags);

                    var levels = [];
                    angular.forEach($scope.course.level, function(data){
                        levels.push(data.id);
                    });
                    $('#levels').selectpicker('val', levels);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.submit = function(){
        var tags = $('#tags').val();
        var levels = $('#levels').val();

        $scope.course.tag = [];
        if(tags !== null && tags.length > 0){
            angular.forEach(tags, function(data){
                $scope.course.tag.push({id:data});
            });
        };

        $scope.course.level = [];
        if(levels !== null && levels.length > 0){
            angular.forEach(levels, function(data){
                $scope.course.level.push({id:data});
            });
        };

        $scope.course.introduction = ue.getContent();

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

    $scope.getCourseById();

    ue.addListener('ready', function(){
        ue.setContent($scope.course.introduction);
    });
});
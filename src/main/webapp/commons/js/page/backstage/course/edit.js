app.controller('courseEditController', function($scope, $state, $stateParams, FileUploader, $http){
    //下拉
    $scope.states   = [];
    $scope.advisors = [];
    $scope.tags     = [];
    $scope.levels   = [];

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
    };

    //$('.selectpicker').selectpicker({
    //    title: '请选择'
    //});

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };

    $scope.course = {
        id           : $stateParams.id,
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
        advisor      : {},
        tag          : [],
        level        : [],
        children     : []
    };

    $scope.chapterAdd = function(){
        $state.go('chapterAdd', {parentId : $scope.course.id});
    };

    $scope.reset = function(){
        $scope.course.title         = '';
        $scope.course.summary       = '';
        $scope.course.introduction  = '';
        $scope.course.state         = '';
        $scope.course.price         = 0;
        $scope.course.likes         = 0;
        $scope.course.view          = 0;
        $scope.course.sort          = 0;
        $scope.course.advisor       = {};
        $scope.course.tag           = [];

        //$('.selectpicker').selectpicker('deselectAll');
    };

    $scope.getCourseById = function(){
        $http({
            url: '/api/1.0/course/' + $scope.course.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.course);
            }
        }).error(function(response){

        });

        //$.ajax({
        //    url: '/api/1.0/course/' + $scope.id,
        //    dataType: 'JSON',
        //    type: 'GET',
        //    success: function(res){
        //        if(res.success){
        //            utils.copyOf(res.result, $scope.course);
        //
        //            var tags = [];
        //            angular.forEach($scope.course.tag, function(data){
        //                tags.push(data.id);
        //            });
        //            $('#tags').selectpicker('val', tags);
        //
        //            var levels = [];
        //            angular.forEach($scope.course.level, function(data){
        //                levels.push(data.id);
        //            });
        //            $('#levels').selectpicker('val', levels);
        //
        //            editor.setData($scope.course.introduction);
        //        }
        //
        //        if(!$scope.$$phase){
        //            $scope.$apply();
        //        }
        //    }
        //});
    };

    $scope.getState = function(){
        $http({
            url: '/api/1.0/usingState/list',
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.states = res.result;
            }
        }).error(function(response){
            $scope.states = [];
        });
    };

    $scope.getAdvisor = function(){
        $http({
            url: '/api/1.0/advisor/list',
            method: 'POST',
            data: $.param({
                'state': 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.advisors = res.result;
            }
        }).error(function(response){
            $scope.advisors = [];
        });
    };

    $scope.getLevel = function(){
        $http({
            url: '/api/1.0/level/list',
            method: 'POST',
            data: $.param({
                'state': 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.levels = res.result;
            }
        }).error(function(response){
            $scope.levels = [];
        });
    };

    $scope.getTag = function(){
        $http({
            url: '/api/1.0/tag/list',
            method: 'POST',
            data: $.param({
                'state': 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.tags = res.result;
            }
        }).error(function(response){
            $scope.tags = [];
        });
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/course',
            method: 'PUT',
            data: JSON.stringify($scope.course),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('course');
            }else{
                alert(res.msg);
            }
        }).error(function(response){

        });

        //var tags = $('#tags').val();
        //var levels = $('#levels').val();
        //
        //$scope.course.tag = [];
        //if(tags !== null && tags.length > 0){
        //    angular.forEach(tags, function(data){
        //        $scope.course.tag.push({id:data});
        //    });
        //};
        //
        //$scope.course.level = [];
        //if(levels !== null && levels.length > 0){
        //    angular.forEach(levels, function(data){
        //        $scope.course.level.push({id:data});
        //    });
        //};
        //
        //$scope.course.introduction = editor.getData();
        //
        //$.ajax({
        //    url: '/api/1.0/course',
        //    type: 'PUT',
        //    data: JSON.stringify($scope.course),
        //    dataType: 'JSON',
        //    contentType: 'application/json',
        //    success: function(res){
        //        if(res.success){
        //            alert(res.msg);
        //            $state.go('course');
        //        }
        //    }
        //});
    };

    //初始化数据
    $scope.getAdvisor();
    $scope.getState();
    $scope.getLevel();
    $scope.getTag();
    $scope.getCourseById();
});
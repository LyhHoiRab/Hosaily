app.controller('chapterAddController', function($scope, $state, $stateParams, FileUploader, $http){
    //下拉
    $scope.states   = [];

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

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };

    $scope.course = {
        parentId     : $stateParams.parentId,
        title        : '',
        type         : 1,
        kind         : 0,
        summary      : '',
        introduction : '',
        state        : '',
        cover        : '/commons/img/level_default.jpg',
        likes        : 0,
        view         : 0,
        sort         : 0,
        price        : 0
    };

    $scope.reset = function(){
        $scope.course.title         = '';
        $scope.course.summary       = '';
        $scope.course.introduction  = '';
        $scope.course.state         = '';
        $scope.course.likes         = 0;
        $scope.course.view          = 0;
        $scope.course.sort          = 0;
        $scope.course.price         = 0;
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/course',
            method: 'POST',
            data: JSON.stringify($scope.course),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('courseEdit', {id : $scope.course.parentId});
            }else{
                alert(res.msg);
            }
        }).error(function(response){

        });
        //
        //$.ajax({
        //    url: '/api/1.0/course',
        //    type: 'POST',
        //    data: JSON.stringify($scope.course),
        //    dataType: 'JSON',
        //    contentType: 'application/json',
        //    success: function(res){
        //        if(res.success){
        //            alert(res.msg);
        //            $state.go('courseEdit', {id : $scope.parentId});
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

    //初始化数据
    $scope.getState();
});
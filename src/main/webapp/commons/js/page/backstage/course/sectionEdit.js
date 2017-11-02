app.controller('sectionEditController', function($scope, $state, $stateParams, FileUploader, $http){
    //下拉
    $scope.states = [];
    $scope.medias = [];

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
        id           : $stateParams.id,
        parentId     : '',
        title        : '',
        type         : 2,
        kind         : 0,
        summary      : '',
        introduction : '',
        state        : '',
        cover        : '/commons/img/level_default.jpg',
        likes        : 0,
        view         : 0,
        sort         : 0,
        price        : 0,
        media        : []
    };

    $scope.reset = function(){
        $scope.course.title         = '';
        $scope.course.summary       = '';
        $scope.course.introduction  = '';
        $scope.course.state         = '';
        $scope.course.likes         = 0;
        $scope.course.view          = 0;
        $scope.course.sort          = 0;
        $scope.course.media         = [];
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
                $state.go('chapterEdit', {id : $scope.course.parentId});
            }else{
                alert(res.msg);
            }
        }).error(function(response){

        });;

        //var medias = $('#medias').val();
        //
        //$scope.course.media = [];
        //if(medias !== null && medias.length > 0){
        //    angular.forEach(medias, function(data){
        //        $scope.course.media.push({id:data});
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
        //            $state.go('chapterEdit', {id : $scope.course.parentId});
        //        }
        //    }
        //});
    };

    $scope.getById = function(){
        $http({
            url: '/api/1.0/course/section/' + $scope.course.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.course);
            }else{
                alert(res.msg);
            }
        }).error(function(response){

        });

        //$.ajax({
        //    url: '/api/1.0/course/section/' + $scope.id,
        //    dataType: 'JSON',
        //    type: 'GET',
        //    success: function(res){
        //        if(res.success){
        //            utils.copyOf(res.result, $scope.course);
        //
        //            var medias = [];
        //            angular.forEach($scope.course.media, function(data){
        //                medias.push(data.id);
        //            });
        //            $('#medias').selectpicker('val', medias);
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

    $scope.getMedia = function(){
        $http({
            url: '/api/1.0/media/list',
            method: 'POST',
            data: $.param({
                'state': 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.medias = res.result;
            }
        }).error(function(response){
            $scope.medias = [];
        });
    };

    //初始化数据
    $scope.getById();
    $scope.getState();
    $scope.getMedia();
});
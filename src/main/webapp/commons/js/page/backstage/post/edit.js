app.controller('postEditController', function($scope, $state, $stateParams, FileUploader, $http){
    //下拉
    $scope.states = [];
    $scope.advisors = [];
    $scope.medias = [];

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
        $scope.post.cover = response.result;
    };

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };

    $scope.post = {
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
        //children     : [],
        media        : []
    };

    $scope.reset = function(){
        $scope.post.title         = '';
        $scope.post.summary       = '';
        $scope.post.introduction  = '';
        $scope.post.state         = '';
        $scope.post.price         = 0;
        $scope.post.likes         = 0;
        $scope.post.view          = 0;
        $scope.post.sort          = 0;
        $scope.post.advisor       = {};
        //$scope.post.children      = [];
        $scope.post.media         = [];
    };

    $scope.getById = function(){
        $http({
            url: '/api/1.0/post/' + $scope.post.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.post);
            }
        }).error(function(response){

        });

        //$.ajax({
        //    url: '/api/1.0/post/' + $scope.id,
        //    dataType: 'JSON',
        //    type: 'GET',
        //    success: function(res){
        //        if(res.success){
        //            utils.copyOf(res.result, $scope.post);
        //            editor.setData($scope.post.introduction);
        //
        //            var medias = [];
        //            angular.forEach($scope.post.media, function(data){
        //                medias.push(data.id);
        //            });
        //            $('#medias').selectpicker('val', medias);
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

    $scope.submit = function(){
        $http({
            url: '/api/1.0/post',
            method: 'PUT',
            data: JSON.stringify($scope.post),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('post');
            }else{
                alert(res.msg);
            }
        }).error(function(response){

        });

        //
        //var medias = $('#medias').val();
        //$scope.post.media = [];
        //if(medias !== null && medias.length > 0){
        //    angular.forEach(medias, function(data){
        //        $scope.post.media.push({id:data});
        //    });
        //};
        //
        //$scope.post.introduction = editor.getData();
        //
        //$.ajax({
        //    url: '/api/1.0/post',
        //    type: 'PUT',
        //    data: JSON.stringify($scope.post),
        //    dataType: 'JSON',
        //    contentType: 'application/json',
        //    success: function(res){
        //        if(res.success){
        //            alert(res.msg);
        //            $state.go('post');
        //        }
        //    }
        //});
    };

    //初始化数据
    $scope.getById();
    $scope.getState();
    $scope.getAdvisor();
    $scope.getMedia();
});
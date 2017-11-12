app.controller('postAddController', function($scope, $state, FileUploader, $http){
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

    $scope.submit = function(){
       $http({
           url: '/api/1.0/post',
           method: 'POST',
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

    //初始化数据
    $scope.getState();
    $scope.getAdvisor();
    $scope.getMedia();
});
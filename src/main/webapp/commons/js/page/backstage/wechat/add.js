app.controller('wechatAddController', function($scope, $state, FileUploader, $http, $stateParams){
    $scope.advisors       = [];
    $scope.states         = {};
    $scope.organizationId = $stateParams.organizationId;

    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/popularize/wechat/upload',
        queueLimit: 1,
        method: 'POST',
        removeAfterUpload: true
    });

    uploader.filters.push({
        name: 'imageFilter',
        fn: function(item){
            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
            return 'jpg|jpeg|png|bmp|gif|'.indexOf(type) !== -1;
        }
    });

    uploader.onSuccessItem = function(item, response, status, headers){
        $scope.wechat.headImgUrl = response.result;
    };

    $scope.wechat = {
        wxno           : '',
        headImgUrl     : '/commons/img/level_default.jpg',
        nickname       : '',
        advisorId      : '',
        organizationId : $scope.organizationId,
        remark         : '',
        seller         : '',
        qr             : '',
        state          : ''
    };

    $scope.reset = function(){
        $scope.wechat.wxno       = '';
        $scope.wechat.nickname   = '';
        $scope.wechat.headImgUrl = '';
        $scope.wechat.nickname   = '';
        $scope.wechat.advisorId  = '';
        $scope.wechat.remark     = '';
        $scope.wechat.seller     = '';
        $scope.wechat.qr         = '';
        $scope.wechat.state      = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/popularize/wechat',
            method: 'POST',
            data: JSON.stringify($scope.wechat),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('wechat');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
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
                'organizationId' : $scope.organizationId
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

    //初始化数据
    $scope.getState();
    $scope.getAdvisor();
});
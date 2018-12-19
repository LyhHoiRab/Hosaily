app.controller('profileAddController', function($scope, $state, FileUploader, $http, $stateParams){
    // $scope.userTypes        = ["情感分析师","情感导师"];
    $scope.shareEnables        = ["Y","N"];
    $scope.states        = {};

    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/app/profile/upload',
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
        $scope.profile.imgUrl = response.result;
    };

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };

    $scope.profile = {
        id   : '',
        title        : '',
        num : '',
        price : '',
        originalPrice : '',
        summry : '',
        needKonw : '',
        state          : '',
        questionCount : '',
        completedCount : '',
        shareEnable : '',
        order : '',
        imgUrl     : '/commons/img/level_default.jpg',
        timeLimit : '',
        organizationId : ''
    };

    $scope.reset = function(){
        $scope.profile.id    = '';
        $scope.profile.title         = '';
        $scope.profile.num  = '';
        $scope.profile.price  = '';
        $scope.profile.originalPrice  = '';
        $scope.profile.summry  = '';
        $scope.profile.needKonw  = '';
        $scope.profile.questionCount  = '';
        $scope.profile.completedCount  = '';
        $scope.profile.shareEnable  = '';
        $scope.profile.order  = '';
        $scope.profile.timeLimit  = '';
        $scope.profile.organizationId  = '';
        $scope.profile.state          = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/app/profile',
            method: 'POST',
            data: JSON.stringify($scope.profile),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('profile');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getOrganization = function(){
        $http({
            url: '/api/1.0/organization/list',
            method: 'POST',
            data: $.param({
                'state' : 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.organizations = res.result;
            }
        }).error(function(response){
            $scope.organizations = [];
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

    $scope.getOrganization();
    $scope.getState();
});
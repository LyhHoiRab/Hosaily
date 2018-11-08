app.controller('advisorEditController', function($scope, $state, $stateParams, FileUploader, $http){
    $scope.sexs          = {};
    $scope.states        = {};
    $scope.organizations = [];

    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/advisor/upload',
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
        $scope.advisor.headImgUrl = response.result;

    };

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };

    $scope.advisor = {
        id             : $stateParams.id,
        name           : '',
        nickname       : '',
        age            : '',
        sex            : '',
        introduction   : '',
        state          : '',
        sort           : '',
        headImgUrl     : '',
        organizationId : '',
        honor          : '',
        summary        : ''
    };

    $scope.reset = function(){
        $scope.advisor.name           = '';
        $scope.advisor.nickname       = '';
        $scope.advisor.age            = '';
        $scope.advisor.sex            = '';
        $scope.advisor.introduction   = '';
        $scope.advisor.state          = '';
        $scope.advisor.sort           = '';
        $scope.advisor.organizationId = '';
        $scope.advisor.honor          = '';
        $scope.advisor.summary        = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/advisor',
            method: 'PUT',
            data: JSON.stringify($scope.advisor),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('advisor');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getById = function(){
        $http({
            url: '/api/1.0/advisor/' + $scope.advisor.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.advisor);
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

    $scope.getSex = function(){
        $http({
            url: '/api/1.0/sex/list',
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.sexs = res.result;
            }
        }).error(function(response){
            $scope.sexs = [];
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

    //初始化
    $scope.getState();
    $scope.getSex();
    $scope.getOrganization();
    $scope.getById();
});
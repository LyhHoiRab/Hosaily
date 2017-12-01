app.controller('webResourceEditController', function($scope, $state, $http, $stateParams){
    //下拉
    $scope.states        = [];
    $scope.organizations = [];

    $scope.resource = {
        id             : $stateParams.id,
        domain         : '',
        imgUrl         : '',
        cssUrl         : '',
        jsUrl          : '',
        mobileImgUrl   : '',
        mobileCssUrl   : '',
        mobileJsUrl    : '',
        organizationId : '',
        state          : ''
    };

    $scope.reset = function(){
        $scope.resource.domain         = '';
        $scope.resource.imgUrl         = '';
        $scope.resource.cssUrl         = '';
        $scope.resource.jsUrl          = '';
        $scope.resource.mobileImgUrl   = '';
        $scope.resource.mobileCssUrl   = '';
        $scope.resource.mobileJsUrl    = '';
        $scope.resource.organizationId = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/webResource',
            method: 'PUT',
            data: JSON.stringify($scope.resource),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('webResource')
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getById = function(){
        $http({
            url: '/api/1.0/webResource/' + $scope.resource.id,
            method: 'GET',
            data: JSON.stringify($scope.resource),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.resource);
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

    //初始化数据
    $scope.getState();
    $scope.getOrganization();
    $scope.getById();
});
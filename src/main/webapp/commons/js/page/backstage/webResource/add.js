app.controller('webResourceAddController', function($scope, $state, $http){
    //下拉
    $scope.states        = [];
    $scope.organizations = [];

    $scope.resource = {
        domain         : '',
        imgUrl         : '',
        cssUrl         : '',
        jsUrl          : '',
        mobileImgUrl   : '',
        mobileCssUrl   : '',
        mobileJsUrl    : '',
        organizationId : ''
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
            method: 'POST',
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
});
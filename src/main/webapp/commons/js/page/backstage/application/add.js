app.controller('applicationAddController', function($scope, $state, $http){
    //下拉
    $scope.types         = [];
    $scope.states        = [];
    $scope.organizations = [];

    $scope.application = {
        name           : '',
        appId          : '',
        secret         : '',
        aesKey         : '',
        token          : '',
        type           : '',
        state          : '',
        organizationId : ''
    };

    $scope.reset = function(){
        $scope.application.name           = '';
        $scope.application.appId          = '';
        $scope.application.secret         = '';
        $scope.application.aesKey         = '';
        $scope.application.token          = '';
        $scope.application.type           = '';
        $scope.application.state          = '';
        $scope.application.organizationId = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/application',
            method: 'POST',
            data: JSON.stringify($scope.application),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('application')
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

    $scope.getType = function(){
        $http({
            url: '/api/1.0/applicationType/list',
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.types = res.result;
            }
        }).error(function(response){
            $scope.types = [];
        });
    };

    //初始化数据
    $scope.getState();
    $scope.getOrganization();
    $scope.getType();
});
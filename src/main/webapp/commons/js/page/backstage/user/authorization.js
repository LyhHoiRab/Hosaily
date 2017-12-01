app.controller('authorizationController', function($scope, $stateParams, $http, $state){
    $scope.organizationId = '';
    $scope.accountId      = $stateParams.accountId;
    $scope.selected = {
        group : []
    };

    $scope.organizations = [];
    $scope.groups        = [];

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

    $scope.getGroup = function(){
        $http({
            url: '/api/1.0/courseGroup/list',
            method: 'POST',
            data: $.param({
                'organizationId' : $scope.organizationId,
                'state'          : 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.groups = res.result;
            }
        }).error(function(response){
            $scope.groups = [];
        });
    };

    $scope.getById = function(){
        $http({
            url: '/api/1.0/courseGroup/list/',
            method: 'POST',
            data: $.param({
                'accountId' : $scope.accountId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.selected.group = res.result;
            }
        }).error(function(response){
            $scope.selected.group = [];
            console.error(response);
        });
    };

    $scope.reset = function(){
        $scope.selected.group = [];
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/courseGroup/authorization/' + $scope.accountId,
            method: 'POST',
            data: JSON.stringify($scope.selected.group),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('user');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.$watch('organizationId', function(newVal, oldVal){
        if(newVal !== oldVal){
            $scope.getGroup();
        }
    }, true);

    //初始化
    $scope.getById();
    $scope.getOrganization();
});
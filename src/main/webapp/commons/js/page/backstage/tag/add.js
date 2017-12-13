app.controller('tagAddController', function($scope, $state, $http, $stateParams){
    //下拉
    $scope.states        = {};
    $scope.organizations = [];

    //标签实体
    $scope.tag = {
        name           : '',
        description    : '',
        state          : '',
        organizationId : $stateParams.organizationId
    };

    $scope.back = function(){
        $state.go('tag');
    };

    $scope.reset = function(){
        $scope.tag.name           = '';
        $scope.tag.description    = '';
        $scope.tag.state          = '';
        $scope.tag.organizationId = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/tag',
            method: 'POST',
            data: JSON.stringify($scope.tag),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $scope.back();
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

    //初始化
    $scope.getState();
    $scope.getOrganization();
});
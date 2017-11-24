app.controller('tagEditController', function($scope, $state, $stateParams, $http){
    //下拉
    $scope.states = [];
    $scope.organizations = [];
    //标签实体
    $scope.tag = {
        id             : $stateParams.id,
        name           : '',
        description    : '',
        state          : '',
        organizationId : ''
    };

    $scope.reset = function(){
        $scope.tag.name         = '';
        $scope.tag.description  = '';
        $scope.tag.state        = '';
        $scope.organizationId   = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/tag',
            method: 'PUT',
            data: JSON.stringify($scope.tag),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('tag');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            alert('系统繁忙');
            console.error(response);
        });
    };

    $scope.getById = function(){
        $http({
            url: '/api/1.0/tag/' + $scope.tag.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.tag);
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            alert('系统繁忙');
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
    $scope.getById();
    $scope.getState();
    $scope.getOrganization();
});
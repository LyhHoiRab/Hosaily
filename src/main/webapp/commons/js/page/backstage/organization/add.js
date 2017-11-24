app.controller('organizationAddController', function($scope, $state, $http){
    $scope.states = [];

    $scope.organization = {
        name         : '',
        token        : '',
        state        : ''
    };

    $scope.reset = function(){
        $scope.organization.name         = '';
        $scope.organization.state        = '';
        $scope.organization.token        = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/organization',
            method: 'POST',
            data: JSON.stringify($scope.organization),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('organization');
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


    //初始化
    $scope.getState();
});
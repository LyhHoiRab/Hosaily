app.controller('simNumUserEditController', function($scope, $state, $stateParams, $http){
    $scope.userTypes        = ["情感分析师","情感导师"];
    $scope.sim = $stateParams.sim;
    $scope.organizations = [];

    $scope.simNumUser = {
        sim   : $stateParams.sim,
        num        : '',
        userName : '',
        userType : '',
        organizationId : ''
    };

    $scope.reset = function(){
        $scope.simNumUser.num         = '';
        $scope.simNumUser.userName  = '';
        $scope.simNumUser.userType  = '';
        $scope.simNumUser.organizationId  = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/simNumUser',
            method: 'PUT',
            data: JSON.stringify($scope.simNumUser),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('simNumUser');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };


    $scope.getBySim = function(){
        $http({
            url: '/api/1.0/simNumUser/' + $scope.sim,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.simNumUser);
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

    $scope.getBySim();
    $scope.getOrganization();
});
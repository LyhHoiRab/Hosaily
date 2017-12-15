app.controller('simNumUserAddController', function($scope, $state, $http, $stateParams){
    $scope.userTypes        = ["情感分析师","情感导师"];
    $scope.simNumUser = {
        sim   : '',
        num        : '',
        userName : '',
        userType : '',
        organizationId : ''
    };
    
    $scope.reset = function(){
        $scope.simNumUser.sim    = '';
        $scope.simNumUser.num         = '';
        $scope.simNumUser.userName  = '';
        $scope.simNumUser.userType  = '';
        $scope.simNumUser.organizationId  = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/simNumUser',
            method: 'POST',
            data: JSON.stringify($scope.advisor),
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

    $scope.getOrganization();
});
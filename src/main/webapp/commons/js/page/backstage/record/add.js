app.controller('recordAddController', function($scope, $state, $http, $stateParams){
    $scope.organizations = [];
    $scope.record = {
        sim   : '',
        outGoingNum        : '',
        num : '',
        time : '',
        path       : '',
        userName       : '',
        organizationId : ''
    };
    
    $scope.reset = function(){
        $scope.record.sim    = '';
        $scope.record.outGoingNum         = '';
        $scope.record.num  = '';
        $scope.record.time        = '';
        $scope.record.path        = '';
        $scope.record.userName        = '';
        $scope.record.organizationId = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/record',
            method: 'POST',
            data: JSON.stringify($scope.advisor),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('record');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };


    $scope.getOrganization();
});
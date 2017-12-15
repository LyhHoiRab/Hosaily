app.controller('recordEditController', function($scope, $state, $stateParams, $http){
    $scope.id = $stateParams.id;
    $scope.organizations = [];

    $scope.record = {
        id           : $stateParams.id,
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
            method: 'PUT',
            data: JSON.stringify($scope.record),
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

    $scope.getById = function(){
        $http({
            url: '/api/1.0/record/' + $scope.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.record);
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


    $scope.getById();
    $scope.getOrganization();
});
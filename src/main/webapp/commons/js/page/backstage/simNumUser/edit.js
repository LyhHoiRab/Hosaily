app.controller('simNumUserEditController', function($scope, $state, $stateParams){
    $scope.sim = $stateParams.sim;

    $scope.simNumUser = {
        sim   : $stateParams.sim,
        num        : '',
        userName : ''
    };

    $scope.reset = function(){
        $scope.simNumUser.num         = '';
        $scope.simNumUser.userName  = '';
    };

    $scope.submit = function(){
        $.ajax({
            url: '/api/1.0/simNumUser',
            type: 'PUT',
            data: JSON.stringify($scope.simNumUser),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('simNumUser');
                }
            }
        });
    };

    $scope.getBySim = function(){
        $.ajax({
            url: '/api/1.0/simNumUser/' + $scope.sim,
            type: 'GET',
            dataType: 'JSON',
            success: function(res){
                if(res.success){
                    //$scope.tag = res.result;
                    utils.copyOf(res.result, $scope.simNumUser);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.getBySim();
});
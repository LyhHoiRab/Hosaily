app.controller('recordEditController', function($scope, $state, $stateParams){
    $scope.id = $stateParams.id;

    $scope.record = {
        id           : $stateParams.id,
        sim   : '',
        outGoingNum        : '',
        num : '',
        time : '',
        path       : '',
        userName       : ''
    };

    $scope.reset = function(){
        $scope.record.sim    = '';
        $scope.record.outGoingNum         = '';
        $scope.record.num  = '';
        $scope.record.time        = '';
        $scope.record.path        = '';
        $scope.record.userName        = '';
    };

    $scope.submit = function(){
        $.ajax({
            url: '/api/1.0/record',
            type: 'PUT',
            data: JSON.stringify($scope.record),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('record');
                }
            }
        });
    };

    $scope.getById = function(){
        $.ajax({
            url: '/api/1.0/record/' + $scope.id,
            type: 'GET',
            dataType: 'JSON',
            success: function(res){
                if(res.success){
                    //$scope.tag = res.result;
                    utils.copyOf(res.result, $scope.record);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.getById();
});
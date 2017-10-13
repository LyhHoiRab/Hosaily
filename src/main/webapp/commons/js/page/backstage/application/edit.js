app.controller('applicationEditController', function($scope, $stateParams, $state){
    $scope.id = $stateParams.id;
    $scope.application = {
        id     : $scope.id,
        name   : '',
        appId  : '',
        secret : '',
        aesKey : '',
        token  : '',
        type   : '',
        state  : ''
    };

    $scope.getById = function(){
        $.ajax({
            url: '/api/1.0/application/' + $scope.id,
            type: 'GET',
            dataType: 'JSON',
            success: function(res){
                if(res.success){
                    //$scope.application = res.result;
                    utils.copyOf(res.result, $scope.application);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.reset = function(){
        $scope.application.name   = '';
        $scope.application.appId  = '';
        $scope.application.secret = '';
        $scope.application.aesKey = '';
        $scope.application.token  = '';
        $scope.application.type   = '';
    };

    $scope.submit = function(){
        $.ajax({
            url: '/api/1.0/application',
            type: 'PUT',
            data: JSON.stringify($scope.application),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('application');
                }
            }
        });
    };

    $scope.getById();
});
app.controller('tagEditController', function($scope, $state, $stateParams){
    $scope.id = $stateParams.id;

    $scope.tag = {
        name        : '',
        description : '',
        state       : ''
    };

    $scope.reset = function(){
        $scope.tag.name         = '';
        $scope.tag.description  = '';
        $scope.tag.state        = '';
    };

    $scope.submit = function(){
        $.ajax({
            url: '/api/1.0/tag',
            type: 'PUT',
            data: JSON.stringify($scope.tag),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('tag');
                }
            }
        });
    };

    $scope.getById = function(){
        $.ajax({
            url: '/api/1.0/tag/' + $scope.id,
            type: 'GET',
            dataType: 'JSON',
            success: function(res){
                if(res.success){
                    //$scope.tag = res.result;
                    utils.copyOf(res.result, $scope.tag);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.getById();
});
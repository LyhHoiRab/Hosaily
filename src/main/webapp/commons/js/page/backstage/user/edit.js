app.controller('userEditController', function($scope, $stateParams, $http, $state){
    $scope.user = {
        id     : $stateParams.id,
        name   : '',
        wechat : ''
    };

    $scope.reset = function(){
        $scope.user.name   = '';
        $scope.user.wechat = '';
    };

    $scope.getById = function(){
        $http({
            url: '/api/1.0/user/' + $scope.user.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.user);
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/user',
            method: 'PUT',
            data: JSON.stringify($scope.user),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('user');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    //初始化
    $scope.getById();
});
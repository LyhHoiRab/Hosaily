app.controller('salesEditController', function($scope, $state, $stateParams, $http){
    //下拉
    $scope.advisors = [];

    $scope.sales = {
        id        : $stateParams.id,
        name      : '',
        advisorId : '',
        wechat    : ''
    };

    $scope.reset = function(){
        $scope.sales.advisorId = '';
        $scope.sales.name      = '';
        $scope.sales.wechat    = '';
    };

    $scope.getById = function(){
        $http({
            url: '/api/1.0/user/' + $scope.sales.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.sales);
            }
        }).error(function(response){

        });
    };

    $scope.getAdvisor = function(){
        $http({
            url: '/api/1.0/advisor/list',
            method: 'POST',
            data: $.param({
                'state': 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.advisors = res.result;
            }
        }).error(function(response){
            $scope.advisors = [];
        });
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/user',
            method: 'PUT',
            data: JSON.stringify($scope.sales),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('sales');
            }else{
                alert(res.msg);
            }
        }).error(function(response){

        });
    };

    //初始化数据
    $scope.getAdvisor();
    $scope.getById();
});
app.controller('productAddController', function($scope, $http, $state, $stateParams){
    //下拉
    $scope.states        = {};
    $scope.organizations = [];

    //实体
    $scope.product = {
        name           : '',
        description    : '',
        organizationId : $stateParams.organizationId,
        price          : 0,
        duration       : 0,
        services       : [],
        state          : ''
    };

    //按钮
    $scope.serviceAdd = function(){
        var service = {
            name        : '',
            description : '',
            time        : 0,
            unitPrice   : 0
        };

        $scope.product.services.push(service);
    };

    $scope.serviceDelete = function(index){
        $scope.product.services.splice(index, 1);
    };

    $scope.reset = function(){
        $scope.product.name           = '';
        $scope.product.price          = 0;
        $scope.product.duration       = 0;
        $scope.product.organizationId = '';
        $scope.product.state          = '';
        $scope.product.services       = [];
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/product',
            method: 'POST',
            data: JSON.stringify($scope.product),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('product');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    //数据查询
    $scope.getState = function(){
        $http({
            url: '/api/1.0/usingState/list',
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.states = res.result;
            }
        }).error(function(response){
            $scope.states = {};
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

    //初始化
    $scope.getState();
    $scope.getOrganization();
});
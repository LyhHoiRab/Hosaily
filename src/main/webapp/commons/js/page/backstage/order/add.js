app.controller('orderAddController', function($scope, $http, $stateParams, $state){
    $scope.users    = [];
    $scope.courses  = [];
    $scope.paymentTypes = [];

    $scope.selected = {};
    $scope.selected.courses = [];

    $scope.order = {
        orderId       : '',
        salesUser     : {},
        salesAccount  : '',
        clientAccount : $stateParams.accountId,
        price         : 0,
        pay           : 0,
        remark        : '',
        payLogs       : [],
        accreditLogs  : []
    };

    $scope.getUser = function(){
        $http({
            url: '/api/1.0/user/list',
            method: 'POST',
            data: $.param({
                'state' : 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.users = res.result;
            }
        }).error(function(response){
            $scope.courses = [];
        });
    };

    $scope.getCourse = function(){
        $http({
            url: '/api/1.0/course/list',
            method: 'POST',
            data: $.param({
                'state': 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.courses = res.result;
            }
        }).error(function(response){
            $scope.courses = [];
        });
    };

    $scope.getPaymentType = function(){
        $http({
            url: '/api/1.0/paymentType/list',
            method: 'POST',
            data: $.param({
                'state': 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.paymentTypes = res.result;
            }
        }).error(function(response){
            $scope.courses = [];
        });
    };

    $scope.pay = function(){
        var item = {
            id      : '',
            pay     : 0,
            payment : {}
        };

        $scope.order.payLogs.push(item);
    };

    $scope.delete = function(index){
        $scope.order.payLogs.splice(index, 1);
    };

    $scope.submit = function(){
        $scope.order.pay = $scope.sum();
        $scope.order.accreditLogs = $scope.accreditLogs();
        $scope.order.salesAccount = $scope.order.salesUser.accountId;

        console.log($scope.order);

        $http({
            url: '/api/1.0/order',
            method: 'POST',
            data: JSON.stringify($scope.order),
            headers: {
                'Content-Type' : 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('accountCourse', {accountId : $scope.order.clientAccount});
            }else{
                alert(res.msg);
            }
        }).error(function(response){

        });
    };

    $scope.reset = function(){
        $scope.order.salesUser    = {};
        $scope.order.payLogs      = [];
        $scope.order.accreditLogs = [];
        $scope.order.pay          = 0;
        $scope.order.price        = 0;
        $scope.selected.courses   = [];
    };

    $scope.sum = function(){
        var result = 0;

        if($scope.order.payLogs.length > 0){
            angular.forEach($scope.order.payLogs, function(data){
                result += data.pay;
            });
        }

        return result;
    };

    $scope.accreditLogs = function(){
        var result = [];

        if($scope.selected.courses.length > 0){
            angular.forEach($scope.selected.courses, function(data){
                var accreditLog = {
                    accreditAccount  : $scope.order.salesUser.accountId,
                    accreditUser     : $scope.order.salesUser,
                    authorizeAccount : $scope.order.clientAccount,
                    course           : data
                };

                result.push(accreditLog);
            });

            return result;
        }
    };

    //初始化数据
    $scope.getUser();
    $scope.getCourse();
    $scope.getPaymentType();
});
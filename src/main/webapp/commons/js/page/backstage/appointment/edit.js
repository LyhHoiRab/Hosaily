app.controller('appointmentEditController', function($scope, $state, $http, $stateParams){
    $scope.sexs          = {};
    $scope.states        = {};

    $scope.appointment = {
        id          : $stateParams.id,
        name        : '',
        phone       : '',
        wechat      : '',
        type        : '',
        description : '',
        state       : '',
        remark      : '',
        sex         : ''
    };

    $scope.reset = function(){
        $scope.appointment.remark = '';
    };

    $scope.submit = function(){
        if($scope.appointment.state == 0){
            $scope.appointment.state = 1;

            $http({
                url: '/api/1.0/appointment',
                method: 'PUT',
                data: JSON.stringify($scope.appointment),
                headers: {
                    'Content-Type': 'application/json'
                }
            }).success(function(res, status, headers, config){
                if(res.success){
                    alert(res.msg);
                    $state.go('appointment');
                }else{
                    alert(res.msg);
                }
            }).error(function(response){
                console.error(response);
            });
        }
    };

    $scope.getById = function(){
        $http({
            url: '/api/1.0/appointment/' + $scope.appointment.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.appointment);
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getState = function(){
        $http({
            url: '/api/1.0/appointmentState/list',
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.states = res.result;
            }
        }).error(function(response){
            $scope.states = [];
        });
    };

    $scope.getSex = function(){
        $http({
            url: '/api/1.0/sex/list',
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.sexs = res.result;
            }
        }).error(function(response){
            $scope.sexs = [];
        });
    };

    //初始化
    $scope.getState();
    $scope.getSex();
    $scope.getById();
});
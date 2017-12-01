app.controller('courseGroupAddController', function($scope, $state, $http){
    $scope.states        = [];
    $scope.organizations = [];
    $scope.courses       = [];

    $scope.group = {
        name           : '',
        organizationId : '',
        state          : '',
        course         : []
    };

    $scope.reset = function(){
        $scope.group.name           = '';
        $scope.group.state          = '';
        $scope.group.organizationId = '';
        $scope.group.course         = [];
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/courseGroup',
            method: 'POST',
            data: JSON.stringify($scope.group),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('courseGroup');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getState = function(){
        $http({
            url: '/api/1.0/usingState/list',
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

    $scope.getCourse = function(){
        $http({
            url: '/api/1.0/course/list',
            method: 'POST',
            data: $.param({
                'state'          : 0,
                'organizationId' : $scope.group.organizationId
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

    $scope.$watch('group.organizationId', function(newVal, oldVal){
        if(newVal !== oldVal){
            $scope.getCourse();
        }
    }, true);

    //初始化
    $scope.getState();
    $scope.getOrganization();
});
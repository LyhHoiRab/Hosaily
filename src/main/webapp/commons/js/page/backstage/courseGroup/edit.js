app.controller('courseGroupEditController', function($scope, $state, $http, $stateParams){
    $scope.states        = {};
    $scope.organizations = [];
    $scope.courses       = [];

    $scope.group = {
        id             : $stateParams.id,
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

    $scope.getById = function(){
        $http({
            url: '/api/1.0/courseGroup/' + $scope.group.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.group);
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/courseGroup',
            method: 'PUT',
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
    $scope.getById();
    $scope.getState();
    $scope.getOrganization();
});
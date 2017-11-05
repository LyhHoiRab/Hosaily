app.controller('accountCourseAddController', function($scope, $state, $stateParams, $http){
    $scope.courses = [];
    $scope.selected = [];
    $scope.accountCourse = {
        accountId : $stateParams.accountId,
        course    : {}
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

    $scope.submit = function(){
        $http({
            url: '/api/1.0/accountCourse',
            method: 'POST',
            data: JSON.stringify($scope.accountCourse),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('accountCourse', {accountId : $scope.accountCourse.accountId});
            }else{
                alert(res.msg);
            }
        }).error(function(response){

        });
    };

    $scope.reset = function(){
        $scope.accountCourse.course = [];
    };

    //初始化数据
    $scope.getCourse();
});
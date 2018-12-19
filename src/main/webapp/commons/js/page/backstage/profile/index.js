app.controller('profileController', function($scope, $http, $state){
    $scope.organizations = [];

    //切换tab
    $scope.toggle = function(organizationId){
        $state.go('profile.list', {'organizationId' : organizationId});
    };

    //查询企业
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

    //初始化数据
    $scope.getOrganization();
    $scope.toggle('ad748e6d57be453f920f2953ddf0bb70');

});
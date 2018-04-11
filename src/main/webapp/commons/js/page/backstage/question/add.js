app.controller('questionAddController', function($scope, $state, $http, $stateParams){
    // $scope.userTypes        = ["情感分析师","情感导师"];
    $scope.isResults        = ["N","Y"];
    $scope.projects = [];
    $scope.organizations = [];

    $scope.question = {
        id   : '',
        title        : '',
        project : {},
        num : '',
        isResult : 'N',
        organizationId : ''
    };

    $scope.reset = function(){
        $scope.question.id    = '';
        $scope.question.title         = '';
        $scope.question.project  = {};
        $scope.question.num  = '';
        $scope.question.isResult  = '';
        $scope.question.organizationId  = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/question',
            method: 'POST',
            data: JSON.stringify($scope.question),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('question');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
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


    $scope.getProject = function(){
        $http({
            url: '/api/1.0/project/list',
            method: 'POST',
            data: $.param({
                'state' : 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.projects = res.result;
            }
        }).error(function(response){
            $scope.projects = [];
        });
    };

    $scope.getOrganization();
    $scope.getProject();
});
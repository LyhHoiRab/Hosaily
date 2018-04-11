app.controller('optionEditController', function($scope, $state, $stateParams, $http){
    $scope.options        = ["A","B","C","D","E"];
    $scope.id = $stateParams.id;
    $scope.organizations = [];
    $scope.projects = [];
    $scope.questions = [];
    $scope.option = {
        id   : $stateParams.id,
        title        : '',
        project : {},
        question : {},
        nestQuestion : {},
        questionOption : '',
        organizationId : ''
    };

    $scope.reset = function(){
        $scope.option.title         = '';
        $scope.option.project  = {};
        $scope.option.question  = {};
        $scope.option.nestQuestion  = {};
        $scope.option.questionOption  = '';
        $scope.option.organizationId  = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/option',
            method: 'PUT',
            data: JSON.stringify($scope.option),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('option');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };


    $scope.getById = function(){
        $http({
            url: '/api/1.0/option/' + $scope.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.option);
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
                'state' : 0,
                'organizationId' : $scope.option.organizationId
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


    $scope.selectQuestion = function(){
        // alert($scope.option.project.id);
        $http({
            url: '/api/1.0/question/list',
            method: 'POST',
            data: $.param({
                'state' : 0,
                'projectId' : $scope.option.project.id,
                'organizationId' : $scope.option.organizationId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.result);
                $scope.questions = res.result;
            }
        }).error(function(response){
            $scope.questions = [];
        });

    }

    $scope.getQuestion = function(){
        // alert($scope.option.project);
        $http({
            url: '/api/1.0/question/list',
            method: 'POST',
            data: $.param({
                'state' : 0,
                'projectId' : $scope.option.project.id,
                'organizationId' : $scope.option.organizationId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.questions = res.result;
            }
        }).error(function(response){
            $scope.questions = [];
        });
    };

    $scope.getProject();
    $scope.getQuestion();
    $scope.getOrganization();
    $scope.getById();

});
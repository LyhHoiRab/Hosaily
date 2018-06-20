app.controller('optionAddController', function($scope, $state, FileUploader, $http, $stateParams){
    $scope.options        = ["A","B","C","D","E"];
    $scope.projects = [];
    $scope.questions = [];
    $scope.organizations = [];

    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/option/upload',
        queueLimit: 1,
        method: 'POST',
        removeAfterUpload: true
    });

    uploader.filters.push({
        name: 'imageFilter',
        fn: function(item){
            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
            return 'jpg|jpeg|png|bmp|gif|'.indexOf(type) !== -1;
        }
    });

    uploader.onSuccessItem = function(item, response, status, headers){
        $scope.option.imgUrl = response.result;
    };

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };

    $scope.option = {
        id   : '',
        title        : '',
        project : {},
        question : {},
        nestQuestion : {},
        questionOption : '',
        imgUrl     : '/commons/img/level_default.jpg',
        organizationId : ''
    };
    
    $scope.reset = function(){
        $scope.option.id    = '';
        $scope.option.title         = '';
        $scope.option.project  = {};
        $scope.option.question  = {};
        $scope.option.nestQuestion = {};
        $scope.option.questionOption  = '';
        $scope.option.organizationId  = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/option',
            method: 'POST',
            data: JSON.stringify($scope.option),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                // alert(res.msg);
                $state.go('option');
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
                // alert(res.result);
                $scope.questions = res.result;
            }
        }).error(function(response){
            $scope.questions = [];
        });

    }
    
    $scope.getQuestion = function(){
        $http({
            url: '/api/1.0/question/list',
            method: 'POST',
            data: $.param({
                'state' : 0,
                'projectId' : $scope.option.project.id,
                'organizationId' : 'ad748e6d57be453f920f2953ddf0bb70'
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
    // $scope.selectQuestion();

    $scope.getOrganization();
});
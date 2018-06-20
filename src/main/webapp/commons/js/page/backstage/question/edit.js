app.controller('questionEditController', function($scope, $state, FileUploader, $stateParams, $http){
    // $scope.userTypes        = ["情感分析师","情感导师"];
    $scope.isResults        = ["N","Y"];
    $scope.id = $stateParams.id;
    $scope.organizations = [];
    $scope.oprojects = [];

    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/question/upload',
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
        $scope.question.imgUrl = response.result;

    };

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };

    $scope.question = {
        id   : $stateParams.id,
        title        : '',
        project : {},
        num : '',
        isResult : 'N',
        organizationId : ''
    };

    $scope.reset = function(){
        $scope.question.title         = '';
        $scope.question.project       = {};
        $scope.question.num  = '';
        $scope.question.isResult  = '';
        $scope.question.organizationId  = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/question',
            method: 'PUT',
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


    $scope.getById = function(){
        $http({
            url: '/api/1.0/question/' + $scope.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.question);
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
                'organizationId' : $scope.question.organizationId
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

    $scope.getProject();
    $scope.getOrganization();
    $scope.getById();
});
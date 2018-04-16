app.controller('projectEditController', function($scope, $state, FileUploader, $stateParams, $http){
    // $scope.userTypes        = ["情感分析师","情感导师"];
    $scope.id = $stateParams.id;
    $scope.organizations = [];
    $scope.shareEnables        = ["Y","N"];
    $scope.states        = {};

    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/project/upload',
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
        $scope.project.imgUrl = response.result;

    };

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };
    
    $scope.project = {
        id   : $stateParams.id,
        title        : '',
        num : '',
        price : '',
        originalPrice : '',
        summry : '',
        state          : '',
        needKonw : '',
        questionCount : '',
        completedCount : '',
        shareEnable : '',
        order : '',
        imgUrl     : '',
        timeLimit : '',
        organizationId : ''
    };

    $scope.reset = function(){
        $scope.project.title         = '';
        $scope.project.num  = '';
        $scope.project.price  = '';
        $scope.project.state          = '';
        $scope.project.originalPrice  = '';
        $scope.project.summry  = '';
        $scope.project.needKonw  = '';
        $scope.project.questionCount  = '';
        $scope.project.completedCount  = '';
        $scope.project.shareEnable  = '';
        $scope.project.order  = '';
        $scope.project.timeLimit  = '';

        $scope.project.organizationId  = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/project',
            method: 'PUT',
            data: JSON.stringify($scope.project),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('project');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };


    $scope.getById = function(){
        $http({
            url: '/api/1.0/project/' + $scope.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.project);
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

    $scope.getById();
    $scope.getState();
    $scope.getOrganization();
});
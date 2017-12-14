app.controller('customizationEditController', function($scope, $state, $stateParams, FileUploader, $http){
    //下拉
    $scope.states        = {};
    $scope.organizations = [];
    $scope.tags          = [];

    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/customization/upload',
        queueLimit: 1,
        method: 'POST',
        removeAfterUpload: true
    });

    uploader.filters.push({
        name: 'imageFilter',
        fn: function(item){
            var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
            return 'jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    });

    uploader.onSuccessItem = function(item, response, status, headers){
        $scope.customization.cover = response.result;
    };

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };

    $scope.customization = {
        id           : $stateParams.id,
        title          : '',
        summary        : '',
        introduction   : '',
        state          : '',
        cover          : '/commons/img/level_default.jpg',
        sort           : 0,
        subscribe      : 0,
        organizationId : '',
        tag            : []
    };

    $scope.reset = function(){
        $scope.customization.title          = '';
        $scope.customization.summary        = '';
        $scope.customization.introduction   = '';
        $scope.customization.state          = '';
        $scope.customization.sort           = 0;
        $scope.customization.subscribe      = 0;
        $scope.customization.organizationId = '';
        $scope.customization.tag            = [];
    };

    $scope.getById = function(){
        $http({
            url: '/api/1.0/customization/' + $scope.customization.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.customization);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/customization',
            method: 'PUT',
            data: JSON.stringify($scope.customization),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('customization');
            }else{
                alert(res.msg);
            }
        }).error(function(response){

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
            $scope.states = {};
        });
    };

    $scope.getTag = function(){
        $http({
            url: '/api/1.0/tag/list',
            method: 'POST',
            data: $.param({
                'state'          : 0,
                'organizationId' : $scope.customization.organizationId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.tags = res.result;
            }
        }).error(function(response){
            $scope.tags = [];
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

    $scope.$watch('customization.organizationId', function(newVal, oldVal){
        if(newVal !== oldVal){
            $scope.getTag();
        }

    }, true);

    //初始化数据
    $scope.getById();
    $scope.getState();
    $scope.getOrganization();
});
app.controller('courseAddController', function($scope, $state, FileUploader, $http){
    //下拉
    $scope.states        = [];
    $scope.advisors      = [];
    $scope.organizations = [];
    //$scope.tags     = [];
    //$scope.levels   = [];

    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/course/upload',
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
        $scope.course.cover = response.result;
    };

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };

    $scope.course = {
        title          : '',
        type           : 0,
        kind           : 0,
        summary        : '',
        introduction   : '',
        state          : '',
        cover          : '/commons/img/level_default.jpg',
        price          : 0,
        likes          : 0,
        view           : 0,
        sort           : 0,
        advisor        : {},
        organizationId : ''
        //tag          : [],
        //level        : []
    };

    $scope.reset = function(){
        $scope.course.title          = '';
        $scope.course.summary        = '';
        $scope.course.introduction   = '';
        $scope.course.state          = '';
        $scope.course.price          = 0;
        $scope.course.likes          = 0;
        $scope.course.view           = 0;
        $scope.course.sort           = 0;
        $scope.course.advisor        = {};
        $scope.course.organizationId = '';
        //$scope.course.tag           = [];
        //$scope.course.level         = [];
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

    $scope.getAdvisor = function(){
        $http({
            url: '/api/1.0/advisor/list',
            method: 'POST',
            data: $.param({
                'state'          : 0,
                'organizationId' : $scope.course.organizationId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.advisors = res.result;
            }
        }).error(function(response){
            $scope.advisors = [];
        });
    };

    $scope.getLevel = function(){
        $http({
            url: '/api/1.0/level/list',
            method: 'POST',
            data: $.param({
                'state': 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.levels = res.result;
            }
        }).error(function(response){
            $scope.levels = [];
        });
    };

    $scope.getTag = function(){
        $http({
            url: '/api/1.0/tag/list',
            method: 'POST',
            data: $.param({
                'state': 0
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

    $scope.submit = function(){
        $http({
            url: '/api/1.0/course',
            method: 'POST',
            data: JSON.stringify($scope.course),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('course');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.$watch('course.organizationId', function(newVal, oldVal){
        if(newVal !== oldVal){
            $scope.getAdvisor();
            //$scope.getTag();
        }
    }, true);

    //初始化数据
    $scope.getOrganization();
    //$scope.getAdvisor();
    $scope.getState();
    //$scope.getLevel();
    //$scope.getTag();
});
app.controller('profileEditController', function($scope, $state, FileUploader, $stateParams, $http){
    // $scope.userTypes        = ["情感分析师","情感导师"];
    $scope.id = $stateParams.id;
    // $scope.organizations = [];
    $scope.roles        = {1:"用户",2:"助理",3:"销售",4:"咨询师"};
    // $scope.states        = {};

    // var uploader = $scope.uploader = new FileUploader({
    //     url: '/api/1.0/app/profile/upload',
    //     queueLimit: 1,
    //     method: 'POST',
    //     removeAfterUpload: true
    // });
    //
    // uploader.filters.push({
    //     name: 'imageFilter',
    //     fn: function(item){
    //         var type = '|' + item.type.slice(item.type.lastIndexOf('/') + 1) + '|';
    //         return 'jpg|jpeg|png|bmp|gif|'.indexOf(type) !== -1;
    //     }
    // });
    //
    // uploader.onSuccessItem = function(item, response, status, headers){
    //     $scope.profile.imgUrl = response.result;
    //
    // };

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };
    
    $scope.profile = {
        id   : $stateParams.id,
        name        : '',
        role : ''
    };

    $scope.reset = function(){
        $scope.profile.name         = '';
        $scope.profile.role  = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/app/profile',
            method: 'PUT',
            data: JSON.stringify($scope.profile),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('profile');
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };


    $scope.getById = function(){

        $http({
            url: '/api/1.0/app/profile/' + $scope.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.profile);
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);
        });
    };

    // $scope.getOrganization = function(){
    //     $http({
    //         url: '/api/1.0/organization/list',
    //         method: 'POST',
    //         data: $.param({
    //             'state' : 0
    //         }),
    //         headers: {
    //             'Content-Type': 'application/x-www-form-urlencoded'
    //         }
    //     }).success(function(res, status, headers, config){
    //         if(res.success){
    //             $scope.organizations = res.result;
    //         }
    //     }).error(function(response){
    //         $scope.organizations = [];
    //     });
    // };

    // $scope.getState = function(){
    //     $http({
    //         url: '/api/1.0/usingState/list',
    //         method: 'GET'
    //     }).success(function(res, status, headers, config){
    //         if(res.success){
    //             $scope.states = res.result;
    //         }
    //     }).error(function(response){
    //         $scope.states = [];
    //     });
    // };

    $scope.getById();
    // $scope.getState();
    // $scope.getOrganization();
});
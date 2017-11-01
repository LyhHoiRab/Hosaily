app.controller('advisorEditController', function($scope, $state, $stateParams, FileUploader, $http){
    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/advisor/upload',
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
        $scope.advisor.headImgUrl = response.result;

    };

    $scope.editor = {
        allowedContent: true,
        entitles: false,
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    };

    $scope.advisor = {
        id           : $stateParams.id,
        name         : '',
        nickname     : '',
        age          : '',
        sex          : '',
        wechat       : '',
        introduction : '',
        state        : '',
        sort         : '',
        headImgUrl   : ''
    };

    $scope.reset = function(){
        $scope.advisor.name         = '';
        $scope.advisor.nickname     = '';
        $scope.advisor.age          = '';
        $scope.advisor.sex          = '';
        $scope.advisor.wechat       = '';
        $scope.advisor.introduction = '';
        $scope.advisor.state        = '';
        $scope.advisor.sort         = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/advisor',
            method: 'PUT',
            data: JSON.stringify($scope.advisor),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('advisor');
            }else{
                alert(res.msg);
            }
        }).error(function(response){

        });
    };

    $scope.getById = function(){
        $http({
            url: '/api/1.0/advisor/' + $scope.advisor.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.advisor);
            }else{
                alert(res.msg);
            }
        }).error(function(response){

        });
    };

    $scope.getById();
});
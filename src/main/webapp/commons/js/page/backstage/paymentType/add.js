app.controller('paymentTypeAddController', function($scope, $state, FileUploader, $http){
    //查询列表
    $scope.states = [];
    $scope.paymentType = {
        name   : '',
        state  : '',
        imgUrl : '/commons/img/level_default.jpg'
    };

    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/paymentType/upload',
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
        $scope.paymentType.imgUrl = response.result;
    };

    $scope.reset = function(){
        $scope.state         = '';
        $scope.name          = '';
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/paymentType',
            method: 'POST',
            data: JSON.stringify($scope.paymentType),
            headers: {
                'Content-Type': 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('paymentType');
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
            $scope.states = [];
        });
    };

    //初始化数据
    $scope.getState();
});
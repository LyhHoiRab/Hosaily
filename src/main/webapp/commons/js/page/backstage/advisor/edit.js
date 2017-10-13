app.controller('advisorEditController', function($scope, $state, $stateParams, FileUploader){
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
            return 'jpeg|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
        }
    });

    uploader.onSuccessItem = function(item, response, status, headers){
        $scope.advisor.headImgUrl = response.result;

        if(!$scope.$$phase){
            $scope.$apply();
        }
    };

    $scope.id = $stateParams.id;
    $scope.advisor = {
        name         : '',
        nickname     : '',
        age          : '',
        sex          : '',
        wechat       : '',
        introduction : '',
        state        : '',
        headImgUrl   : '/commons/img/level_default.jpg'
    };

    $scope.reset = function(){
        $scope.advisor.name         = '';
        $scope.advisor.nickname     = '';
        $scope.advisor.age          = '';
        $scope.advisor.sex          = '';
        $scope.advisor.wechat       = '';
        $scope.advisor.introduction = '';
        $scope.advisor.state        = '';
    };

    $scope.submit = function(){
        $.ajax({
            url: '/api/1.0/advisor',
            type: 'PUT',
            data: JSON.stringify($scope.advisor),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('advisor');
                }
            }
        });
    };

    $scope.getById = function(){
        $.ajax({
            url: '/api/1.0/advisor/' + $scope.id,
            type: 'GET',
            dataType: 'JSON',
            success: function(res){
                if(res.success){
                    //$scope.advisor = res.result;
                    utils.copyOf(res.result, $scope.advisor);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.getById();
});
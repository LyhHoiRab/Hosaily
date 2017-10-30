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
            return 'jpg|jpeg|png|bmp|gif|'.indexOf(type) !== -1;
        }
    });

    uploader.onSuccessItem = function(item, response, status, headers){
        $scope.advisor.headImgUrl = response.result;

        if(!$scope.$$phase){
            $scope.$apply();
        }
    };

    var editor = CKEDITOR.replace('editor', {
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    });

    $scope.id = $stateParams.id;
    $scope.advisor = {
        id           : $scope.id,
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

        editor.setData('');
    };

    $scope.submit = function(){
        $scope.advisor.introduction = editor.getData();

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
                    utils.copyOf(res.result, $scope.advisor);
                    editor.setData($scope.advisor.introduction);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.getById();
});
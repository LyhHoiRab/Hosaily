app.controller('advisorAddController', function($scope, $state, FileUploader){
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

    //初始化富文本框
    editorInit();
    var ue = UE.getEditor('editor', {
        initialFrameHeight: 450,
        serverUrl: ''
    });

    $scope.advisor = {
        name         : '',
        nickname     : '',
        age          : '',
        sex          : '',
        wechat       : '',
        introduction : '',
        state        : '',
        sort         : '',
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
        $scope.advisor.sort         = '';

        ue.setContent('');
    };

    $scope.submit = function(){
        $scope.advisor.introduction = ue.getContent();

        $.ajax({
            url: '/api/1.0/advisor',
            type: 'POST',
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
});
app.controller('customizationAddController', function($scope, $state, FileUploader){
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

        if(!$scope.$$phase){
            $scope.$apply();
        }
    };

    var editor = CKEDITOR.replace('editor', {
        customConfig: '/commons/js/plugin/ckeditor/config.js'
    });

    $scope.customization = {
        title        : '',
        summary      : '',
        introduction : '',
        state        : '',
        cover        : '/commons/img/level_default.jpg',
        sort         : 0,
        subscribe    : 0
    };

    $scope.reset = function(){
        $scope.customization.title         = '';
        $scope.customization.summary       = '';
        $scope.customization.introduction  = '';
        $scope.customization.state         = '';
        $scope.customization.sort          = 0;
        $scope.customization.subscribe     = 0;

        editor.setData('');
    };

    $scope.submit = function(){
        $scope.customization.introduction = editor.getData();

        $.ajax({
            url: '/api/1.0/customization',
            type: 'POST',
            data: JSON.stringify($scope.customization),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('customization');
                }
            }
        });
    };
});
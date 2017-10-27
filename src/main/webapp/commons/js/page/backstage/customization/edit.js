app.controller('customizationEditController', function($scope, $state, $stateParams, FileUploader){
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

    editorInit();
    var ue = UE.getEditor('editor', {
        initialFrameHeight: 450,
        serverUrl: ''
    });

    $scope.id = $stateParams.id;
    $scope.customization = {
        id           : $scope.id,
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

        ue.setContent('');
    };

    $scope.getById = function(){
        $.ajax({
            url: '/api/1.0/customization/' + $scope.id,
            dataType: 'JSON',
            type: 'GET',
            success: function(res){
                if(res.success){
                    utils.copyOf(res.result, $scope.customization);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.submit = function(){
        $scope.customization.introduction = ue.getContent();

        $.ajax({
            url: '/api/1.0/customization',
            type: 'PUT',
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

    $scope.getById();

    ue.addListener('ready', function(){
        ue.setContent($scope.customization.introduction);
    });
});
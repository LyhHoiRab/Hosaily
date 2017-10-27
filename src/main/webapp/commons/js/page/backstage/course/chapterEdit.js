app.controller('chapterEditController', function($scope, $state, $stateParams, FileUploader){
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

        if(!$scope.$$phase){
            $scope.$apply();
        }
    };

    $('.selectpicker').selectpicker({
        title: '请选择'
    });

    editorInit();
    var ue = UE.getEditor('editor', {
        initialFrameHeight: 450,
        serverUrl: ''
    });

    $scope.id = $stateParams.id;
    $scope.course = {
        id           : $scope.id,
        parentId     : '',
        title        : '',
        type         : 1,
        kind         : 0,
        summary      : '',
        introduction : '',
        state        : '',
        cover        : '/commons/img/level_default.jpg',
        likes        : 0,
        view         : 0,
        sort         : 0,
        price        : 0,
        children     : []
    };

    $scope.sectionAdd = function(){
        $state.go('sectionAdd', {parentId : $scope.id});
    };

    $scope.reset = function(){
        $scope.course.title         = '';
        $scope.course.summary       = '';
        $scope.course.introduction  = '';
        $scope.course.state         = '';
        $scope.course.likes         = 0;
        $scope.course.view          = 0;
        $scope.course.sort          = 0;
        $scope.course.price         = 0;

        $('.selectpicker').selectpicker('deselectAll');
        ue.setContent('');
    };

    $scope.submit = function(){
        $scope.course.introduction = ue.getContent();

        $.ajax({
            url: '/api/1.0/course',
            type: 'PUT',
            data: JSON.stringify($scope.course),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('courseEdit', {id : $scope.course.parentId});
                }
            }
        });
    };

    $scope.getById = function(){
        $.ajax({
            url: '/api/1.0/course/chapter/' + $scope.id,
            dataType: 'JSON',
            type: 'GET',
            success: function(res){
                if(res.success){
                    utils.copyOf(res.result, $scope.course);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.getById();

    ue.addListener('ready', function(){
        ue.setContent($scope.course.introduction);
    });
});
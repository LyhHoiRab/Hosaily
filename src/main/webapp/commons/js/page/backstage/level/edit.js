app.controller('levelEditController', function($scope, $state, $stateParams, FileUploader){
    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/level/upload',
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
        $scope.level.icon = response.result;

        if(!$scope.$$phase){
            $scope.$apply();
        }
    };

    $scope.id = $stateParams.id;

    $scope.level = {
        id          : '',
        name        : '',
        state       : '',
        description : '',
        icon        : basePath + '/commons/img/level_default.jpg',
        price       : []
    };

    $scope.price = function(){
        var item = {
            id        : '',
            price     : 0,
            effective : 0,
            levelId  : $scope.id
        };

        $scope.level.price.push(item);
    };

    $scope.delete = function(index){
        $.ajax({
            url: '/api/1.0/levelPrice/' + $scope.level.price[index].id,
            type: 'DELETE',
            dataType: 'JSON',
            success: function(res){
                if(res.success){
                    $scope.level.price.splice(index, 1);
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.reset = function(){
        $scope.level.name         = '';
        $scope.level.description  = '';
        $scope.level.state        = '';
        $scope.level.price        = [];
    };

    $scope.submit = function(){
        $.ajax({
            url: '/api/1.0/level',
            type: 'PUT',
            data: JSON.stringify($scope.level),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('level');
                }
            }
        });
    };

    $scope.getById = function(){
        $.ajax({
            url: '/api/1.0/level/' + $scope.id,
            type: 'GET',
            dataType: 'JSON',
            success: function(res){
                if(res.success){
                    $scope.level = res.result;
                }

                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.getById();
});
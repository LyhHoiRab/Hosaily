app.controller('levelAddController', function($scope, $state, FileUploader){
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

    $scope.level = {
        name        : '',
        description : '',
        state       : '',
        icon        : basePath + '/commons/img/level_default.jpg',
        price       : []
    };

    $scope.price = function(){
        var item = {
            price    : 0,
            effective: 0
        };

        $scope.level.price.push(item);
    };

    $scope.delete = function(index){
        $scope.level.price.splice(index, 1);
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
            type: 'POST',
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
});
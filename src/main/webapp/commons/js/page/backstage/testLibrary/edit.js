app.controller('testLibraryEditController', function($scope, $state, $stateParams, FileUploader, $http){
    //下拉
    $scope.states = [];

    var uploader = $scope.uploader = new FileUploader({
        url: '/api/1.0/testLibrary/upload',
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
        $scope.testLibrary.icon = response.result;
    };

    $scope.testLibrary = {
        id          : $stateParams.id,
        title       : '',
        kind        : '',
        topic       : '',
        state       : '',
        imgUrl      : '/commons/img/level_default.jpg',
        answers     : []
    };

    $scope.answer = function(){
        var item = {
            content  : '',
            analysis : '',
            sort     : $scope.testLibrary.answers.length,
            isTrue   : 'false'
        };

        $scope.testLibrary.answers.push(item);
    };

    $scope.delete = function(index){
        $scope.testLibrary.answers.splice(index, 1);
    };

    $scope.reset = function(){
        $scope.testLibrary.title   = '';
        $scope.testLibrary.kind    = '';
        $scope.testLibrary.topic   = '';
        $scope.testLibrary.state   = '';
        $scope.testLibrary.answers = [];
    };

    $scope.submit = function(){
        $http({
            url: '/api/1.0/testLibrary',
            method: 'POST',
            data: JSON.stringify($scope.testLibrary),
            headers: {
                'Content-Type' : 'application/json'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $state.go('testLibrary');
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

    $scope.getData = function(){
        $http({
            url: '/api/1.0/testLibrary/' + $scope.testLibrary.id,
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                utils.copyOf(res.result, $scope.testLibrary);
                console.log($scope.testLibrary);
            }
        }).error(function(response){

        });
    };

    //初始化数据
    $scope.getState();
    $scope.getData();
});
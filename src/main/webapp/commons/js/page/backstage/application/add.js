app.controller('applicationAddController', function($scope, $state){
    $scope.application = {
        name   : '',
        appId  : '',
        secret : '',
        aesKey : '',
        token  : '',
        type   : ''
    };

    $scope.reset = function(){
        $scope.application.name   = '';
        $scope.application.appId  = '';
        $scope.application.secret = '';
        $scope.application.aesKey = '';
        $scope.application.token  = '';
        $scope.application.type   = '';
    };

    $scope.submit = function(){
        $.ajax({
            url: '/api/1.0/application',
            type: 'POST',
            data: JSON.stringify($scope.application),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('application');
                }
            }
        });
    };
});
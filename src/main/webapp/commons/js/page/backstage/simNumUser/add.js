app.controller('simNumUserAddController', function($scope, $state){
    $scope.simNumUser = {
        sim   : '',
        num        : '',
        userName : ''
    };
    
    $scope.reset = function(){
        $scope.simNumUser.sim    = '';
        $scope.simNumUser.num         = '';
        $scope.simNumUser.userName  = '';
    };

    $scope.submit = function(){
        $.ajax({
            url: '/api/1.0/simNumUser',
            type: 'POST',
            data: JSON.stringify($scope.simNumUser),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('simNumUser');
                }
            }
        });
    };
});
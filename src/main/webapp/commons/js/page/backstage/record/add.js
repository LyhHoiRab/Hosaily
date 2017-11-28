app.controller('recordAddController', function($scope, $state){
    $scope.record = {
        sim   : '',
        outGoingNum        : '',
        num : '',
        time : '',
        path       : '',
        userName       : ''
    };
    
    $scope.reset = function(){
        $scope.record.sim    = '';
        $scope.record.outGoingNum         = '';
        $scope.record.num  = '';
        $scope.record.time        = '';
        $scope.record.path        = '';
        $scope.record.userName        = '';
    };

    $scope.submit = function(){
        $.ajax({
            url: '/api/1.0/record',
            type: 'POST',
            data: JSON.stringify($scope.record),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('record');
                }
            }
        });
    };
});
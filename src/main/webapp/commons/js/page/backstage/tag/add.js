app.controller('tagAddController', function($scope, $state){
    $scope.tag = {
        name        : '',
        description : '',
        state       : ''
    };

    $scope.reset = function(){
        $scope.tag.name         = '';
        $scope.tag.description  = '';
        $scope.tag.state        = '';
    };

    $scope.submit = function(){
        $.ajax({
            url: '/api/1.0/tag',
            type: 'POST',
            data: JSON.stringify($scope.tag),
            dataType: 'JSON',
            contentType: 'application/json',
            success: function(res){
                if(res.success){
                    alert(res.msg);
                    $state.go('tag');
                }
            }
        });
    };
});